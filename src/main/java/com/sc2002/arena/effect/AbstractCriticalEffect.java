package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.strategy.BattleContext;

public abstract class AbstractCriticalEffect extends StatusEffect {
    private final int multiplierPercent;
    private int remainingTurnEnds = 1;

    protected AbstractCriticalEffect(String name, int multiplierPercent) {
        super(name);
        if (multiplierPercent <= 0) {
            throw new IllegalArgumentException("multiplierPercent must be greater than 0.");
        }
        this.multiplierPercent = multiplierPercent;
    }

    @Override
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return Math.max(0, attack * multiplierPercent / 100);
    }

    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnEnds > 0) {
            remainingTurnEnds--;
        }
    }

    @Override
    public boolean isExpired() {
        return remainingTurnEnds <= 0;
    }
}
