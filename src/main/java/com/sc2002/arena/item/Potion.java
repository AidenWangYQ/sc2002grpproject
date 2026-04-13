package com.sc2002.arena.item;

import com.sc2002.arena.action.*;
import com.sc2002.arena.combatant.*;

public final class Potion implements Item {
    private static final int HEAL_AMOUNT = 100;

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
}
