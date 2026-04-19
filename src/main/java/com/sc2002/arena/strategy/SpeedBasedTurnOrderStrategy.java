package com.sc2002.arena.strategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.sc2002.arena.combatant.Combatant;

/**
 * Concrete TurnOrderStrategy that orders combatants by speed,
 * highest speed first.
 *
 * Responsibilities:
 * - implement the current game's turn-order rule
 * - preserve a deterministic, stable order for tied speeds
 *
 * Design notes:
 * - OCP: adding a RandomTurnOrderStrategy or priority-based ordering
 *   would not require modifying this class.
 * - SRP: this class only determines ordering; it does not execute turns.
 */
public class SpeedBasedTurnOrderStrategy implements TurnOrderStrategy {

    /**
     * Returns a new ordered list rather than mutating the incoming list.
     *
     * Stable sort behaviour matters here:
     * if two combatants have the same speed, their original relative order
     * is preserved, giving deterministic behaviour.
     */
    @Override
    public List<Combatant> getOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);

        // Higher speed acts first.
        // Stable sort keeps tie order predictable.
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());

        return ordered;
    }
}