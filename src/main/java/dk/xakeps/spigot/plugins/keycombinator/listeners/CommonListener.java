package dk.xakeps.spigot.plugins.keycombinator.listeners;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import dk.xakeps.spigot.plugins.keycombinator.events.PlayerKeyPressEvent;
import dk.xakeps.spigot.plugins.keycombinator.models.Command;
import dk.xakeps.spigot.plugins.keycombinator.models.KeySequence;
import dk.xakeps.spigot.plugins.keycombinator.models.Keys;
import dk.xakeps.spigot.plugins.keycombinator.models.KeysPressed;
import dk.xakeps.spigot.plugins.keycombinator.utils.SequenceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import java.util.List;
import java.util.Map;

public class CommonListener implements Listener {
    private Map<Player, KeysPressed> playerKeysPressed;

    public CommonListener() {
        playerKeysPressed = KeyCombinator.getInstance().getPlayerKeysPressed();
    }

    @EventHandler
    public void onPlayerSprint(PlayerToggleSprintEvent event) {
        if(event.isSprinting()) {
            callKeyPressEvent(event.getPlayer(), Keys.SPRINT);
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if(event.isSneaking()) {
            callKeyPressEvent(event.getPlayer(), Keys.SNEAK);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        switch(event.getAction()) {
            case LEFT_CLICK_AIR:
                callKeyPressEvent(event.getPlayer(), Keys.LMB_AIR);
                break;
            case LEFT_CLICK_BLOCK:
                callKeyPressEvent(event.getPlayer(), Keys.LMB_BLOCK);
                break;
            case RIGHT_CLICK_AIR:
                callKeyPressEvent(event.getPlayer(), Keys.RMB_AIR);
                break;
            case RIGHT_CLICK_BLOCK:
                callKeyPressEvent(event.getPlayer(), Keys.RMB_BLOCK);
                break;
        }
    }

    @EventHandler
    public void onPlayerKeyPress(PlayerKeyPressEvent event) {
        playerKeysPressed.get(event.getPlayer()).pressKey(event.getKey());
        List<KeySequence> sequences = KeyCombinator.getInstance().getSequences();
        KeysPressed keysPressed = playerKeysPressed.get(event.getPlayer());
        if(SequenceUtils.getPassed(sequences, keysPressed).size() == 1) {
            List<Command> commands = sequences.get(0).getCommands();

        }
    }

    private void callKeyPressEvent(Player presser, Keys key) {
        PlayerKeyPressEvent playerKeyPressEvent = new PlayerKeyPressEvent(presser, key);
        Bukkit.getPluginManager().callEvent(playerKeyPressEvent);
    }
}
