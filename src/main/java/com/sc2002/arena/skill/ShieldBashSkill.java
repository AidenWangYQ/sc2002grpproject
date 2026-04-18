package com.sc2002.arena.skill;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.effect.StunEffect;

import com.sc2002.arena.strategy.BattleContext;

public final class ShieldBashSkill implements SpecialSkill {
    @Override
    public String getName() {
        return "Shield Bash";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public ActionResult use(ActionContext context, SkillUseMode mode) {
        Combatant actor = context.getActor();
        Combatant target = context.getRequiredTarget();
        BattleContext battleContext = context.getBattleContext();

        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot use Shield Bash after being eliminated.");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException(target.getName() + " is already eliminated.");
        }

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
        if (target.isAlive()) {
            target.applyEffect(new StunEffect(), battleContext);
            result.recordEffect(target, "Stun");
        } else {
            result.recordDefeat(target);
        }
        if (mode == SkillUseMode.POWER_STONE_TRIGGER) {
            result.recordNote("Triggered by Power Stone.");
        }
        return result;
    }
}
