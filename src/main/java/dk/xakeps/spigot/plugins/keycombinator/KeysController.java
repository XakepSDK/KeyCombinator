package dk.xakeps.spigot.plugins.keycombinator;

import dk.xakeps.spigot.plugins.keycombinator.models.CommandType;
import dk.xakeps.spigot.plugins.keycombinator.models.CommandsHolder;
import dk.xakeps.spigot.plugins.keycombinator.models.Key;
import dk.xakeps.spigot.plugins.keycombinator.models.NamedKeySequenceImpl;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KeysController {
    private Set<List<Key>> sequences;
    private Set<CommandsHolder> commandsHolders;
    private Map<Player, List<Key>> playerKeysPressed;

    public KeysController() {
        loadCommands();
        loadSequences();
        playerKeysPressed = new HashMap<>();
    }

    private void loadFiles(Consumer<Path> consumer, String extension, String... path) {
        try {
            Path sequencesPath = Paths.get(KeyCombinator.getInstance().getDataFolder().toPath().toString(), path);
            Files.createDirectories(sequencesPath);
            try(Stream<Path> pathStream = Files.walk(sequencesPath, FileVisitOption.FOLLOW_LINKS)) {
                pathStream.filter(
                        pathPredicate -> !pathPredicate.toFile().isDirectory()
                                && pathPredicate.toFile().getAbsolutePath().endsWith(extension))
                        .forEach(consumer);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSequences() {
        if(sequences != null) return;
        sequences = new HashSet<>();
        loadFiles((sequencePath) -> {
            FileConfiguration config = YamlConfiguration.loadConfiguration(sequencePath.toFile());

            List<String> sequenceCommandNames = config.getStringList("commands");
            List<CommandsHolder> sequenceCommands = new ArrayList<>();
            sequenceCommandNames.forEach(s -> getCommandByName(s).ifPresent(sequenceCommands::add));
            List<Key> sequenceKeys = config.getStringList("sequence")
                    .stream().map(Key::valueOf).collect(Collectors.toList());

            List<Key> keySequence = new NamedKeySequenceImpl(
                    FilenameUtils.getBaseName(sequencePath.toFile().getName()),
                    sequenceCommands, sequenceKeys);
            sequences.add(keySequence);
        }, ".yml", "sequences");
    }

    private void loadCommands() {
        if(commandsHolders != null) return;
        commandsHolders = new HashSet<>();
        loadFiles((path -> {
            FileConfiguration config = YamlConfiguration.loadConfiguration(path.toFile());
            Map<CommandType, List<String>> typedCommands = new HashMap<>();
            for(CommandType commandType : CommandType.values()) {
                List<String> commands = config.getStringList(commandType.name());
                typedCommands.put(commandType, commands);
            }

            CommandsHolder commandsHolder = new CommandsHolder(
                    FilenameUtils.getBaseName(path.toFile().getName()),
                    typedCommands);
            commandsHolders.add(commandsHolder);
        }), ".yml", "commands");
    }

    private Optional<CommandsHolder> getCommandByName(String name) {
        if(commandsHolders == null) loadCommands();
        for(CommandsHolder commandsHolder : commandsHolders) {
            if(commandsHolder.getName().equals(name)) {
                return Optional.of(commandsHolder);
            }
        }
        return Optional.empty();
    }

    public Set<List<Key>> getSequences() {
        return sequences;
    }

    public Set<CommandsHolder> getCommandsHolders() {
        return commandsHolders;
    }

    public Map<Player, List<Key>> getPlayerKeysPressed() {
        return playerKeysPressed;
    }

    public Optional<List<Key>> getPassedSequence(List<Key> pressedSequence) {
        for(List<Key> sequence : sequences) {
            if(checkSequence(pressedSequence, sequence)) {
                return Optional.of(sequence);
            }
        }
        return Optional.empty();
    }

    public Optional<List<Key>> getPassedSequence(Player player) {
        return getPassedSequence(playerKeysPressed.get(player));
    }

    private boolean checkSequence(List<Key> pressedSequence, List<Key> sequence) {
        System.out.println(Arrays.toString(pressedSequence.toArray()));
        System.out.println(Arrays.toString(sequence.toArray()));
        int lastIndex = Collections.lastIndexOfSubList(pressedSequence, sequence);
        try {
            if(lastIndex != -1 && sequence.get(lastIndex + pressedSequence.size()) == null) {
                return true;
            }
        } catch(IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }
}
