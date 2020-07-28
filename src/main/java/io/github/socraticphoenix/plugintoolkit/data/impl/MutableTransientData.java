package io.github.socraticphoenix.plugintoolkit.data.impl;

import io.github.socraticphoenix.plugintoolkit.data.TransientData;
import org.spongepowered.api.data.manipulator.DataManipulator;

public interface MutableTransientData extends DataManipulator<MutableTransientData, ImmutableTransientData> {

    TransientData data();

}
