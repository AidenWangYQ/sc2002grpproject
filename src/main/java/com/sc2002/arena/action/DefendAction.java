package com.sc2002.arena.action;

import com.sc2002.arena.combatant.*;
import com.sc2002.arena.effect.*;

public final class DefendAction implements CombatAction {
    @Override
    public String getName() {
        return "Defend";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();
        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot defend after being eliminated.");
        }
        ActionResult result = ActionResult.forAction(actor, getName());
        actor.applyEffect(new DefendEffect(), context.getBattleContext());
        result.recordEffect(actor, "Defend +10 DEF");
        return result;
    }
}
