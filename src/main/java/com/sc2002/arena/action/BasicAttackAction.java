package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.engine.ActionResolver;

import com.sc2002.arena.strategy.BattleContext;

/**
 * Represents the standard single-target attack action.
 *
 * Responsibilities:
 * - execute a basic attack using ActionResolver
 * - validate actor and target eligibility before execution
 * - record resulting damage and defeat events
 *
 * Design notes:
 * - This class follows the Command pattern via CombatAction,
 *   allowing actions to be executed polymorphically.
 * - Damage calculation is delegated to ActionResolver to keep
 *   this class focused on orchestration rather than mechanics.
 * - Results are captured through ActionResult, ensuring separation
 *   between execution logic and presentation.
 */
public final class BasicAttackAction implements CombatAction {

    /** @return name used for logging/UI display */
    @Override
    public String getName() {
        return "BasicAttack";
    }

    /** @return true as this action requires a target */
    @Override
    public boolean requiresTarget() {
        return true;
    }

    /**
     * Executes the basic attack action.
     *
     * Flow:
     * 1. Validate actor and target state
     * 2. Prepare attack values via ActionResolver
     * 3. Apply damage to target
     * 4. Record damage and potential defeat
     *
     * @param context encapsulates all required runtime data
     * @return ActionResult containing all events produced
     */
    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();
        Combatant target = context.getRequiredTarget();
        BattleContext battleContext = context.getBattleContext();

        validateCombatants(actor, target);

        ActionResult result = ActionResult.forAction(actor, getName());

        /** Resolver handles attack preparation and damage application */
        ActionResolver actionResolver = context.getActionResolver();

        /**
         * Step 1: Prepare attack (e.g., apply buffs, crit chance, modifiers)
         */
        ActionResolver.PreparedAttack preparedAttack =
                actionResolver.prepareAttack(actor, battleContext, result);

        /**
         * Step 2: Apply damage to target after all calculations
         */
        ActionResolver.DamageResolution damage =
                actionResolver.applyDamage(
                        actor,
                        target,
                        preparedAttack.attackValue(),
                        battleContext
                );

        /**
         * Step 3: Record damage event for logging/UI
         */
        result.recordDamage(
                actor,
                target,
                damage.beforeHp(),
                damage.rawDamage(),
                damage.appliedDamage(),
                damage.afterHp()
        );

        /**
         * Step 4: Record defeat if target HP reaches zero
         */
        if (!target.isAlive()) {
            result.recordDefeat(target);
        }

        return result;
    }

    /**
     * Validates whether both actor and target are eligible for combat.
     *
     * @throws IllegalStateException if actor cannot act or target is invalid
     */
    private void validateCombatants(Combatant actor, Combatant target) {
        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot act after being eliminated.");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException(target.getName() + " is already eliminated.");
        }
    }
}