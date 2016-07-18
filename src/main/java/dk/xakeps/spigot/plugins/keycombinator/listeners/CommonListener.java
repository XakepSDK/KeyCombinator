package dk.xakeps.spigot.plugins.keycombinator.listeners;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import dk.xakeps.spigot.plugins.keycombinator.api.NamedKeySequence;
import dk.xakeps.spigot.plugins.keycombinator.events.PlayerKeyPressEvent;
import dk.xakeps.spigot.plugins.keycombinator.events.SequencePassedEvent;
import dk.xakeps.spigot.plugins.keycombinator.models.Key;
import dk.xakeps.spigot.plugins.keycombinator.models.KeySequence;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.List;
import java.util.Map;

public class CommonListener implements Listener {
    private Map<Player, List<Key>> playerKeysPressed;

    public CommonListener() {
        playerKeysPressed = KeyCombinator.getKeysController().getPlayerKeysPressed();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerKeysPressed.put(event.getPlayer(), new KeySequence());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        playerKeysPressed.remove(event.getPlayer());
    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent event) {
        if(event.isSprinting()) {
            callKeyPressEvent(event.getPlayer(), Key.SPRINT);
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if(event.isSneaking()) {
            callKeyPressEvent(event.getPlayer(), Key.SNEAK);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        switch(event.getAction()) {
            case LEFT_CLICK_AIR:
                callKeyPressEvent(event.getPlayer(), Key.LMB_AIR);
                break;
            case LEFT_CLICK_BLOCK:
                callKeyPressEvent(event.getPlayer(), Key.LMB_BLOCK);
                break;
            case RIGHT_CLICK_AIR:
                callKeyPressEvent(event.getPlayer(), Key.RMB_AIR);
                break;
            case RIGHT_CLICK_BLOCK:
                callKeyPressEvent(event.getPlayer(), Key.RMB_BLOCK);
                break;
        }
    }

    @EventHandler
    public void onPlayerKeyPress(PlayerKeyPressEvent event) {
        List<Key> keysPressed = playerKeysPressed.get(event.getPlayer());
        keysPressed.add(event.getKey());
        KeyCombinator.getKeysController().getPassedSequence(keysPressed).ifPresent(keySequence -> {
            keysPressed.clear();
            SequencePassedEvent sequencePassedEvent = new SequencePassedEvent(event.getPlayer(), keySequence);
            Bukkit.getPluginManager().callEvent(sequencePassedEvent);
        });
    }

    @EventHandler
    public void onSequencePassed(SequencePassedEvent event) {
        if(event.getSequence() instanceof NamedKeySequence) {
            NamedKeySequence namedKeySequenceImpl = (NamedKeySequence) event.getSequence();
            if(event.getPlayer().hasPermission("keycombinator.sequence." + namedKeySequenceImpl.getName())) {
                namedKeySequenceImpl.getCommandsHolders().forEach(commandsHolder -> commandsHolder.execute(event.getPlayer()));
            }
        }
    }

    private void callKeyPressEvent(Player presser, Key key) {
        PlayerKeyPressEvent playerKeyPressEvent = new PlayerKeyPressEvent(presser, key);
        Bukkit.getScheduler().scheduleAsyncDelayedTask(KeyCombinator.getInstance(), () -> Bukkit.getPluginManager().callEvent(playerKeyPressEvent));
    }
}
