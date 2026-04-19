package com.sc2002.arena.strategy;

import java.util.List;

import com.sc2002.arena.combatant.Combatant;

/**
 * Read-only context interface exposing the minimum battle information
 * needed by strategies and other decision-making components.
 *
 * Responsibilities:
 * - provide access to allies/opponents of a combatant
 * - expose current round number
 *
 * Design notes:
 * - Keeps strategy classes decoupled from the full BattleState implementation.
 * - Supports DIP by allowing strategies to depend on this narrow abstraction
 *   instead of a heavyweight engine/domain class.
 * - Also supports ISP because only the needed battle query methods are exposed.
 */
public interface BattleContext {

    /**
     * @param actor the acting combatant
     * @return all living opponents of the actor
     */
    List<Combatant> getLivingOpponentsOf(Combatant actor);

    /**
     * @param actor the acting combatant
     * @return all living allies of the actor
     */
    List<Combatant> getLivingAlliesOf(Combatant actor);

    /**
     * Convenience alias kept for readability in strategies that think in terms
     * of "enemies" rather than generic opponents.
     */
    default List<Combatant> getAliveEnemiesOf(Combatant actor) {
        return getLivingOpponentsOf(actor);
    }

    /**
     * Convenience alias kept for readability.
     */
    default List<Combatant> getAlliesOf(Combatant actor) {
        return getLivingAlliesOf(actor);
    }

    /**
     * @return current round number, useful for effects or strategies that may
     * depend on battle progression
     */
    int getRoundNumber();
}