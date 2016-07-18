package dk.xakeps.spigot.plugins.keycombinator.models;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;

import java.util.ArrayList;

public class KeySequence extends ArrayList<Key> {
    public KeySequence() {
        super(KeyCombinator.getHistoryMaxSize());
    }

    @Override
    public boolean add(Key key) {
        if(size() >= KeyCombinator.getHistoryMaxSize()) remove(0);
        return super.add(key);
    }
}
