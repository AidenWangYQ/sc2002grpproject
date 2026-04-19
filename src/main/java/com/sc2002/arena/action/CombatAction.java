package com.sc2002.arena.action;
/**
 * Defines a contract for executable combat actions.
 *
 * Responsibilities:
 * - provide action name
 * - indicate if a target is required
 * - execute using ActionContext and return ActionResult
 */
public interface CombatAction {

    /** @return action name */
    String getName();

    /** @return true if the action requires a target */
    boolean requiresTarget();

    /**
     * Executes the action.
     *
     * @param context execution data
     * @return result of the action
     */
    ActionResult execute(ActionContext context);
}