package io.github.socraticphoenix.anubismc.plugintoolkit;

import com.google.common.reflect.TypeToken;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.TransientData;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.ImmutableTransientData;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.ImmutableTransientDataImpl;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.MutableTransientData;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.MutableTransientDataImpl;
import io.github.socraticphoenix.anubismc.plugintoolkit.data.impl.TransientDataBuilder;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

@Plugin(
        id = "plugintoolkit",
        name = "Plugin Toolkit",
        dependencies = @Dependency(id = "placeholderapi")
)
public class PluginToolkitPlugin {
    public static Key<Value<TransientData>> TRANSIENT_DATA = DummyObjectProvider.createExtendedFor(Key.class, "TRANSIENT_DATA");
    public static DataRegistration<MutableTransientData, ImmutableTransientData> TRANSIENT_DATA_REGISTRATION = DummyObjectProvider.createExtendedFor(DataRegistration.class, "TRANSIENT_DATA_REGISTRATION");

    @Listener
    public void onKeyRegister(GameRegistryEvent.Register<Key<?>> ev) {
        TRANSIENT_DATA = Key.builder()
                .type(new TypeToken<Value<TransientData>>() {})
                .name("Transient Data")
                .id("plugintoolkit_transient_data")
                .query(DataQuery.of("TransientData"))
                .build();
        ev.register(TRANSIENT_DATA);
    }

    @Listener
    public void onDataRegister(GameRegistryEvent.Register<DataRegistration<?, ?>> ev) {
        TRANSIENT_DATA_REGISTRATION = DataRegistration.builder()
                .dataClass(MutableTransientData.class)
                .dataImplementation(MutableTransientDataImpl.class)
                .immutableClass(ImmutableTransientData.class)
                .immutableImplementation(ImmutableTransientDataImpl.class)
                .builder(new TransientDataBuilder())
                .id("plugintoolkit_transient_data")
                .name("Transient Data")
                .build();
    }

}
