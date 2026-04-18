package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.engine.ActionResolver;

import com.sc2002.arena.strategy.BattleContext;

public final class BasicAttackAction implements CombatAction {
    @Override
    public String getName() {
        return "BasicAttack";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();
        Combatant target = context.getRequiredTarget();
        BattleContext battleContext = context.getBattleContext();
        validateCombatants(actor, target);

        ActionResult result = ActionResult.forAction(actor, getName());
        ActionResolver actionResolver = context.getActionResolver();
        ActionResolver.PreparedAttack preparedAttack = actionResolver.prepareAttack(actor, battleContext, result);
        ActionResolver.DamageResolution damage = actionResolver.applyDamage(
                actor,
                target,
                preparedAttack.attackValue(),
                battleContext
        );
        result.recordDamage(actor, target, damage.beforeHp(), damage.rawDamage(), damage.appliedDamage(), damage.afterHp());
        if (!target.isAlive()) {
            result.recordDefeat(target);
        }
        return result;
    }

    private void validateCombatants(Combatant actor, Combatant target) {
        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot act after being eliminated.");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException(target.getName() + " is already eliminated.");
        }
    }
}
