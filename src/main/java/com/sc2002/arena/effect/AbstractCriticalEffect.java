package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.strategy.BattleContext;

/**
 * Base class for effects that modify attack using a percentage multiplier.
 *
 * Responsibilities:
 * - apply attack scaling based on multiplier percentage
 * - manage effect duration through turn-based expiration
 *
 * Design notes:
 * - Extends StatusEffect to provide reusable critical/buff-like behaviour
 *   for multiple concrete effect implementations.
 */
public abstract class AbstractCriticalEffect extends StatusEffect {

    /** Percentage multiplier applied to attack value. */
    private final int multiplierPercent;

    /** Tracks remaining turns before effect expires. */
    private int remainingTurnEnds = 1;

    /**
     * Constructs a critical-style effect with an attack multiplier.
     *
     * @param name effect name
     * @param multiplierPercent attack multiplier percentage
     */
    protected AbstractCriticalEffect(String name, int multiplierPercent) {
        super(name);
        if (multiplierPercent <= 0) {
            throw new IllegalArgumentException("multiplierPercent must be greater than 0.");
        }
        this.multiplierPercent = multiplierPercent;
    }

    /**
     * Modifies attack value using percentage multiplier.
     */
    @Override
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return Math.max(0, attack * multiplierPercent / 100);
    }

    /**
     * Reduces remaining duration at end of each turn.
     */
    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnEnds > 0) {
            remainingTurnEnds--;
        }
    }

    /**
     * @return true if effect duration has ended
     */
    @Override
    public boolean isExpired() {
        return remainingTurnEnds <= 0;
    }
}