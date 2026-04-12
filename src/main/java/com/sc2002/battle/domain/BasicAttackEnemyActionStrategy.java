package com.sc2002.battle.domain;
import sc2002.battle.domain.EnemyActionStrategy;
import sc2002.battle.domain.CombatAction;
import sc2002.battle.domain.Enemy;
import sc2002.battle.domain.BattleContext;
import sc2002.battle.domain.BasicAttackAction;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.BattleContext;
import java.util.List;

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
