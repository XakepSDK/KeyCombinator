package dk.xakeps.spigot.plugins.keycombinator.api;

import dk.xakeps.spigot.plugins.keycombinator.models.CommandsHolder;

import java.util.List;

public interface NamedKeySequence {
    String getName();

    void setName(String name);

    List<CommandsHolder> getCommandsHolders();

    void setCommandsHolders(List<CommandsHolder> commandsHolders);
}
