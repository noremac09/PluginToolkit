package io.github.socraticphoenix.anubismc.plugintoolkit.data;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class PluginTransientData {
    private Object plugin;
    private TransientData data;

    public PluginTransientData(Object plugin, TransientData data) {
        this.plugin = plugin;
        this.data = data;
    }

    public <T> T getOrCreate(String key, Class<T> type, T def) {
        return this.data.getOrCreate(this.plugin, key, type, def);
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        return this.data.get(this.plugin, key, type);
    }

    public PluginTransientData put(String key, Object val) {
        this.data.put(this.plugin, key, val);
        return this;
    }

    public Map<String, Object> getAll() {
        Map<String, Object> res = new LinkedHashMap<>();
        this.data.getAll().forEach((k, v) -> {
            if (k.getPlugin() == this.plugin) {
                res.put(k.getKey(), v);
            }
        });
        return res;
    }

}
