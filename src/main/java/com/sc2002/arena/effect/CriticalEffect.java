package com.sc2002.arena.effect;

public final class CriticalEffect extends AbstractCriticalEffect {
    private static final int MULTIPLIER_PERCENT = 250;

    public CriticalEffect() {
        super("Critical", MULTIPLIER_PERCENT);
    }
}
