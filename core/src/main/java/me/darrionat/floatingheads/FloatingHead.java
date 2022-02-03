package me.darrionat.floatingheads;

import me.darrionat.floatingheads.animations.Animation;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

/**
 * Represents an invisible floating {@link ArmorStand} with a skull item. A FloatingHead can only have one texture.
 * <p>
 * FloatingHeads can be animated and they can have their locations, size, and visibility changed.
 *
 * @see #animate(Animation, Animation)
 * @see #setLocation(Location)
 * @see #setSmall(boolean)
 * @see #setVisible(boolean)
 */
public class FloatingHead {
    private final Plugin plugin;
    private final ItemStack skullItem;
    private ArmorStand stand;
    /**
     * Determines if the skull size is small or not.
     */
    private boolean small = true;
    /**
     * Determines visibility of the skull item. The armor stand will always be invisible.
     */
    private boolean visible = true;
    /**
     * The current animation of this FloatingHead.
     */
    private Animation currentAnimation;

    /**
     * Constructs a new {@link FloatingHead}.
     *
     * @param plugin The plugin this FloatingHead belongs to
     * @param skull  The to be the skull of an invisible ArmorStand
     */
    public FloatingHead(Plugin plugin, ItemStack skull) {
        Objects.requireNonNull(plugin);
        Objects.requireNonNull(skull);
        this.plugin = plugin;
        this.skullItem = skull;
    }

    /**
     * Constructs and creates a new {@link FloatingHead}.
     *
     * @param plugin The plugin this FloatingHead belongs to
     * @param skull  The to be the skull of an invisible ArmorStand
     * @param loc    The location to be spawned into
     */
    public FloatingHead(Plugin plugin, ItemStack skull, Location loc) {
        this(plugin, skull);
        create(loc);
    }

    /**
     * Gets the Plugin this FloatingHead belongs to.
     *
     * @return The plugin this FloatingHead belongs to
     */
    public Plugin getPlugin() {
        return plugin;
    }

    // will set this to visible

    /**
     * Spawns this FloatingHead. This method also makes this FloatingHead visible by consequence.
     *
     * @param loc The location to be spawned at
     * @see #setVisible(boolean)
     */
    public void create(Location loc) {
        if (exists())
            remove();
        stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setSmall(small);
        setVisible(true);
    }

    /**
     * Removes this FloatingHead from the world.
     *
     * @throws NullPointerException Thrown when this FloatingHead does not exist
     * @see #exists()
     */
    public void remove() {
        if (!exists())
            throw new NullPointerException("FloatingHead does not exist");
        stand.remove();
        stand = null;
    }

    /**
     * Determines if this FloatingHead exists.
     *
     * @return {@code true} if spawned in; otherwise {@code false}
     */
    public boolean exists() {
        return stand != null;
    }

    private void setSkull(ItemStack skull) {
        stand.getEquipment().setHelmet(skull);
    }

    /**
     * Determines if the ArmorStand is small.
     *
     * @return {@true} if the stand is small; otherwise {@code false}
     */
    public boolean isSmall() {
        return small;
    }

    /**
     * Sets the ArmorStand to small or normally sized
     *
     * @param small If {@code true}, the ArmorStand will be small.
     */
    public void setSmall(boolean small) {
        this.small = small;
        if (stand != null)
            stand.setSmall(small);
    }

    /**
     * Gets the location of this {@link FloatingHead}.
     *
     * @return The location of this FloatingHead
     */
    public Location getLocation() {
        return stand.getLocation();
    }

    /**
     * Teleport to a new location.
     *
     * @param loc The location to teleport to
     */
    public void setLocation(Location loc) {
        stand.teleport(loc);
    }

    /**
     * Determines if this {@link FloatingHead} is visible.
     *
     * @return {@code true} if visible; otherwise {@code false}
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets the visibility of the ArmorStand.
     *
     * @param visible The visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible) {
            setSkull(skullItem);
        } else {
            setSkull(null);
        }
    }

    /**
     * Determines if this {@link FloatingHead} is animated.
     *
     * @return {@true} if there is a current animation that is not finished; otherwise {@code false}
     */
    public boolean isAnimated() {
        return currentAnimation != null && !currentAnimation.finished();
    }

    /**
     * Gets the current animation
     *
     * @return The current animation
     */
    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

    /**
     * Sets the current animation. This will stop all previous animations.
     *
     * @param animation The animation to set
     */
    public void animate(Animation animation) {
        animate(animation, null);
    }

    /**
     * Sets the current animation and a follow-up animation. This will stop all previous animations.
     *
     * @param animation  The animation to set
     * @param onComplete An animation to run once {@code animation} is finished.
     */
    public void animate(Animation animation, Animation onComplete) {
        if (isAnimated())
            stopAnimation();
        currentAnimation = animation;
        animation.setOnCompleteAnimation(onComplete);
        animation.run();
    }

    /**
     * Stops the current animation and prevents an on -complete animation from occurring.
     * <p>
     * To stop the current animation without preventing its on-complete animation, utilize {@code
     * getCurrentAnimation().stop()}.
     *
     * @see #getCurrentAnimation()
     */
    public void stopAnimation() {
        currentAnimation.setOnCompleteAnimation(null);
        currentAnimation.stop();
        currentAnimation = null;
    }
}