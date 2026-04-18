package com.sc2002.arena.engine;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.combatant.Combatant;

public record TurnResolution(Combatant actor, ActionResult actionResult, boolean skipped) {
    public static TurnResolution skipped(Combatant actor) {
        return new TurnResolution(actor, null, true);
    }

    public static TurnResolution performed(Combatant actor, ActionResult actionResult) {
        return new TurnResolution(actor, actionResult, false);
    }
}
