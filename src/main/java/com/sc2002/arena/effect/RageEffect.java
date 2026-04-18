package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

public final class RageEffect extends StatusEffect {
    private static final double CRITICAL_CHANCE_BONUS = 0.50;

    private int remainingTurnEnds = 3;

    public RageEffect() {
        super("Rage");
    }

    @Override
    public double modifyCriticalChance(Combatant target, double criticalChance, BattleContext context) {
        return criticalChance + CRITICAL_CHANCE_BONUS;
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
