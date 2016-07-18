package dk.xakeps.spigot.plugins.keycombinator.models;

import dk.xakeps.spigot.plugins.keycombinator.api.NamedKeySequence;

import java.util.List;

public class NamedKeySequenceImpl extends KeySequence implements NamedKeySequence {
    private String name;
    private List<CommandsHolder> commandsHolders;

    public NamedKeySequenceImpl(String name, List<CommandsHolder> commandsHolders, List<Key> sequence) {
        this.name = name;
        this.commandsHolders = commandsHolders;
        this.addAll(sequence);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<CommandsHolder> getCommandsHolders() {
        return commandsHolders;
    }

    @Override
    public void setCommandsHolders(List<CommandsHolder> commandsHolders) {
        this.commandsHolders = commandsHolders;
    }

    @Override
    public String toString() {
        return name;
    }
}
