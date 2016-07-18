package dk.xakeps.spigot.plugins.keycombinator.events;

import dk.xakeps.spigot.plugins.keycombinator.models.Keys;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerKeyPressEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Player player;
    private final Keys key;

    public PlayerKeyPressEvent(Player player, Keys key) {
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

    public Keys getKey() {
        return key;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
