package com.sc2002.arena.effect;

public final class DragonBreathCriticalEffect extends AbstractCriticalEffect {
    private static final int MULTIPLIER_PERCENT = 150;

    public DragonBreathCriticalEffect() {
        super("Dragon Breath Critical", MULTIPLIER_PERCENT);
    }
}
