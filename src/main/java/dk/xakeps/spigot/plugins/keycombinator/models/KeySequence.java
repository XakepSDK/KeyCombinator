package dk.xakeps.spigot.plugins.keycombinator.models;

import java.util.List;

public class KeySequence {
    private String name;
    private List<String> commands;
    private List<Keys> sequence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<Keys> getSequence() {
        return sequence;
    }

    public void setSequence(List<Keys> sequence) {
        this.sequence = sequence;
    }
}
