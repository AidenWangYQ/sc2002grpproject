package com.sc2002.arena.strategy;

import com.sc2002.arena.action.CombatAction;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;


public interface EnemyActionStrategy {
    CombatAction selectAction(Enemy enemy, BattleContext context);

    Combatant selectTarget(Enemy enemy, BattleContext context);
}
