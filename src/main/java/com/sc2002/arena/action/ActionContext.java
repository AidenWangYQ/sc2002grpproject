package com.sc2002.arena.action;

import java.util.Objects;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.engine.ActionResolver;

import com.sc2002.arena.strategy.BattleContext;

public final class ActionContext {
    private final Combatant actor;
    private final Combatant target;
    private final Integer itemSlot;
    private final BattleContext battleContext;
    private final ActionResolver actionResolver;

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

    public static ActionContext forTargetedAction(
            Combatant actor,
            Combatant target,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, target, null, battleContext, actionResolver);
    }

    public static ActionContext forTargetedAction(Combatant actor, Combatant target, BattleContext battleContext) {
        return forTargetedAction(actor, target, battleContext, new ActionResolver());
    }

    public static ActionContext forUntargetedAction(
            Combatant actor,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, null, null, battleContext, actionResolver);
    }

    public static ActionContext forUntargetedAction(Combatant actor, BattleContext battleContext) {
        return forUntargetedAction(actor, battleContext, new ActionResolver());
    }

    public static ActionContext forItemAction(
            Combatant actor,
            Combatant target,
            int itemSlot,
            BattleContext battleContext,
            ActionResolver actionResolver
    ) {
        return new ActionContext(actor, target, itemSlot, battleContext, actionResolver);
    }

    public static ActionContext forItemAction(
            Combatant actor,
            Combatant target,
            int itemSlot,
            BattleContext battleContext
    ) {
        return forItemAction(actor, target, itemSlot, battleContext, new ActionResolver());
    }

    public Combatant getActor() {
        return actor;
    }

    public Combatant getTarget() {
        return target;
    }

    public Combatant getRequiredTarget() {
        if (target == null) {
            throw new IllegalStateException("This action requires a target.");
        }
        return target;
    }

    public Integer getItemSlot() {
        return itemSlot;
    }

    public int getRequiredItemSlot() {
        if (itemSlot == null) {
            throw new IllegalStateException("This action requires an item slot.");
        }
        return itemSlot;
    }

    public BattleContext getBattleContext() {
        return battleContext;
    }

    public ActionResolver getActionResolver() {
        return actionResolver;
    }
}
