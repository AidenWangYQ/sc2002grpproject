package com.sc2002.arena.engine;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.effect.StatusEffect;

public class ActionResolver {
    public ActionResult resolve(CombatAction action, ActionContext context) {
        return action.execute(context);
    }

    public int applyDamage(Combatant attacker, Combatant target, com.sc2002.arena.strategy.BattleContext context) {
        int rawDamage = Math.max(0, attacker.getEffectiveAttack(context) - target.getEffectiveDefense(context));
        return target.receiveDamage(rawDamage, attacker, context);
    }

    public void applyStatus(StatusEffect effect, Combatant target, com.sc2002.arena.strategy.BattleContext context) {
        target.applyEffect(effect, context);
    }
}
