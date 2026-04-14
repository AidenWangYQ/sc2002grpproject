package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.effect.SmokeBombInvulnerabilityEffect;

public final class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();
        ActionResult result = ActionResult.forAction(actor, "Item");
        actor.applyEffect(new SmokeBombInvulnerabilityEffect(), context.getBattleContext());
        result.recordEffect(actor, "Smoke Bomb Invulnerability");
        return result;
    }
}
