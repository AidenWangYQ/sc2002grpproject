package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;

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

        int attack = actor.getEffectiveAttack(battleContext);
        int defense = target.getEffectiveDefense(battleContext);
        int rawDamage = Math.max(0, attack - defense);
        int beforeHp = target.getCurrentHp();
        int appliedDamage = target.receiveDamage(rawDamage, actor, battleContext);
        int afterHp = target.getCurrentHp();

        ActionResult result = ActionResult.forAction(actor, getName());
        result.recordDamage(actor, target, beforeHp, rawDamage, appliedDamage, afterHp);
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
