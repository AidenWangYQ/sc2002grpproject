package com.sc2002.battle.domain;
import sc2002.battle.domain.SpecialSkill;
import sc2002.battle.domain.ActionResult;
import sc2002.battle.domain.ActionContext;
import sc2002.battle.domain.SkillUseMode;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.BattleContext;
import sc2002.battle.domain.AttackBuffEffect;
import java.util.List;

public final class ArcaneBlastSkill implements SpecialSkill {
    @Override
    public String getName() {
        return "Arcane Blast";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public ActionResult use(ActionContext context, SkillUseMode mode) {
        Combatant actor = context.getActor();
        BattleContext battleContext = context.getBattleContext();
        List<Combatant> targets = battleContext.getAliveEnemiesOf(actor);
        if (targets.isEmpty()) {
            throw new IllegalStateException("Arcane Blast requires at least one living enemy.");
        }

        int attackAtCastStart = actor.getEffectiveAttack(battleContext);
        int defeatedCount = 0;
        ActionResult result = ActionResult.forAction(actor, getName());

        for (Combatant target : targets) {
            if (!target.isAlive()) {
                continue;
            }
            int rawDamage = Math.max(0, attackAtCastStart - target.getEffectiveDefense(battleContext));
            int beforeHp = target.getCurrentHp();
            int appliedDamage = target.receiveDamage(rawDamage, actor, battleContext);
            int afterHp = target.getCurrentHp();
            result.recordDamage(actor, target, beforeHp, rawDamage, appliedDamage, afterHp);
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
