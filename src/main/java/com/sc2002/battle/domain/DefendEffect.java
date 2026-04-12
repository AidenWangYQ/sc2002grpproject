package com.sc2002.battle.domain;
import sc2002.battle.domain.AbstractStatusEffect;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.BattleContext;
import sc2002.battle.domain.Player;
public final class DefendEffect extends AbstractStatusEffect {
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
