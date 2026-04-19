package com.sc2002.arena.engine;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.combatant.Combatant;
/**
 * Immutable value object representing the outcome of one combatant's turn.
 *
 * Returned by TurnManager.processTurn() to BattleEngine so it can:
 *   - Print the ActionResult via UI if an action was performed
 *   - Distinguish skipped turns (stunned/dead) from performed turns
 *
 * Uses Java records for conciseness — fields are final and auto-generated
 * with accessors, equals, hashCode, and toString.
 *
 * skipped = true  → combatant was dead or could not act (stunned)
 * skipped = false → combatant successfully performed an action
 * actionResult    → null if skipped, populated ActionResult if performed
 */
public record TurnResolution(Combatant actor, ActionResult actionResult, boolean skipped) {
    public static TurnResolution skipped(Combatant actor) {
        return new TurnResolution(actor, null, true);
    }

    public static TurnResolution performed(Combatant actor, ActionResult actionResult) {
        return new TurnResolution(actor, actionResult, false);
    }
}
