package com.sc2002.arena.strategy;

import com.sc2002.arena.model.combatant.Combatant;
import java.util.List;
 
/**
 * Strategy interface for determining the order in which combatants act
 * each round.
 *
 * OCP  – new ordering schemes (e.g. random, initiative-roll) can be added
 *         by implementing this interface without touching BattleEngine.
 * DIP  – BattleEngine depends on this abstraction, never on a concrete class.
 */
public interface TurnOrderStrategy {
    List<Combatant> getOrder(List<Combatant> combatants);
}
 