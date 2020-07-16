package io.github.socraticphoenix.anubismc.plugintoolkit.data;

import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.MutableTransientDataImpl;
import org.spongepowered.api.data.DataHolder;

import java.util.Optional;
import java.util.function.Function;

public class TransientDataView {
    private DataHolder holder;
    private Object plugin;
    private TransientData data;

    public TransientDataView(DataHolder holder, Object plugin, TransientData data) {
        this.holder = holder;
        this.plugin = plugin;
        this.data = data;
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        return this.data.get(this.plugin, key, type);
    }

    public <T> Optional<T> map(String key, Class<T> type, Function<T, T> fn) {
        Optional<T> opt = get(key, type);
        if (opt.isPresent()) {
            T res = fn.apply(opt.get());
            put(key, res);
            return Optional.ofNullable(res);
        } else {
            return Optional.empty();
        }
    }

    public void put(String key, Object val) {
        this.data.put(this.plugin, key, val);
        this.holder.offer(new MutableTransientDataImpl(this.data));
    }

}
