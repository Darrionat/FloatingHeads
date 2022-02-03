package me.darrionat.floatingheads.animations;

import me.darrionat.floatingheads.FloatingHead;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

/**
 * Represents a {@link FloatingHead} moving to a new location.
 */
public class MoveAnimation extends Animation {
    private final Plugin plugin;
    private final Location destination;
    private final double seconds;

    private boolean finished = true;
    private int step = 0;
    private int id;

    /**
     * Constructs a new HoverAnimation for a FloatingHead.
     *
     * @param floatingHead The FloatingHead to act on
     * @param destination  The location that the FloatingHead will be targeted towards
     * @param seconds      Amount of seconds to travel to the destination
     */
    public MoveAnimation(FloatingHead floatingHead, Location destination, double seconds) {
        super(floatingHead);
        this.plugin = floatingHead.getPlugin();
        this.destination = destination;
        this.seconds = seconds;
    }

    public int getTaskId() {
        if (finished)
            return -1;
        return id;
    }

    @Override
    public boolean finished() {
        return finished;
    }

    @Override
    protected void runAnimation() {
        // Inits variables to prevent bugs
        finished = false;
        step = 0;
        Location start = floatingHead.getLocation();
        // To vectors
        Vector startVec = start.toVector(), destinationVec = destination.toVector();
        // Get the yaw between the points for correct directional facing
        Vector difference = destinationVec.subtract(startVec);
        float yaw = start.setDirection(difference).getYaw();

        // Calculate distance and points for teleportation locations
        double distance = start.distance(destination);
        double blocksPerTick = distance / (seconds * 20);
        double points = Math.ceil(distance / blocksPerTick);
        // Scales the difference so that it fits within the overall difference an amount of times equal to points
        difference.multiply(1 / points);

        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

            if (step > points) {
                finished = true;
                stop();
                return;
            }
            Location loc = floatingHead.getLocation();
            // only needs done once
            if (step == 0)
                loc.setYaw(yaw);
            // Location newLoc = new Location(world, loc.getX(), loc.getY(), loc.getZ(), yaw, loc.getPitch());
            // floatingHead.setLocation(newLoc);
            floatingHead.setLocation(loc.add(difference));
            step++;
        }, 0, 1l); // delay, period
    }

    @Override
    protected void stopAnimation() {
        Bukkit.getScheduler().cancelTask(id);
        finished = true;
    }
}