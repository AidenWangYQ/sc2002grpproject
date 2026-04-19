package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.action.UseSpecialSkillAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.SpecialSkillUser;

/**
 * Boss enemy strategy for Dragon behaviour.
 *
 * Behaviour:
 * - if the Dragon's special skill is available, use it
 * - otherwise fall back to a normal basic attack
 * - target the first available living opponent
 *
 * Design notes:
 * - Demonstrates how different enemy behaviour can be introduced purely
 *   through strategy replacement, without modifying the Enemy class.
 * - Also shows how the SpecialSkillUser capability is used only where relevant.
 */
public final class DragonEnemyActionStrategy implements EnemyActionStrategy {

    /**
     * Choose between Dragon Breath and BasicAttack depending on cooldown.
     */
    @Override
    public CombatAction selectAction(Enemy enemy, BattleContext context) {
        if (enemy instanceof SpecialSkillUser skillUser && skillUser.getSpecialSkillCooldown() == 0) {
            return new UseSpecialSkillAction();
        }

        return new BasicAttackAction();
    }

    /**
     * Select the first living opponent as the target.
     *
     * For Dragon Breath, the skill itself may hit multiple targets, but the
     * targeting contract is still preserved here for consistency.
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