package dk.xakeps.spigot.plugins.keycombinator.events;

import dk.xakeps.spigot.plugins.keycombinator.models.Key;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class SequencePassedEvent extends Event {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final Player player;
    private final List<Key> sequence;

    public SequencePassedEvent(Player player, List<Key> sequence) {
        super(true);
        this.player = player;
        this.sequence = sequence;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Key> getSequence() {
        return sequence;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
