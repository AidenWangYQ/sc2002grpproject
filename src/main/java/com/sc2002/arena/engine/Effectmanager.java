package com.sc2002.arena.engine;

import com.sc2002.arena.model.combatant.Combatant;
import com.sc2002.arena.model.effect.StatusEffect;
 
import java.util.List;
 
/**
 * Manages the lifecycle of status effects on all combatants.
 *
 * Responsibilities (SRP):
 *   1. Call applyOnTurnStart() for each active effect at the start of
 *      a combatant's turn.
 *   2. Tick effects after the combatant has acted.
 *   3. Remove expired effects and roll back any stat changes they held.
 *
 * BattleEngine delegates all effect timing to this class and never
 * manipulates StatusEffect objects directly (DIP / SRP).
 */
public class EffectManager {
 

    public void applyEffectsOnTurnStart(Combatant combatant) {
        for (StatusEffect effect : combatant.getActiveEffects()) {
            if (!effect.isExpired()) {
                effect.applyOnTurnStart(combatant);
            }
        }
    }
 
    /**
     * Tick all effects on the combatant by one turn, then remove
     * any that have expired. Expired effects clean up their own
     * stat changes via Combatant.removeExpiredEffects().
     *
     * Called after the combatant has finished acting this turn.
     *
     * @param combatant the combatant who just acted
     */
    public void tickAndExpire(Combatant combatant) {
        combatant.tickEffects();
        combatant.removeExpiredEffects();
    }
 
    /**
     * Convenience: tick and expire effects for a whole list of combatants.
     * Used at round end for combatants who were stunned 
     *
     * @param combatants list to process
     */

    public void tickAndExpireAll(List<Combatant> combatants) {
        for (Combatant c : combatants) {
            tickAndExpire(c);
        }
    }

    public void printEffectSummary(Combatant combatant) {
        List<StatusEffect> effects = combatant.getActiveEffects();
        if (effects.isEmpty()) return;
 
        System.out.print("    [" + combatant.getName() + " effects: ");
        for (int i = 0; i < effects.size(); i++) {
            StatusEffect e = effects.get(i);
            System.out.printf("%s(%d)", e.getName(), e.getRemainingTurns());
            if (i < effects.size() - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
 