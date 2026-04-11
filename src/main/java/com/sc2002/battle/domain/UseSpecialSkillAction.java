package com.sc2002.battle.domain;

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
        if (!(actor instanceof Player player)) {
            throw new IllegalStateException("Only players can use special skills.");
        }
        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot use a special skill after being eliminated.");
        }
        if (player.getSpecialSkillCooldown() > 0) {
            throw new IllegalStateException("Special skill is on cooldown for " + player.getSpecialSkillCooldown() + " more turn(s).");
        }

        int cooldownBefore = player.getSpecialSkillCooldown();
        ActionResult result = player.getSpecialSkill().use(context, SkillUseMode.NORMAL);
        player.setSpecialSkillCooldown(3);
        result.recordCooldownChange(cooldownBefore, player.getSpecialSkillCooldown());
        return result;
    }
}
