package dk.xakeps.spigot.plugins.keycombinator.hooks;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook extends EZPlaceholderHook {
    public PlaceholderAPIHook() {
        super(KeyCombinator.getInstance(), "keycombinator");
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equals("keys_pressed")) {
            return "";
        }
        return null;
    }
}
