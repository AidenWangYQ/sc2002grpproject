package com.sc2002.arena.effect;

/**
 * A critical-style effect used for Dragon Breath attacks.
 *
 * Responsibilities:
 * - define a fixed critical multiplier specific to Dragon Breath
 * - reuse AbstractCriticalEffect logic for attack modification
 */
public final class DragonBreathCriticalEffect extends AbstractCriticalEffect {

    /** Critical multiplier percentage for Dragon Breath. */
    private static final int MULTIPLIER_PERCENT = 150;

    public DragonBreathCriticalEffect() {
        super("Dragon Breath Critical", MULTIPLIER_PERCENT);
    }
}