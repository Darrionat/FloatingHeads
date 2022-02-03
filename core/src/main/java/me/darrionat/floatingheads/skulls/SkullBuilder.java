package me.darrionat.floatingheads.skulls;

import dev.dbassett.skullcreator.SkullCreator;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Represents an container object for an ItemStack that is a skull.
 * <p>
 * This class utilizes the SkullCreator API created by deanveloper.
 *
 * @author Darrionat
 * @author deanveloper
 */
public class SkullBuilder {
    private final ItemStack skull;

    private SkullBuilder(ItemStack skull) {
        this.skull = skull;
    }

    /**
     * Creates a player skull item with the skin based on a player's UUID.
     *
     * @param uuid The Player's UUID.
     * @return A SkullBuilder object with a skull contained within it.
     */
    public static SkullBuilder skullFromUuid(UUID uuid) {
        return new SkullBuilder(SkullCreator.itemFromUuid(uuid));
    }

    /**
     * Creates a player skull item with the skin at a Mojang URL.
     *
     * @param url The Mojang URL.
     * @return A SkullBuilder object with a skull contained within it.
     */
    public static SkullBuilder skullFromUrl(String url) {
        return new SkullBuilder(SkullCreator.itemFromUrl(url));
    }

    /**
     * Creates a player skull item with the skin based on a base64 string.
     *
     * @param base64 The Mojang URL.
     * @return A SkullBuilder object with a skull contained within it.
     */
    public static SkullBuilder skullFromBase64(String base64) {
        return new SkullBuilder(SkullCreator.itemFromBase64(base64));
    }

    /**
     * Gets the skull as an ItemStack.
     *
     * @return The skull of a player head.
     */
    public ItemStack getSkull() {
        return skull;
    }
}