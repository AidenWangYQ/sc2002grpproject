package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

public final class DefendEffect extends StatusEffect {
    private static final int DEFENSE_BONUS = 10;
    private int remainingRoundEnds = 2;

    public DefendEffect() {
        super("Defend");
    }

    @Override
    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense + DEFENSE_BONUS;
    }

    @Override
    public void onRoundEnd(Combatant target, BattleContext context) {
        if (remainingRoundEnds > 0) {
            remainingRoundEnds--;
        }
    }

    @Override
    public boolean isExpired() {
        return remainingRoundEnds <= 0;
    }
}
