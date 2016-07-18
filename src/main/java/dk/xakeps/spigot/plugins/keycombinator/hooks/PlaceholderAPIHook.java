package dk.xakeps.spigot.plugins.keycombinator.hooks;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import dk.xakeps.spigot.plugins.keycombinator.api.NamedKeySequence;
import dk.xakeps.spigot.plugins.keycombinator.models.Key;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public class PlaceholderAPIHook extends EZPlaceholderHook {
    public PlaceholderAPIHook() {
        super(KeyCombinator.getInstance(), "keycombinator");
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equals("keys_pressed")) {
            return KeyCombinator.getKeysController().getPlayerKeysPressed().get(player).toString();
        }

        if(identifier.equals("last_sequence")) {
            Optional<List<Key>> keySequenceOptional = KeyCombinator.getKeysController().getPassedSequence(player);
            if(keySequenceOptional.isPresent() && keySequenceOptional.get() instanceof NamedKeySequence) {
                return ((NamedKeySequence) keySequenceOptional.get()).getName();
            }
        }

        return null;
    }
}
