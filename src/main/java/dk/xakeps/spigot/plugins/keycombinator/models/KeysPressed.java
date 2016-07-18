package dk.xakeps.spigot.plugins.keycombinator.models;

import java.util.ArrayList;
import java.util.List;

public class KeysPressed {
    private List<Keys> keysPressed;

    public KeysPressed() {
        keysPressed = new ArrayList<>();
    }

    public void pressKey(Keys key) {
        keysPressed.add(key);
    }

    public void reset() {
        keysPressed.clear();
    }
}
