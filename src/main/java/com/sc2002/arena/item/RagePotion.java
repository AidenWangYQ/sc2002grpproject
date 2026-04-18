package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.effect.RageEffect;

public final class RagePotion implements Item {
    @Override
    public String getName() {
        return "Rage Potion";
    }

    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();
        ActionResult result = ActionResult.forAction(actor, "Item");
        actor.applyEffect(new RageEffect(), context.getBattleContext());
        result.recordEffect(actor, "Rage +50% Crit Chance");
        result.recordNote("Rage Potion adds 50% critical chance for 3 turns.");
        return result;
    }
}
