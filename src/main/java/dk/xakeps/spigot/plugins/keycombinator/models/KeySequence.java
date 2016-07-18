package dk.xakeps.spigot.plugins.keycombinator.models;

import java.util.List;

public class KeySequence {
    private String name;
    private List<Command> commands;
    private List<Keys> sequence;

    public KeySequence(String name, List<Command> commands, List<Keys> sequence) {
        this.name = name;
        this.commands = commands;
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    public List<Keys> getSequence() {
        return sequence;
    }

    public void setSequence(List<Keys> sequence) {
        this.sequence = sequence;
    }
}
