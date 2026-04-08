package sc2002.battle.domain;

import java.util.Objects;

public final class ActionContext {
    private final Combatant actor;
    private final Combatant target;
    private final Integer itemSlot;
    private final BattleContext battleContext;

    public ActionContext(Combatant actor, Combatant target, Integer itemSlot, BattleContext battleContext) {
        this.actor = Objects.requireNonNull(actor, "actor cannot be null");
        this.target = target;
        this.itemSlot = itemSlot;
        this.battleContext = Objects.requireNonNull(battleContext, "battleContext cannot be null");
    }

    public static ActionContext forTargetedAction(Combatant actor, Combatant target, BattleContext battleContext) {
        return new ActionContext(actor, target, null, battleContext);
    }

    public static ActionContext forUntargetedAction(Combatant actor, BattleContext battleContext) {
        return new ActionContext(actor, null, null, battleContext);
    }

    public static ActionContext forItemAction(
            Combatant actor,
            Combatant target,
            int itemSlot,
            BattleContext battleContext
    ) {
        return new ActionContext(actor, target, itemSlot, battleContext);
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
}
