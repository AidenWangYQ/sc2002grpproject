package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.effect.SmokeBombInvulnerabilityEffect;

/**
 * A consumable item that grants temporary damage immunity against enemies.
 *
 * Responsibilities:
 * - apply SmokeBombInvulnerabilityEffect to the actor
 * - record effect application in ActionResult
 */
public final class SmokeBomb implements Item {

    /** @return item name for UI and inventory display */
    @Override
    public String getName() {
        return "Smoke Bomb";
    }

    /**
     * Uses the smoke bomb to apply temporary invulnerability.
     *
     * @param context execution data
     * @return result containing applied effect
     */
    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();

        ActionResult result = ActionResult.forAction(actor, "Item");

        actor.applyEffect(new SmokeBombInvulnerabilityEffect(), context.getBattleContext());

        result.recordEffect(actor, "Smoke Bomb Invulnerability");

        return result;
    }
}