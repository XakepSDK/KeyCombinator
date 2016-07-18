package dk.xakeps.spigot.plugins.keycombinator;

import dk.xakeps.spigot.plugins.keycombinator.hooks.PlaceholderAPIHook;
import dk.xakeps.spigot.plugins.keycombinator.listeners.CommonListener;
import dk.xakeps.spigot.plugins.keycombinator.models.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyCombinator extends JavaPlugin {
    private static KeyCombinator instance;
    private boolean placeholderApiEnabled;
    private List<KeySequence> sequences;
    private List<Command> commands;
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
        loadCommands();

        Bukkit.getPluginManager().registerEvents(new CommonListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public Map<Player, KeysPressed> getPlayerKeysPressed() {
        return playerKeysPressed;
    }

    public List<KeySequence> getSequences() {
        return sequences;
    }

    public List<Command> getCommands() {
        return commands;
    }

    private void loadSequences() {
        sequences = new ArrayList<>();
        try {
            Path sequencesPath = Paths.get(getDataFolder().toPath().toString(), "sequences");
            Files.walk(sequencesPath).filter(file -> file.getFileName().endsWith(".yml")).forEach(file -> {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file.toFile());
                String name = config.getString("name");
                List<String> commands = config.getStringList("commands");
                List<String> sequenceKeys = config.getStringList("sequence");
                List<Keys> sequence = sequenceKeys.stream().map(Keys::valueOf).collect(Collectors.toList());
                KeySequence keySequence = new KeySequence(name, commands, sequence);
            });
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCommands() {
        commands = new ArrayList<>();
        try {
            Path commandsPath = Paths.get(getDataFolder().toPath().toString(), "commands");
            Files.walk(commandsPath).filter(file -> file.getFileName().endsWith(".yml")).forEach(file -> {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file.toFile());
                String name = config.getString("name");
                List<Map<?, ?>> commandsList = config.getMapList("commands");
                Map<CommandTypes, String> typedCommands = new HashMap<>();
                commandsList.forEach(map -> {
                    CommandTypes executor = CommandTypes.valueOf((String) map.get("executor"));
                    String command = (String) map.get("command");
                    typedCommands.put(executor, command);
                });
                Command command = new Command(name, typedCommands);
                commands.add(command);
            });
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaceholderApiEnabled() {
        return placeholderApiEnabled;
    }
}
