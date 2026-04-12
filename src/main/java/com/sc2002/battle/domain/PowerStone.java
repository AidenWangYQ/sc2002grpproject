package com.sc2002.battle.domain;

public final class PowerStone implements Item {
    @Override
    public String getName() {
        return "Power Stone";
    }
    private boolean used;
    public PowerStone() {
        this.used = false;
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
    public boolean isUsed() {
        return used;  // Returns whether the power stone has been used
    }

    @Override
    public void use() {
        if (!used) {
            used = true;  // Mark as used when used
            // Apply power stone effects (e.g., free special skill use)
        }
    }
}
