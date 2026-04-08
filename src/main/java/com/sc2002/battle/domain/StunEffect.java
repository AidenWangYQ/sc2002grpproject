package sc2002.battle.domain;

public final class StunEffect extends AbstractStatusEffect {
    private int remainingTurnsToSkip = 2;

    public StunEffect() {
        super("Stun");
    }

    @Override
    public void onTurnEnd(Combatant target, BattleContext context) {
        if (remainingTurnsToSkip > 0) {
            remainingTurnsToSkip--;
        }
    }

    @Override
    public boolean preventsAction() {
        return remainingTurnsToSkip > 0;
    }

    @Override
    public boolean isExpired() {
        return remainingTurnsToSkip <= 0;
    }
}
