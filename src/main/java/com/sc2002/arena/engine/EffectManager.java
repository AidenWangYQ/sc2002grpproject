package com.sc2002.arena.engine;

import com.sc2002.arena.combatant.Combatant;
/**
 * Utility class that applies status effect lifecycle hooks to combatants
 * at the correct timing points in the round.
 *
 * The actual effect logic lives inside each StatusEffect implementation
 * (via the interface's onTurnStart/onRoundEnd hooks). EffectManager is
 * simply the caller that invokes those hooks at the right moment and
 * cleans up expired effects afterwards.
 *
 * SRP – sole responsibility: trigger effect hooks and remove expired effects.
 *        No damage calculation or turn-order logic lives here.
 */
public class EffectManager {
    public void applyEffectsOnTurnStart(Combatant combatant, BattleState context) {
        combatant.onTurnStart(context);
        combatant.removeExpiredEffects();
    }

    public void applyEffectsOnRoundEnd(Combatant combatant, BattleState context) {
        combatant.onRoundEnd(context);
        combatant.removeExpiredEffects();
    }
}
