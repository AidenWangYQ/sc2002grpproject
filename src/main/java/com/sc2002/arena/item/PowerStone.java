package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Player;

import com.sc2002.arena.skill.SkillUseMode;

/**
 * An item that triggers the player's special skill without affecting cooldown.
 *
 * Responsibilities:
 * - validate usage is restricted to Player
 * - trigger special skill execution
 * - merge skill results into item result
 */
public final class PowerStone implements Item {

    /** @return item name for UI and inventory display */
    @Override
    public String getName() {
        return "Power Stone";
    }

    /**
     * Uses the Power Stone to trigger a special skill.
     *
     * @param context execution data
     * @return merged result of item and skill execution
     */
    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();

        if (!(actor instanceof Player player)) {
            throw new IllegalStateException("Power Stone can only be used by players.");
        }

        ActionResult result = ActionResult.forAction(actor, "Item");

        result.recordNote("Power Stone triggered " + player.getSpecialSkill().getName() + ".");

        ActionResult skillResult =
                player.getSpecialSkill().use(context, SkillUseMode.POWER_STONE_TRIGGER);

        result.merge(skillResult);

        result.recordNote("Power Stone does not change cooldown.");

        return result;
    }
}