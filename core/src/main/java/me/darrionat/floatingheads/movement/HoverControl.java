package me.darrionat.floatingheads.movement;

public class HoverControl {
    private final double radius;
    private final double speed;

    /**
     * Constructs a new {@link HoverControl}.
     *
     * @param radius The vertical radius of a hover movement
     * @param speed  The blocks per second of a hover movement
     */
    public HoverControl(double radius, double speed) {
        this.radius = radius;
        this.speed = speed;
    }

    /**
     * Gets the vertical radius of this movement
     *
     * @return The vertical radius in blocks of this movement
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Gets the blocks per second of this movement.
     *
     * @return Blocks per second of this movement
     */
    public double getSpeed() {
        return speed;
    }
}