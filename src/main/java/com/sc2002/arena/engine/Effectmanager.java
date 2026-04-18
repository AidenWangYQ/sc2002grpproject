package com.sc2002.arena.engine;

import com.sc2002.arena.combatant.Combatant;

public class EffectManager {
    public void applyEffectsOnTurnStart(Combatant combatant, BattleContext context) {
        combatant.onTurnStart(context);
        combatant.removeExpiredEffects();
    }

    public void applyEffectsOnRoundEnd(Combatant combatant, BattleContext context) {
        combatant.onRoundEnd(context);
        combatant.removeExpiredEffects();
    }
}
