package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

/**
 * Represents a consumable item that can be used during combat.
 *
 * Responsibilities:
 * - define item identity
 * - execute item effect and return results
 */
public interface Item {

    /** @return item name for UI and inventory display */
    String getName();

    /**
     * Uses the item and produces its effects.
     *
     * @param context execution data
     * @return result of item usage
     */
    ActionResult use(ActionContext context);
}