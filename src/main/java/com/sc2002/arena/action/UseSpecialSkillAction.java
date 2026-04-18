package com.sc2002.arena.action;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.SpecialSkillUser;

import com.sc2002.arena.skill.SkillUseMode;

public final class UseSpecialSkillAction implements CombatAction {
    @Override
    public String getName() {
        return "SpecialSkill";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();
        if (!(actor instanceof SpecialSkillUser skillUser)) {
            throw new IllegalStateException("Only skill-bearing combatants can use special skills.");
        }
        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot use a special skill after being eliminated.");
        }
        if (skillUser.getSpecialSkillCooldown() > 0) {
            throw new IllegalStateException("Special skill is on cooldown for " + skillUser.getSpecialSkillCooldown() + " more turn(s).");
        }

        int cooldownBefore = skillUser.getSpecialSkillCooldown();
        ActionResult result = skillUser.getSpecialSkill().use(context, SkillUseMode.NORMAL);
        skillUser.setSpecialSkillCooldown(skillUser.getSpecialSkillCooldownDuration());
        result.recordCooldownChange(cooldownBefore, skillUser.getSpecialSkillCooldown());
        return result;
    }
}
