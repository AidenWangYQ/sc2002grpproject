package com.sc2002.arena.engine;
import com.sc2002.arena.model.combatant.Combatant;
import com.sc2002.arena.strategy.TurnOrderStrategy;
import sc2002.battle.domain.Combatant;
import java.util.List;
 
/**
 * Determines the ordered list of combatants who will act each round.
 *
 * SRP  – sole responsibility: produce a correctly ordered turn list.
 * DIP  – depends on TurnOrderStrategy interface, not SpeedBasedTurnOrderStrategy.
 * OCP  – swapping to a different ordering requires only injecting a different
 *         strategy; TurnManager itself never changes.
 */

public class TurnManager {
 
    private final TurnOrderStrategy strategy;

    public TurnManager(TurnOrderStrategy strategy) {
        this.strategy = strategy;
    }
 
    public List<Combatant> getTurnOrder(BattleContext context) {
        List<Combatant> living = context.getLivingCombatants();
        return strategy.getOrder(living);
    }
}
 