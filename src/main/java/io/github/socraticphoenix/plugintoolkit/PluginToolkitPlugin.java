package io.github.socraticphoenix.plugintoolkit;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import io.github.socraticphoenix.plugintoolkit.data.TransientData;
import io.github.socraticphoenix.plugintoolkit.data.impl.ImmutableTransientData;
import io.github.socraticphoenix.plugintoolkit.data.impl.ImmutableTransientDataImpl;
import io.github.socraticphoenix.plugintoolkit.data.impl.MutableTransientData;
import io.github.socraticphoenix.plugintoolkit.data.impl.MutableTransientDataImpl;
import io.github.socraticphoenix.plugintoolkit.data.impl.TransientDataBuilder;
import io.github.socraticphoenix.plugintoolkit.database.PlayerList;
import io.github.socraticphoenix.plugintoolkit.optional.MissingPlaceholderService;
import io.github.socraticphoenix.plugintoolkit.optional.PlaceHolderOptionalService;
import io.github.socraticphoenix.plugintoolkit.optional.PresentPlaceholderService;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

@Plugin(
        id = "socratictoolkit",
        name = "Plugin Toolkit",
        version = "1.0.1",
        authors = "SocraticPhoenix",
        dependencies = @Dependency(id = "placeholderapi", optional = true)
)
public class PluginToolkitPlugin {
    public static Key<Value<TransientData>> TRANSIENT_DATA = DummyObjectProvider.createExtendedFor(Key.class, "TRANSIENT_DATA");
    public static DataRegistration<MutableTransientData, ImmutableTransientData> TRANSIENT_DATA_REGISTRATION = DummyObjectProvider.createExtendedFor(DataRegistration.class, "TRANSIENT_DATA_REGISTRATION");

    @Inject
    Logger logger;

    private static PluginToolkitPlugin plugin;

    public PluginToolkitPlugin() {
        plugin = this;
    }

    public static PluginToolkitPlugin get() {
        return plugin;
    }

    private PlaceHolderOptionalService placeHolderOptionalService;

    @Listener(order = Order.EARLY)
    public void onPreInit(GamePreInitializationEvent ev) {
        try {
            Class.forName("me.rojo8399.placeholderapi.PlaceholderService");
            placeHolderOptionalService = new PresentPlaceholderService();
            this.logger.info("Placeholder API found");
        } catch (ClassNotFoundException e) {
            placeHolderOptionalService = new MissingPlaceholderService();
            this.logger.info("Placeholder API not found, falling back to default implementation");
        }

        Sponge.getEventManager().registerListeners(this, PlayerList.INSTANCE);
    }

    public PlaceHolderOptionalService getPlaceHolderOptionalService() {
        return this.placeHolderOptionalService;
    }

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
