package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Player;

/**
 * Represents an action that uses an item from the player's inventory.
 *
 * Responsibilities:
 * - validate actor is a Player
 * - delegate item usage to Player's inventory
 */
public final class UseItemAction implements CombatAction {

    /** @return action name */
    @Override
    public String getName() {
        return "Item";
    }

    /** @return false as target is handled by the item itself */
    @Override
    public boolean requiresTarget() {
        return false;
    }

    /**
     * Executes the item usage action.
     *
     * @param context execution data
     * @return result from inventory item usage
     */
    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();

        if (!(actor instanceof Player player)) {
            throw new IllegalStateException("Only players can use items.");
        }

        return player.getInventory().useItem(context.getRequiredItemSlot(), context);
    }
}