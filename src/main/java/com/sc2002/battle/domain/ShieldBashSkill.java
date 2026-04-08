package sc2002.battle.domain;

public final class ShieldBashSkill implements SpecialSkill {
    @Override
    public String getName() {
        return "Shield Bash";
    }

    @Override
    public boolean requiresTarget() {
        return true;
    }

    @Override
    public ActionResult use(ActionContext context, SkillUseMode mode) {
        Combatant actor = context.getActor();
        Combatant target = context.getRequiredTarget();
        BattleContext battleContext = context.getBattleContext();

        if (!actor.isAlive()) {
            throw new IllegalStateException(actor.getName() + " cannot use Shield Bash after being eliminated.");
        }
        if (!target.isAlive()) {
            throw new IllegalStateException(target.getName() + " is already eliminated.");
        }

        int rawDamage = Math.max(0, actor.getEffectiveAttack(battleContext) - target.getEffectiveDefense(battleContext));
        int beforeHp = target.getCurrentHp();
        int appliedDamage = target.receiveDamage(rawDamage, actor, battleContext);
        int afterHp = target.getCurrentHp();

        ActionResult result = ActionResult.forAction(actor, getName());
        result.recordDamage(actor, target, beforeHp, rawDamage, appliedDamage, afterHp);
        if (target.isAlive()) {
            target.applyEffect(new StunEffect(), battleContext);
            result.recordEffect(target, "Stun");
        } else {
            result.recordDefeat(target);
        }
        if (mode == SkillUseMode.POWER_STONE_TRIGGER) {
            result.recordNote("Triggered by Power Stone.");
        }
        return result;
    }
}
