package io.github.socraticphoenix.anubismc.plugintoolkit.data.impl;

import io.github.socraticphoenix.anubismc.plugintoolkit.data.TransientData;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class TransientDataBuilder implements DataManipulatorBuilder<MutableTransientData, ImmutableTransientData> {

    @Override
    public MutableTransientData create() {
        return new MutableTransientDataImpl(new TransientData());
    }

    @Override
    public Optional<MutableTransientData> createFrom(DataHolder dataHolder) {
        return Optional.empty();
    }

    @Override
    public Optional<MutableTransientData> build(DataView container) throws InvalidDataException {
        return Optional.empty();
    }

}
