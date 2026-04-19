package com.sc2002.arena.skill;

import java.util.List;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.effect.AttackBuffEffect;

import com.sc2002.arena.strategy.BattleContext;

/**
 * A multi-target special skill that damages all living enemies.
 *
 * Responsibilities:
 * - apply damage to all valid enemy targets
 * - track defeated enemies
 * - grant attack buff based on number of defeats
 */
public final class ArcaneBlastSkill implements SpecialSkill {

    /** @return skill name for UI and logging */
    @Override
    public String getName() {
        return "Arcane Blast";
    }

    /** @return false as targeting is handled internally */
    @Override
    public boolean requiresTarget() {
        return false;
    }

    /**
     * Executes Arcane Blast on all living opponents.
     *
     * @param context execution data
     * @param mode skill usage mode
     * @return result containing all damage and effects
     */
    @Override
    public ActionResult use(ActionContext context, SkillUseMode mode) {
        Combatant actor = context.getActor();
        BattleContext battleContext = context.getBattleContext();

        List<Combatant> targets = battleContext.getLivingOpponentsOf(actor);

        if (targets.isEmpty()) {
            throw new IllegalStateException("Arcane Blast requires at least one living enemy.");
        }

        int defeatedCount = 0;

        ActionResult result = ActionResult.forAction(actor, getName());

        ActionResolver actionResolver = context.getActionResolver();

        ActionResolver.PreparedAttack preparedAttack =
                actionResolver.prepareAttack(actor, battleContext, result);

        for (Combatant target : targets) {

            if (!target.isAlive()) {
                continue;
            }

            ActionResolver.DamageResolution damage =
                    actionResolver.applyDamage(
                            actor,
                            target,
                            preparedAttack.attackValue(),
                            battleContext
                    );

            result.recordDamage(
                    actor,
                    target,
                    damage.beforeHp(),
                    damage.rawDamage(),
                    damage.appliedDamage(),
                    damage.afterHp()
            );

            if (!target.isAlive()) {
                result.recordDefeat(target);
                defeatedCount++;
            }
        }

        if (defeatedCount > 0) {
            int attackBonus = defeatedCount * 10;

            actor.applyEffect(new AttackBuffEffect(attackBonus), battleContext);

            result.recordEffect(actor, "Arcane Blast ATK Bonus +" + attackBonus);
        }

        if (mode == SkillUseMode.POWER_STONE_TRIGGER) {
            result.recordNote("Triggered by Power Stone.");
        }

        return result;
    }
}