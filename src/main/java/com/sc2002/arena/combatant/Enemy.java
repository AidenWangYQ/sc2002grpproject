package com.sc2002.arena.combatant;

import java.util.Objects;

import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.strategy.BattleContext;
import com.sc2002.arena.strategy.EnemyActionStrategy;

/**
 * Abstract base class for all AI-controlled opponents.
 *
 * Responsibilities:
 * - inherit shared combat behaviour from Combatant
 * - delegate decision-making (what action to take / who to target)
 *   to an EnemyActionStrategy
 *
 * Design notes:
 * - This uses the Strategy pattern so enemy behaviour can vary without
 *   modifying the Enemy class itself.
 * - BattleEngine / TurnManager do not need to hardcode per-enemy behaviour.
 */
public abstract class Enemy extends Combatant {
    /** Strategy object that decides enemy action and target selection. */
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

    /** @return the strategy object currently controlling this enemy's AI */
    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }

    /**
     * Ask the strategy which action this enemy should perform this turn.
     */
    public CombatAction chooseAction(BattleContext context) {
        return actionStrategy.selectAction(this, context);
    }

    /**
     * Ask the strategy which target this enemy should act on.
     */
    public Combatant chooseTarget(BattleContext context) {
        return actionStrategy.selectTarget(this, context);
    }
}