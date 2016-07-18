package dk.xakeps.spigot.plugins.keycombinator;

import dk.xakeps.spigot.plugins.keycombinator.hooks.PlaceholderAPIHook;
import dk.xakeps.spigot.plugins.keycombinator.listeners.CommonListener;
import dk.xakeps.spigot.plugins.keycombinator.models.KeySequence;
import dk.xakeps.spigot.plugins.keycombinator.models.KeysPressed;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyCombinator extends JavaPlugin {
    private static KeyCombinator instance;
    private boolean placeholderApiEnabled;
    private List<KeySequence> sequences;
    private Map<Player, KeysPressed> playerKeysPressed;

    public static KeyCombinator getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        playerKeysPressed = new HashMap<>();

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIHook();
            placeholderApiEnabled = true;
        }

        loadSequences();

        Bukkit.getPluginManager().registerEvents(new CommonListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public Map<Player, KeysPressed> getPlayerKeysPressed() {
        return playerKeysPressed;
    }

    private void loadSequences() {
        sequences = new ArrayList<>();
    }

    public boolean isPlaceholderApiEnabled() {
        return placeholderApiEnabled;
    }
}
