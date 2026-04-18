package com.sc2002.arena.skill;

import java.util.List;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.effect.DragonBreathCriticalEffect;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.strategy.BattleContext;

public final class DragonBreathSkill implements SpecialSkill {
    @Override
    public String getName() {
        return "Dragon Breath";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public ActionResult use(ActionContext context, SkillUseMode mode) {
        Combatant actor = context.getActor();
        BattleContext battleContext = context.getBattleContext();
        List<Combatant> targets = battleContext.getLivingOpponentsOf(actor);
        if (targets.isEmpty()) {
            throw new IllegalStateException("Dragon Breath requires at least one living opponent.");
        }

        ActionResult result = ActionResult.forAction(actor, getName());
        ActionResolver actionResolver = context.getActionResolver();
        ActionResolver.PreparedAttack preparedAttack = actionResolver.prepareGuaranteedCriticalAttack(
                actor,
                battleContext,
                result,
                new DragonBreathCriticalEffect(),
                "Dragon Breath Critical x1.5",
                "Dragon Breath lands a guaranteed critical hit."
        );

        for (Combatant target : targets) {
            if (!target.isAlive()) {
                continue;
            }
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
        }

        result.recordNote("Dragon Breath scorches all living opponents.");
        if (mode == SkillUseMode.POWER_STONE_TRIGGER) {
            result.recordNote("Triggered by Power Stone.");
        }
        return result;
    }
}
