package com.sc2002.arena.combatant;

import java.util.*;
import com.sc2002.arena.action.*;
import com.sc2002.arena.strategy.BattleContext;
import com.sc2002.arena.strategy.EnemyActionStrategy;


public abstract class Enemy extends Combatant {
    private final EnemyActionStrategy actionStrategy;

    protected Enemy(
            String name,
            int maxHp,
            int baseAttack,
            int baseDefense,
            int speed,
            EnemyActionStrategy actionStrategy
    ) {
        super(name, maxHp, baseAttack, baseDefense, speed);
        this.actionStrategy = Objects.requireNonNull(actionStrategy, "actionStrategy cannot be null");
    }

    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }

    public CombatAction chooseAction(BattleContext context) {
        return actionStrategy.selectAction(this, context);
    }

    public Combatant chooseTarget(BattleContext context) {
        return actionStrategy.selectTarget(this, context);
    }
}
