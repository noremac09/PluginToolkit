package io.github.socraticphoenix.anubismc.plugintoolkit.database;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerList {
    public static final PlayerList INSTANCE = new PlayerList();

    private List<Player> players = new CopyOnWriteArrayList<>();

    @Listener
    public void onJoin(ClientConnectionEvent.Join ev) {
        this.players.add(ev.getTargetEntity());
    }

    @Listener
    public void onDisconnect(ClientConnectionEvent.Disconnect ev) {
        this.players.remove(ev.getTargetEntity());
    }

    public List<Player> getPlayers() {
        return players;
    }
}
