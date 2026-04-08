package sc2002.battle.domain;

import java.util.List;

public interface BattleContext {
    List<Combatant> getAliveEnemiesOf(Combatant actor);

    List<Combatant> getAlliesOf(Combatant actor);

    int getRoundNumber();
}
