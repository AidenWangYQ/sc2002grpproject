package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

public final class StunEffect extends StatusEffect {
    private int remainingTurnsToSkip = 2;

    public StunEffect() {
        super("Stun");
    }

    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnsToSkip > 0) {
            remainingTurnsToSkip--;
        }
    }

    @Override
    public boolean preventsAction() {
        return remainingTurnsToSkip > 0;
    }

    @Override
    public boolean isExpired() {
        return remainingTurnsToSkip <= 0;
    }
}
