package io.github.socraticphoenix.anubismc.plugintoolkit.data.impl;

import io.github.socraticphoenix.anubismc.plugintoolkit.PluginToolkitPlugin;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.TransientData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class MutableTransientDataImpl extends AbstractSingleData<TransientData, MutableTransientData, ImmutableTransientData> implements MutableTransientData {

    public MutableTransientDataImpl(TransientData value) {
        super(PluginToolkitPlugin.TRANSIENT_DATA, value);
    }

    @Override
    public TransientData data() {
        return this.value;
    }

    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(PluginToolkitPlugin.TRANSIENT_DATA, this.value);
    }

    @Override
    public Optional<MutableTransientData> fill(DataHolder dataHolder, MergeFunction overlap) {
        Optional<MutableTransientData> mergeData = dataHolder.get(MutableTransientData.class);

        if (mergeData.isPresent()) {
            MutableTransientData merge = overlap.merge(this, mergeData.get());
            this.value = merge.data();
        }

        return Optional.of(this);
    }

    @Override
    public Optional<MutableTransientData> from(DataContainer container) {
        return Optional.empty();
    }

    @Override
    public MutableTransientData copy() {
        return new MutableTransientDataImpl(this.value.copy());
    }

    @Override
    public ImmutableTransientData asImmutable() {
        return new ImmutableTransientDataImpl(this.value.immutable());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

}
