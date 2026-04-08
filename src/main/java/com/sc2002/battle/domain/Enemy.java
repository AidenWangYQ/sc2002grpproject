package sc2002.battle.domain;

import java.util.Objects;

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
