package com.sc2002.battle.domain;

import java.util.Objects;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.CombatAction;
import sc2002.battle.domain.EnemyActionStrategy;
import sc2002.battle.domain.BattleContext;
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
