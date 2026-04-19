package com.sc2002.arena.strategy;

import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;

/**
 * Strategy interface for enemy AI behaviour.
 *
 * Responsibilities:
 * - decide what action an enemy should perform
 * - decide which target that action should be applied to
 *
 * Design notes:
 * - Demonstrates OCP: new enemy AI styles can be added without modifying
 *   Enemy, TurnManager, or BattleEngine.
 * - Keeps enemy behaviour modular and swappable.
 */
public interface EnemyActionStrategy {

    /**
     * Decide which action the enemy should perform this turn.
     *
     * @param enemy the acting enemy
     * @param context battle state information exposed through BattleContext
     * @return chosen action
     */
    CombatAction selectAction(Enemy enemy, BattleContext context);

    /**
     * Decide which combatant the enemy should target.
     *
     * @param enemy the acting enemy
     * @param context battle state information exposed through BattleContext
     * @return chosen target
     */
    Combatant selectTarget(Enemy enemy, BattleContext context);
}