package dk.xakeps.spigot.plugins.keycombinator;

import dk.xakeps.spigot.plugins.keycombinator.hooks.PlaceholderAPIHook;
import dk.xakeps.spigot.plugins.keycombinator.listeners.CommonListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class KeyCombinator extends JavaPlugin {
    private static KeyCombinator instance;
    private boolean placeholderApiEnabled;
    private KeysController controller;
    private int historySize;

    public static KeyCombinator getInstance() {
        return instance;
    }

    public static boolean isPlaceholderApiEnabled() {
        return getInstance().placeholderApiEnabled;
    }

    public static KeysController getKeysController() {
        return getInstance().controller;
    }

    public static int getHistoryMaxSize() {
        return getInstance().historySize;
    }

    @Override
    public void onEnable() {
        instance = this;
        historySize = getConfig().getInt("SequenceMaxSize", 10);

        if(!getDataFolder().exists()) {
            saveResource("commands/example.yml", false);
            saveResource("sequences/example.yml", false);
        }

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIHook().hook();
            placeholderApiEnabled = true;
        }

        controller = new KeysController();
        Bukkit.getPluginManager().registerEvents(new CommonListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
