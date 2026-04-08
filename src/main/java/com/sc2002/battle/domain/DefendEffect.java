package sc2002.battle.domain;

public final class DefendEffect extends AbstractStatusEffect {
    private static final int DEFENSE_BONUS = 10;
    private int remainingRoundEnds = 2;

    public DefendEffect() {
        super("Defend");
    }

    @Override
    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense + DEFENSE_BONUS;
    }

    @Override
    public void onRoundEnd(Combatant target, BattleContext context) {
        if (remainingRoundEnds > 0) {
            remainingRoundEnds--;
        }
    }

    @Override
    public boolean isExpired() {
        return remainingRoundEnds <= 0;
    }
}
