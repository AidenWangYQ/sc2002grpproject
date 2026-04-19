package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

/**
 * A status effect that prevents a combatant from acting for a limited duration.
 *
 * Responsibilities:
 * - block action execution while active
 * - expire after a fixed number of turns
 */
public final class StunEffect extends StatusEffect {

    /** Tracks remaining turns during which actions are skipped. */
    private int remainingTurnsToSkip = 2;

    public StunEffect() {
        super("Stun");
    }

    /**
     * Reduces stun duration at the end of each turn.
     */
    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnsToSkip > 0) {
            remainingTurnsToSkip--;
        }
    }

    /**
     * @return true if the combatant is currently stunned
     */
    @Override
    public boolean preventsAction() {
        return remainingTurnsToSkip > 0;
    }

    /**
     * @return true if stun duration has ended
     */
    @Override
    public boolean isExpired() {
        return remainingTurnsToSkip <= 0;
    }
}