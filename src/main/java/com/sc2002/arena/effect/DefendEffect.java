package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

/**
 * A defensive status effect that increases defense for a limited duration.
 *
 * Responsibilities:
 * - apply a flat defense bonus
 * - expire after a fixed number of rounds
 */
public final class DefendEffect extends StatusEffect {

    /** Flat defense bonus granted by this effect. */
    private static final int DEFENSE_BONUS = 10;

    /** Tracks remaining rounds before the effect expires. */
    private int remainingRoundEnds = 2;

    public DefendEffect() {
        super("Defend");
    }

    /**
     * Increases defense by a fixed amount.
     */
    @Override
    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense + DEFENSE_BONUS;
    }

    /**
     * Decrements remaining duration at the end of each round.
     */
    @Override
    public void onRoundEnd(Combatant target, BattleContext context) {
        if (remainingRoundEnds > 0) {
            remainingRoundEnds--;
        }
    }

    /** @return true if the effect has expired */
    @Override
    public boolean isExpired() {
        return remainingRoundEnds <= 0;
    }
}