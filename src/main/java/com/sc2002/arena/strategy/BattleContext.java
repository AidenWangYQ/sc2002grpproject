package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.combatant.Combatant;

public interface BattleContext {
    List<Combatant> getAliveEnemiesOf(Combatant actor);

    List<Combatant> getAlliesOf(Combatant actor);

    int getRoundNumber();
}
