package io.github.socraticphoenix.anubismc.plugintoolkit.data.impl;

import io.github.socraticphoenix.anubismc.plugintoolkit.data.TransientData;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;

public interface ImmutableTransientData extends ImmutableDataManipulator<ImmutableTransientData, MutableTransientData> {

    TransientData data();

}
