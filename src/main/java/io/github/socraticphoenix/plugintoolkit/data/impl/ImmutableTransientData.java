package io.github.socraticphoenix.plugintoolkit.data.impl;

import io.github.socraticphoenix.plugintoolkit.data.TransientData;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;

public interface ImmutableTransientData extends ImmutableDataManipulator<ImmutableTransientData, MutableTransientData> {

    TransientData data();

}
