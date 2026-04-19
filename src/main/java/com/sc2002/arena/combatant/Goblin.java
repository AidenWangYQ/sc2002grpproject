package com.sc2002.arena.combatant;

import com.sc2002.arena.strategy.BasicAttackEnemyActionStrategy;

/**
 * Standard enemy type with fixed Goblin stats.
 *
 * Behaviour:
 * - always uses the basic attack strategy
 *
 * This class is intentionally small because all shared logic is inherited
 * from Enemy/Combatant, while behaviour is delegated to the strategy object.
 */
public final class Goblin extends Enemy {
    /** Convenience constructor with default name. */
    public Goblin() {
        this("Goblin");
    }

    /**
     * Named constructor is useful when spawning multiple Goblins
     * such as "Goblin A", "Goblin B", etc.
     */
    public Goblin(String name) {
        super(name, 55, 35, 15, 25, new BasicAttackEnemyActionStrategy());
    }
}