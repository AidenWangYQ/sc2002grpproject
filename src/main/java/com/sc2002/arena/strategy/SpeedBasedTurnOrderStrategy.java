package com.sc2002.arena.strategy;

import com.sc2002.arena.model.combatant.Combatant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
 
/**
 * Concrete TurnOrderStrategy that orders combatants by speed, highest first.
 * OCP – adding a RandomTurnOrderStrategy would not touch this class.
 * SRP – sole responsibility: sort combatants by speed.
 */
public class SpeedBasedTurnOrderStrategy implements TurnOrderStrategy {
 
    @Override
    public List<Combatant> getOrder(List<Combatant> combatants) {
        List<Combatant> ordered = new ArrayList<>(combatants);
        // Stable sort: ties preserve original list order
        ordered.sort(Comparator.comparingInt(Combatant::getSpeed).reversed());
        return ordered;
    }
}