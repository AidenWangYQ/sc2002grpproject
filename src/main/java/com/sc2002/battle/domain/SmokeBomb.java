package sc2002.battle.domain;

public final class SmokeBomb implements Item {
    @Override
    public String getName() {
        return "Smoke Bomb";
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
