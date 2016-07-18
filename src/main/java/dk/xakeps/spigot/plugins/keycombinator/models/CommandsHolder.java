package dk.xakeps.spigot.plugins.keycombinator.models;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CommandsHolder {
    private String name;
    private Map<CommandType, List<String>> commands;

    public CommandsHolder(String name, Map<CommandType, List<String>> commands) {
        this.name = name;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<CommandType, List<String>> getCommands() {
        return commands;
    }

    public void setCommands(Map<CommandType, List<String>> commands) {
        this.commands = commands;
    }

    public void execute(Player player) {
        commands.forEach((type, commandList) -> commandList.forEach(command -> {
            if(KeyCombinator.isPlaceholderApiEnabled()) {
                command = PlaceholderAPI.setPlaceholders(player, command);
            }

            switch(type) {
                case CONSOLE:
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    break;
                case PLAYER:
                    Bukkit.dispatchCommand(player, command);
                    break;
            }
        }));
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        CommandsHolder that = (CommandsHolder) o;

        return name.equals(that.name) && commands.equals(that.commands);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + commands.hashCode();
        return result;
    }
}
