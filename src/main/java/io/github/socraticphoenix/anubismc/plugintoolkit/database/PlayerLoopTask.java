package io.github.socraticphoenix.anubismc.plugintoolkit.database;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.List;
import java.util.function.Consumer;

public class PlayerLoopTask implements Consumer<Task> {
    private Consumer<Player> task;

    private int index;

    public PlayerLoopTask(Consumer<Player> task) {
        this.task = task;
        this.index = 0;
    }

    @Override
    public void accept(Task task) {
        List<Player> players = PlayerList.INSTANCE.getPlayers();
        if (!players.isEmpty()) {
            Player p = null;
            try {
                p = players.get(this.index++ % players.size());
            } catch (IndexOutOfBoundsException | ArithmeticException ignore) {
                //I think this can technically happen if we call players.size, then another thread
                // performs a removal. It's unlikely but just to be safe...
            }

            if (p != null) {
                this.task.accept(p);
            }
        }

        if (index == Integer.MAX_VALUE) {
            this.index = 0; //For the one server owner that leaves it running for 20 years with no restarts
        }
    }

}
