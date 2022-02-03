package me.darrionat.floatingheads.animations;

import me.darrionat.floatingheads.FloatingHead;
import me.darrionat.floatingheads.movement.HoverControl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * Represents a {@link FloatingHead} staying in the same X and Z coordinates and "hovering" by changing its Y position.
 */
public class HoverAnimation extends Animation {
    private final Plugin plugin;
    private final FloatingHead floatingHead;
    private final double originY;
    private final double maxY;
    private final double minY;
    private final double blocksPerTick;
    /**
     * Denotes the current direction. If increasing Y, then {@code true}; otherwise {@code false}.
     */
    private boolean upward = true;

    private int id = -1;

    /**
     * Constructs a new HoverAnimation for a FloatingHead.
     *
     * @param floatingHead The FloatingHead to act on.
     * @param radius       The maximum radius from the original Y location.
     * @param speed        Blocks per second the FloatingHead will move.
     */
    public HoverAnimation(FloatingHead floatingHead, HoverControl settings) {
        super(floatingHead);
        this.floatingHead = floatingHead;
        this.plugin = floatingHead.getPlugin();
        this.originY = floatingHead.getLocation().getY();
        double radius = settings.getRadius();
        this.maxY = originY + radius;
        this.minY = originY - radius;
        this.blocksPerTick = settings.getSpeed() / 20;
    }

    /**
     * Does a step in the hover animation.
     *
     * @param floatingHead The floating head in the animation
     * @param upward       {@code true} if moving upwards
     * @param blockDiff    The amount of blocks to move vertically
     */
    protected static void step(FloatingHead floatingHead, boolean upward, double blockDiff) {
        Location loc = floatingHead.getLocation();
        double y = loc.getY();
        double newY = y;
        if (upward)
            newY += blockDiff;
        else
            newY -= blockDiff;
        Location newLoc = new Location(loc.getWorld(), loc.getX(), newY, loc.getZ(), loc.getYaw(), loc.getPitch());
        floatingHead.setLocation(newLoc);
    }

    public int getTaskId() {
        return id;
    }

    @Override
    public boolean finished() {
        return false;
    }

    @Override
    public void runAnimation() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            Location loc = floatingHead.getLocation();
            double yLoc = loc.getY();
            if (upward && yLoc >= maxY) {
                upward = false;
            }
            if (!upward && yLoc <= minY) {
                upward = true;
            }
            step(floatingHead, upward, blocksPerTick);
        }, 0, 1l); // delay, period
    }

    @Override
    protected void stopAnimation() {
        Bukkit.getScheduler().cancelTask(id);
    }
}