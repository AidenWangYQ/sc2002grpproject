package sc2002.battle.domain;
import sc2002.battle.domain.StatusEffect;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.BattleContext;
import java.util.Objects;


public abstract class AbstractStatusEffect implements StatusEffect {
    private final String name;

    protected AbstractStatusEffect(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract int modifyDefense(Combatant target, int defense, BattleContext context);

    // Handle logic at the end of each round
    public abstract void onRoundEnd(Combatant target, BattleContext context);

    // Check if the effect has expired
    public abstract boolean isExpired();
}
