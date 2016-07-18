package dk.xakeps.spigot.plugins.keycombinator.models;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class KeysPressed {
    private List<Keys> keysPressed;

    public KeysPressed() {
        keysPressed = new ArrayList<>();
    }

    public void pressKey(Keys key) {
        keysPressed.add(key);
    }

    public List<Keys> getKeysPressed() {
        return keysPressed;
    }

    public void reset() {
        keysPressed.clear();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        keysPressed.forEach(keys -> joiner.add(keys.name()));
        return joiner.toString();
    }
}
