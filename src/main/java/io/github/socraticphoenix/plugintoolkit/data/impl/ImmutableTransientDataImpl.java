package io.github.socraticphoenix.plugintoolkit.data.impl;

import io.github.socraticphoenix.plugintoolkit.PluginToolkitPlugin;
import io.github.socraticphoenix.plugintoolkit.data.TransientData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableTransientDataImpl extends AbstractImmutableSingleData<TransientData, ImmutableTransientData, MutableTransientData> implements ImmutableTransientData {


    protected ImmutableTransientDataImpl(TransientData value) {
        super(PluginToolkitPlugin.TRANSIENT_DATA, value);
    }

    @Override
    public TransientData data() {
        return this.value;
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(PluginToolkitPlugin.TRANSIENT_DATA, this.defaultValue).asImmutable();
    }

    @Override
    public MutableTransientData asMutable() {
        return new MutableTransientDataImpl(this.value.copy());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    protected DataContainer fillContainer(DataContainer dataContainer) {
        return dataContainer;
    }
}
