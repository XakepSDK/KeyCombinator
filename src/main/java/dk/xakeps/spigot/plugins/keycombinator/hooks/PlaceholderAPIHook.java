package dk.xakeps.spigot.plugins.keycombinator.hooks;

import dk.xakeps.spigot.plugins.keycombinator.KeyCombinator;
import dk.xakeps.spigot.plugins.keycombinator.models.KeySequence;
import dk.xakeps.spigot.plugins.keycombinator.utils.SequenceUtils;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.StringJoiner;

public class PlaceholderAPIHook extends EZPlaceholderHook {
    public PlaceholderAPIHook() {
        super(KeyCombinator.getInstance(), "keycombinator");
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(identifier.equals("keys_pressed")) {
            return KeyCombinator.getInstance().getPlayerKeysPressed().get(player).toString();
        }

        if(identifier.equals("sequences_passed")) {
            return getPassedSequences(player);
        }

        if(identifier.equals("sequence")) {
            return getPassedSequences(player);
        }

        return null;
    }

    private String getPassedSequences(Player player) {
        KeyCombinator instance = KeyCombinator.getInstance();
        List<KeySequence> sequences = SequenceUtils.getPassed(instance.getSequences(), instance.getPlayerKeysPressed().get(player));
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        sequences.forEach(keySequence -> {
            joiner.add(keySequence.getName());
        });
        return joiner.toString();
    }
}
