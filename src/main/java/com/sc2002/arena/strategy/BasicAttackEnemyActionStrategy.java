package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;

/**
 * Simple enemy strategy used by standard enemies such as Goblins and Wolves.
 *
 * Behaviour:
 * - always chooses BasicAttackAction
 * - targets the first available living opponent
 *
 * Design notes:
 * - Represents the baseline enemy AI required by the assignment.
 * - Small and focused, making it easy to replace or extend in future.
 */
public final class BasicAttackEnemyActionStrategy implements EnemyActionStrategy {

    /**
     * Standard enemies always perform a basic attack.
     */
    @Override
    public CombatAction selectAction(Enemy enemy, BattleContext context) {
        return new BasicAttackAction();
    }

    /**
     * Select the first living opponent as the target.
     *
     * In the current project this is sufficient because the game has
     * straightforward enemy targeting rules.
     */
    @Override
    public Combatant selectTarget(Enemy enemy, BattleContext context) {
        List<Combatant> targets = context.getLivingOpponentsOf(enemy);

        if (targets.isEmpty()) {
            throw new IllegalStateException("No valid targets available for enemy action.");
        }

        return targets.getFirst();
    }
}