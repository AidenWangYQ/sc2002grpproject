package com.sc2002.arena.strategy;

import java.util.*;
import com.sc2002.arena.combatant.*;

public interface BattleContext {
    List<Combatant> getAliveEnemiesOf(Combatant actor);

    List<Combatant> getAlliesOf(Combatant actor);

    int getRoundNumber();
}
