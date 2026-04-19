package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.combatant.Combatant;

/**
 * Strategy interface for determining the order in which combatants act
 * during a round.
 *
 * Responsibilities:
 * - define a common contract for turn ordering
 * - allow BattleEngine / TurnManager to remain independent of any one
 *   concrete ordering rule
 *
 * Design notes:
 * - Demonstrates OCP: new ordering rules can be introduced by adding a new
 *   implementation instead of changing engine logic.
 * - Demonstrates DIP: high-level components depend on this abstraction,
 *   not a concrete ordering class.
 */
public interface TurnOrderStrategy {

    /**
     * Produce the ordered list of combatants for the current round.
     *
     * @param combatants all currently living combatants
     * @return combatants arranged in the order they should act
     */
    List<Combatant> getOrder(List<Combatant> combatants);
}