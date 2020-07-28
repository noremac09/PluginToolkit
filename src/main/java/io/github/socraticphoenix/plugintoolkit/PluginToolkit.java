package io.github.socraticphoenix.plugintoolkit;

import io.github.socraticphoenix.plugintoolkit.database.PlayerLoopTask;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface PluginToolkit {

    static void async(Runnable runnable) {
        Sponge.getScheduler().createTaskBuilder()
                .async()
                .name("PluginToolkit Async Task")
                .execute(runnable)
                .submit(PluginToolkitPlugin.get());
    }

    static void sync(Runnable runnable) {
        Sponge.getScheduler().createTaskBuilder()
                .name("PluginToolkit Sync Task")
                .execute(runnable)
                .submit(PluginToolkitPlugin.get());
    }

    static void asyncPlayerLoop(Consumer<Player> task, long interval, TimeUnit unit) {
        Sponge.getScheduler().createTaskBuilder()
                .async()
                .name("PluginToolkit Async Player Loop")
                .execute(new PlayerLoopTask(task))
                .interval(interval, unit)
                .submit(PluginToolkitPlugin.get());
    }

    static void asyncPlayerLoop(Consumer<Player> task, long intervalSeconds) {
        asyncPlayerLoop(task, intervalSeconds, TimeUnit.SECONDS);
    }

    static void asyncPlayerLoop(Consumer<Player> task) {
        asyncPlayerLoop(task, 60);
    }

}
