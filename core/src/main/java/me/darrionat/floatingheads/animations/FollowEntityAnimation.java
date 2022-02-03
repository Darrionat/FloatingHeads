package me.darrionat.floatingheads.animations;

import me.darrionat.floatingheads.FloatingHead;
import me.darrionat.floatingheads.movement.HoverControl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Represents a {@link FloatingHead} continually following an entity.
 */
public class FollowEntityAnimation extends Animation {
    /**
     * If the distance between the entity and the FloatingHead is greater than this value, the FloatingHead will
     * teleport to the entity.
     */
    public static final double TELEPORT_DISTANCE = 32;
    private final Plugin plugin;
    private final Entity entity;
    private final double minFollowDistance;
    /**
     * Blocks per second.
     */
    private final double moveBlocksPerTick;
    private final boolean hover;
    private double hoverRadius;
    private double hoverBlocksPerTick;
    private int id;

    /**
     * Denotes the current direction. If increasing Y, then {@code true}; otherwise {@code false}.
     */
    private boolean upward = true;

    // TODO JAVA DOCS

    /**
     * Constructs a new {@link FollowEntityAnimation}. This will cause a {@link FloatingHead} to both follow an entity
     * and hover when nearby it.
     *
     * @param floatingHead      The FloatingHead to act on
     * @param entity            The entity to follow
     * @param minFollowDistance Once an entity is this far away from the {@code floatingHead}, then the {@code
     *                          floatingHead} will move towards the entity
     * @param moveSpeed         How many blocks per second the {@code floatingHead} should move
     */
    public FollowEntityAnimation(FloatingHead floatingHead, Entity entity, double minFollowDistance, double moveSpeed) {
        this(floatingHead, entity, minFollowDistance, moveSpeed, null);
    }

    /**
     * Constructs a new {@link FollowEntityAnimation}. This will cause a {@link FloatingHead} to both follow an entity
     * and hover when nearby it.
     *
     * @param floatingHead      The FloatingHead to act on
     * @param entity            The entity to follow
     * @param minFollowDistance Once an entity is this far away from the {@code floatingHead}, then the {@code
     *                          floatingHead} will move towards the entity
     * @param moveSpeed         How many blocks per second the {@code floatingHead} should move
     */
    public FollowEntityAnimation(FloatingHead floatingHead, Entity entity, double minFollowDistance, double moveSpeed, HoverControl hoverControl) {
        super(floatingHead);
        this.plugin = floatingHead.getPlugin();
        this.entity = entity;
        this.minFollowDistance = minFollowDistance;
        this.moveBlocksPerTick = moveSpeed / 20;
        this.hover = hoverControl != null;
        if (this.hover) {
            this.hoverRadius = hoverControl.getRadius();
            this.hoverBlocksPerTick = hoverControl.getSpeed() / 20;
        }
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    protected void runAnimation() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Location headLoc = floatingHead.getLocation();
            Location entityLoc = entity.getLocation();
            // Calculate distance and points for teleportation locations
            double distance = headLoc.distance(entityLoc);

            if (distance >= minFollowDistance) {
                if (distance > TELEPORT_DISTANCE) {
                    floatingHead.setLocation(entityLoc);
                } else {
                    moveTowardsEntity(headLoc, entityLoc, distance);
                }
            } else if (hover) {
                double originY = entityLoc.getY();
                double yLoc = headLoc.getY();
                // Change directions
                if (upward && yLoc >= originY + hoverRadius) {
                    upward = false;
                } else if (!upward && yLoc <= originY - hoverRadius) {
                    upward = true;
                }
                // Move vertically
                HoverAnimation.step(floatingHead, upward, hoverBlocksPerTick);
            }

        }, 0, 1l); // delay, period
    }

    private void moveTowardsEntity(Location start, Location destination, double distance) {
        // To vectors
        Vector startVec = start.toVector(), destinationVec = destination.toVector();
        // Get the yaw between the points for correct directional facing
        Vector difference = destinationVec.subtract(startVec);
        float yaw = start.setDirection(difference).getYaw();

        double points = Math.ceil(distance / moveBlocksPerTick);
        // Scales the difference so that it fits within the overall difference an amount of times equal to points
        difference.multiply(1 / points);

        Location loc = floatingHead.getLocation();
        loc.setYaw(yaw);
        floatingHead.setLocation(loc.add(difference));
    }

    @Override
    protected void stopAnimation() {
        Bukkit.getScheduler().cancelTask(id);
    }
}
