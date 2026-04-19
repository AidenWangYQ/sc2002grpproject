package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

/**
 * A temporary buff effect that increases critical hit chance.
 *
 * Responsibilities:
 * - increase critical hit chance by a fixed amount
 * - expire after a limited number of turns
 */
public final class RageEffect extends StatusEffect {

    /** Flat bonus added to critical chance. */
    private static final double CRITICAL_CHANCE_BONUS = 0.50;

    /** Tracks remaining turns before the effect expires. */
    private int remainingTurnEnds = 3;

    public RageEffect() {
        super("Rage");
    }

    /**
     * Increases critical hit chance by a fixed bonus.
     */
    @Override
    public double modifyCriticalChance(Combatant target, double criticalChance, BattleContext context) {
        return criticalChance + CRITICAL_CHANCE_BONUS;
    }

    /**
     * Reduces remaining duration each turn.
     */
    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnEnds > 0) {
            remainingTurnEnds--;
        }
    }

    /** @return true if the effect has expired */
    @Override
    public boolean isExpired() {
        return remainingTurnEnds <= 0;
    }
}