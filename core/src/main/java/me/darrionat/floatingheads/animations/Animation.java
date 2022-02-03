package me.darrionat.floatingheads.animations;

import me.darrionat.floatingheads.FloatingHead;

import java.util.Objects;

/**
 * Represents a dynamic movement or action that is performed on a {@link FloatingHead}.
 * <p>
 * Animations can have a follow-up animation that is run once they are complete. This allows animations to be run in
 * sequence.
 *
 * @see #setOnCompleteAnimation(Animation)
 */
public abstract class Animation {
    /**
     * The {@link FloatingHead} that is being affected by this animation.
     */
    protected FloatingHead floatingHead;
    private Animation onComplete;
    private boolean running = false;

    /**
     * Constructs a new {@link Animation}.
     *
     * @param floatingHead The {@link FloatingHead} to be affected by this animation.
     */
    public Animation(FloatingHead floatingHead) {
        Objects.requireNonNull(floatingHead);
        this.floatingHead = floatingHead;
    }

    /**
     * Gets the {@link FloatingHead} that this animation belongs to.
     *
     * @return The FloatingHead this animation belongs to.
     */
    protected FloatingHead getFloatingHead() {
        return floatingHead;
    }

    /**
     * Determines if the animation is complete. Continuous animations never finish.
     *
     * @return {@code true} if the animation is finished; {@code false} otherwise
     */
    public abstract boolean finished();

    /**
     * Runs the animation. Does nothing if the animation is running.
     */
    public final void run() {
        if (running) return;
        running = true;
        runAnimation();
    }

    /**
     * Runs the animation.
     */
    protected abstract void runAnimation();

    /**
     * Stops the animation. Does nothing if the animation is not running.
     */
    public final void stop() {
        if (!finished() || running)
            stopAnimation();
        running = false;
        // Run onComplete animation
        if (onComplete == null)
            return;
        floatingHead.animate(onComplete, null);
    }

    /**
     * Stops the animation.
     */
    protected abstract void stopAnimation();

    /**
     * Gets the animation that will run when this animation is stopped.
     *
     * @return The animation that runs after this animation.
     * @see #stop()
     */
    public Animation getOnCompleteAnimation() {
        return onComplete;
    }

    /**
     * Sets the animation to be run after this animation is stopped.
     *
     * @param onComplete The animation to run once this animation is stopped.
     * @see #stop()
     */
    public void setOnCompleteAnimation(Animation onComplete) {
        this.onComplete = onComplete;
    }
}