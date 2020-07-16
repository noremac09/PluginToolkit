package io.github.socraticphoenix.anubismc.plugintoolkit.data;

import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.MutableTransientData;
import org.spongepowered.api.data.DataHolder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TransientData {
    private Map<Key, Object> data = new LinkedHashMap<>();

    public static <T> Optional<T> get(DataHolder holder, Object plugin, String key, Class<T> type) {
        return holder.get(MutableTransientData.class).flatMap(t -> t.data().get(plugin, key, type));
    }

    public static Optional<TransientDataView> getView(DataHolder holder, Object plugin) {
        return holder.get(MutableTransientData.class).map(t -> new TransientDataView(holder, plugin, t.data()));
    }

    public static TransientDataView getOrCreateView(DataHolder holder, Object plugin) {
        MutableTransientData data = holder.getOrCreate(MutableTransientData.class).get();
        return new TransientDataView(holder, plugin, data.data());
    }

    public TransientData copy() {
        TransientData copy = new TransientData();
        copy.data.putAll(this.data);
        return copy;
    }

    public TransientData immutable() {
        TransientData copy = copy();
        copy.data = Collections.unmodifiableMap(copy.data);
        return copy;
    }

    public <T> Optional<T> get(Key key, Class<T> type) {
        Object obj = this.data.get(key);
        if (type.isInstance(obj)) {
            return (Optional<T>) Optional.of(obj);
        } else {
            return Optional.empty();
        }
    }

    public <T> T getOrCreate(Key key, Class<T> type, T def) {
        return get(key, type).orElseGet(() -> {
           put(key, def);
            return def;
        });
    }

    public <T> T getOrCreate(Object plugin, String key, Class<T> type, T def) {
        return getOrCreate(key(plugin, key), type, def);
    }

    public <T> Optional<T> get(Object plugin, String key, Class<T> type) {
        return get(key(plugin, key), type);
    }

    public TransientData put(Key key, Object object) {
        this.data.put(key, object);
        return this;
    }

    public TransientData put(Object plugin, String key, Object object) {
        this.data.put(key(plugin, key), object);
        return this;
    }

    public Map<Key, Object> getAll() {
        return Collections.unmodifiableMap(this.data);
    }

    public PluginTransientData getFor(Object plugin) {
        return new PluginTransientData(plugin, this);
    }

    public static Key key(Object plugin, String key) {
        return new Key(plugin, key);
    }

    public static class Key {
        private Object plugin;
        private String key;

        public Key(Object plugin, String key) {
            this.plugin = plugin;
            this.key = key;
        }

        public Object getPlugin() {
            return plugin;
        }

        public String getKey() {
            return key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key1 = (Key) o;
            return Objects.equals(plugin, key1.plugin) &&
                    Objects.equals(key, key1.key);
        }

        @Override
        public int hashCode() {
            return Objects.hash(plugin, key);
        }
    }

}
