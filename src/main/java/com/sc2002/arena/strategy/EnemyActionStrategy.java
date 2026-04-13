package com.sc2002.arena.strategy;

import com.sc2002.arena.action.*;
import com.sc2002.arena.combatant.*;


public interface EnemyActionStrategy {
    CombatAction selectAction(Enemy enemy, BattleContext context);

    Combatant selectTarget(Enemy enemy, BattleContext context);
}
