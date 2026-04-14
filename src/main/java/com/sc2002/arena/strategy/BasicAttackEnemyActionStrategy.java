package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.CombatAction;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;

public final class BasicAttackEnemyActionStrategy implements EnemyActionStrategy {
    @Override
    public CombatAction selectAction(Enemy enemy, BattleContext context) {
        return new BasicAttackAction();
    }

    @Override
    public Combatant selectTarget(Enemy enemy, BattleContext context) {
        List<Combatant> targets = context.getAliveEnemiesOf(enemy);
        if (targets.isEmpty()) {
            throw new IllegalStateException("No valid targets available for enemy action.");
        }
        return targets.getFirst();
    }
}
