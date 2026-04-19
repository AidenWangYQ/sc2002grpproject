package com.sc2002.arena.combatant;

import com.sc2002.arena.strategy.BasicAttackEnemyActionStrategy;

/**
 * Standard enemy type with higher speed and attack than Goblin,
 * but lower defense.
 *
 * Behaviour:
 * - uses the same basic attack strategy as Goblin
 *
 * This shows that enemy stats and enemy behaviour are separate concerns:
 * two enemies may share AI behaviour but differ in combat attributes.
 */
public final class Wolf extends Enemy {
    /** Convenience constructor with default name. */
    public Wolf() {
        this("Wolf");
    }

    /**
     * Named constructor is useful when multiple Wolves are spawned.
     */
    public Wolf(String name) {
        super(name, 40, 45, 5, 35, new BasicAttackEnemyActionStrategy());
    }
}