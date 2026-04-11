package com.sc2002.battle.domain;

public final class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }

    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();
        if (!(actor instanceof Player player)) {
            throw new IllegalStateException("Power Stone can only be used by players.");
        }
        ActionResult result = ActionResult.forAction(actor, "Item");
        result.recordNote("Power Stone triggered " + player.getSpecialSkill().getName() + ".");
        ActionResult skillResult = player.getSpecialSkill().use(context, SkillUseMode.POWER_STONE_TRIGGER);
        result.merge(skillResult);
        result.recordNote("Power Stone does not change cooldown.");
        return result;
    }
}
