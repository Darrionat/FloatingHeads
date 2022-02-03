package me.darrionat.floatingheads.movement;

public class MovementControl {
    private final boolean byTime;
    private final double blocksPerTick;

    /**
     * @param initSpeed
     * @param completeTime
     */
    public MovementControl(double initSpeed, double completeTime) {
        byTime = true;
        blocksPerTick = 0;
    }

    /**
     * @param speed The blocks per second of movement
     */
    public MovementControl(double speed) {
        this.blocksPerTick = speed / 20;
        byTime = false;
    }

    public double getBlocksPerTick(double seconds) {
        if (byTime) {

        }
        return blocksPerTick;
    }
}
