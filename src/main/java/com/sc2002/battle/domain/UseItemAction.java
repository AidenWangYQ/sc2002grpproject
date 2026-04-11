package com.sc2002.battle.domain;

public final class UseItemAction implements CombatAction {
    @Override
    public String getName() {
        return "Item";
    }

    @Override
    public boolean requiresTarget() {
        return false;
    }

    @Override
    public ActionResult execute(ActionContext context) {
        Combatant actor = context.getActor();
        if (!(actor instanceof Player player)) {
            throw new IllegalStateException("Only players can use items.");
        }
        return player.getInventory().useItem(context.getRequiredItemSlot(), context);
    }
}
