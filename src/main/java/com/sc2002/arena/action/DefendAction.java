package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.effect.DefendEffect;

/**
 * Represents a defend action that applies a defensive effect to the actor.
 *
 * Responsibilities:
 * - apply DefendEffect to the actor
 * - record the applied effect in ActionResult
 */
public final class DefendAction implements CombatAction {

    /** @return action name */
    @Override
    public String getName() {
        return "Defend";
    }

    /** @return false as this action does not require a target */
    @Override
    public boolean requiresTarget() {
        return false;
    }

    /**
     * Executes the defend action.
     *
     * @param context execution data
     * @return result containing applied effect
     */
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