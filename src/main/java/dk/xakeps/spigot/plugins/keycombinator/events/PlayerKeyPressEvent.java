package dk.xakeps.spigot.plugins.keycombinator.events;

import dk.xakeps.spigot.plugins.keycombinator.models.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKeyPressEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Player player;
    private final Key key;

    public PlayerKeyPressEvent(Player player, Key key) {
        super(true);
        this.player = player;
        this.key = key;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Key getKey() {
        return key;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
