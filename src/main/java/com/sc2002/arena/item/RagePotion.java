package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.effect.RageEffect;

/**
 * A consumable item that grants a temporary rage buff.
 *
 * Responsibilities:
 * - apply RageEffect to the actor
 * - record effect application in ActionResult
 */
public final class RagePotion implements Item {

    /** @return item name for UI and inventory display */
    @Override
    public String getName() {
        return "Rage Potion";
    }

    /**
     * Uses the rage potion to apply a critical chance buff.
     *
     * @param context execution data
     * @return result containing applied effect
     */
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