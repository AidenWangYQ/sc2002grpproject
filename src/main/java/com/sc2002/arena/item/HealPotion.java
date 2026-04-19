package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

/**
 * A consumable item that restores a fixed amount of HP to the user.
 *
 * Responsibilities:
 * - heal the actor by a fixed value
 * - record healing outcome in ActionResult
 */
public final class HealPotion implements Item {

    /** Fixed healing amount provided by this item. */
    private static final int HEAL_AMOUNT = 100;

    /** @return item name for UI and inventory display */
    @Override
    public String getName() {
        return "Heal Potion";
    }

    /**
     * Uses the heal potion on the actor.
     *
     * @param context execution data
     * @return result containing healing event
     */
    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();

        ActionResult result = ActionResult.forAction(actor, "Item");

        int beforeHp = actor.getCurrentHp();
        int healedAmount = actor.heal(HEAL_AMOUNT);
        int afterHp = actor.getCurrentHp();

        result.recordHeal(actor, beforeHp, healedAmount, afterHp);
        result.recordNote("Heal Potion restored up to 100 HP.");

        return result;
    }
}