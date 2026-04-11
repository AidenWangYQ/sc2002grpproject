package com.sc2002.battle.domain;

public interface EnemyActionStrategy {
    CombatAction selectAction(Enemy enemy, BattleContext context);

    Combatant selectTarget(Enemy enemy, BattleContext context);
}
