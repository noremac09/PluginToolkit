package io.github.socraticphoenix.anubismc.plugintoolkit.data.impl;

import io.github.socraticphoenix.anubismc.plugintoolkit.data.TransientData;
import org.spongepowered.api.data.manipulator.DataManipulator;

public interface MutableTransientData extends DataManipulator<MutableTransientData, ImmutableTransientData> {

    TransientData data();

}
