package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;

public class BasicAttackOnlyStrategy implements EnemyActionStrategy {
    @Override
    public Action chooseAction(Enemy self, List<Combatant> possibleTargets) {
        return new BasicAttackAction();
    }
}