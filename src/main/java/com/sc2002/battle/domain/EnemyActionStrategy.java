package com.sc2002.battle.domain;
import sc2002.battle.domain.CombatAction;
import sc2002.battle.domain.Enemy;
import sc2002.battle.domain.BattleContext;
import sc2002.battle.domain.Combatant;
public interface EnemyActionStrategy {
    CombatAction selectAction(Enemy enemy, BattleContext context);

    Combatant selectTarget(Enemy enemy, BattleContext context);
}
