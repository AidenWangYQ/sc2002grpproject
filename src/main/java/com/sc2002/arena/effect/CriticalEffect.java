package com.sc2002.arena.effect;

/**
 * A critical hit effect that increases attack using a fixed multiplier.
 *
 * Responsibilities:
 * - define a predefined critical multiplier value
 * - reuse logic from AbstractCriticalEffect
 */
public final class CriticalEffect extends AbstractCriticalEffect {

    /** Critical hit multiplier percentage. */
    private static final int MULTIPLIER_PERCENT = 250;

    /**
     * Constructs a critical effect with fixed multiplier.
     */
    public CriticalEffect() {
        super("Critical", MULTIPLIER_PERCENT);
    }
}