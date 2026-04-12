package com.sc2002.battle.domain;

public final class SmokeBomb implements Item {
    private boolean used;
    public SmokeBomb() {
        this.used = false;  // By default, the power stone is not used
    }
    @Override
    public String getName() {
        return "Smoke Bomb";
    }
    public boolean isUsed() {
        return used;  // Returns whether the power stone has been used
    }
    public void use() {
        if (!used) {
            used = true;  // Mark as used when used
            // Apply power stone effects (e.g., free special skill use)
        }
    }
    @Override
    public ActionResult use(ActionContext context) {
        Combatant actor = context.getActor();
        ActionResult result = ActionResult.forAction(actor, "Item");
        actor.applyEffect(new SmokeBombInvulnerabilityEffect(), context.getBattleContext());
        result.recordEffect(actor, "Smoke Bomb Invulnerability");
        return result;
    }
}
