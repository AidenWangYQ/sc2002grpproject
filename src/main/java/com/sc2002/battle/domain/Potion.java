package com.sc2002.battle.domain;
import sc2002.battle.domain.ActionResult;
import sc2002.battle.domain.ActionContext;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.Item;
public final class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;
    private boolean used;
    public Potion() {
        this.used = false;  // By default, the power stone is not used
    }
    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();
        ActionResult result = ActionResult.forAction(actor, "Item");
        int beforeHp = actor.getCurrentHp();
        int healedAmount = actor.heal(HEAL_AMOUNT);
        int afterHp = actor.getCurrentHp();
        result.recordHeal(actor, beforeHp, healedAmount, afterHp);
        result.recordNote("Potion restored up to 100 HP.");
        return result;
    }
    public boolean isUsed() {
        return used;  // Returns whether the potion has been used
    }

    @Override
    public void use() {
        if (!used) {
            used = true;  // Mark as used when used
            // Apply healing logic or other effects of using the potion
        }
    }
}
