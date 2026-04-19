package com.sc2002.arena.action;

import java.util.Objects;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.engine.ActionResolver;

import com.sc2002.arena.strategy.BattleContext;

/**
 * Encapsulates all runtime data required to execute a combat action.
 *
 * Responsibilities:
 * - store the acting combatant and optional target
 * - provide contextual dependencies required for action execution
 * - support multiple action types (targeted, untargeted, item-based)
 *
 * Design notes:
 * - This class reduces parameter complexity by grouping all action inputs
 *   into a single object passed across the system.
 * - Factory methods are used to clearly define valid construction patterns
 *   for different action types.
 * - Nullable fields (target, itemSlot) are intentionally controlled through
 *   "required" accessors to enforce correctness at runtime.
 */
public final class ActionContext {

    /** The combatant performing the action. */
    private final Combatant actor;

    /** The target of the action, if applicable (null for untargeted actions). */
    private final Combatant target;

    /** Inventory slot index used for item-based actions (null otherwise). */
    private final Integer itemSlot;

    /**
     * Shared battle state containing all combatants and turn-related data.
     * Ensures consistency across all actions executed within the same battle.
     */
    private final BattleContext battleContext;

    /**
     * Resolves combat mechanics such as damage calculation and interactions
     * between combatants during action execution.
     */
    private final ActionResolver actionResolver;

    /**
     * Core constructor used internally by factory methods.
     *
     * Validation ensures required dependencies are always present.
     */
    public ActionContext(
            Combatant actor,
            Combatant target,
            Integer itemSlot,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        this.actor = Objects.requireNonNull(actor, "actor cannot be null");
        this.target = target;
        this.itemSlot = itemSlot;
        this.battleContext = Objects.requireNonNull(battleContext, "battleContext cannot be null");
        this.actionResolver = Objects.requireNonNull(actionResolver, "actionResolver cannot be null");
    }

    /**
     * Creates a context for actions that require a target.
     * Example: basic attack, single-target skills.
     */
    public static ActionContext forTargetedAction(
            Combatant actor,
            Combatant target,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, target, null, battleContext, actionResolver);
    }

    /**
     * Convenience overload using a default ActionResolver.
     */
    public static ActionContext forTargetedAction(Combatant actor, Combatant target, BattleContext battleContext) {
        return forTargetedAction(actor, target, battleContext, new ActionResolver());
    }

    /**
     * Creates a context for actions that do not require a target.
     * Example: defend, self-buff abilities.
     */
    public static ActionContext forUntargetedAction(
            Combatant actor,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, null, null, battleContext, actionResolver);
    }

    /**
     * Convenience overload using a default ActionResolver.
     */
    public static ActionContext forUntargetedAction(Combatant actor, BattleContext battleContext) {
        return forUntargetedAction(actor, battleContext, new ActionResolver());
    }

    /**
     * Creates a context for item-based actions.
     * Requires both a target and an inventory slot.
     */
    public static ActionContext forItemAction(
            Combatant actor,
            Combatant target,
            int itemSlot,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, target, itemSlot, battleContext, actionResolver);
    }

    /**
     * Convenience overload using a default ActionResolver.
     */
    public static ActionContext forItemAction(
            Combatant actor,
            Combatant target,
            int itemSlot,
            BattleContext battleContext
    ) {
        return forItemAction(actor, target, itemSlot, battleContext, new ActionResolver());
    }

    /** @return the acting combatant */
    public Combatant getActor() {
        return actor;
    }

    /** @return the target, or null if the action is untargeted */
    public Combatant getTarget() {
        return target;
    }

    /**
     * Returns the required target for this action.
     *
     * @throws IllegalStateException if the action does not have a target
     */
    public Combatant getRequiredTarget() {
        if (target == null) {
            throw new IllegalStateException("This action requires a target.");
        }
        return target;
    }

    /** @return the item slot, or null if not applicable */
    public Integer getItemSlot() {
        return itemSlot;
    }

    /**
     * Returns the required item slot for this action.
     *
     * @throws IllegalStateException if the action does not involve an item
     */
    public int getRequiredItemSlot() {
        if (itemSlot == null) {
            throw new IllegalStateException("This action requires an item slot.");
        }
        return itemSlot;
    }

    /** @return shared battle context */
    public BattleContext getBattleContext() {
        return battleContext;
    }

    /** @return action resolver used for combat calculations */
    public ActionResolver getActionResolver() {
        return actionResolver;
    }
}