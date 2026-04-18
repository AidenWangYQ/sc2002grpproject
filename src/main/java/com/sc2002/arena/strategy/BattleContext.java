package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.combatant.Combatant;

public interface BattleContext {
    List<Combatant> getLivingOpponentsOf(Combatant actor);

    List<Combatant> getLivingAlliesOf(Combatant actor);

    default List<Combatant> getAliveEnemiesOf(Combatant actor) {
        return getLivingOpponentsOf(actor);
    }

    default List<Combatant> getAlliesOf(Combatant actor) {
        return getLivingAlliesOf(actor);
    }

    int getRoundNumber();
}
