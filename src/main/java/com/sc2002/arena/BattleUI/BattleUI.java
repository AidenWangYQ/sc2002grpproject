package com.sc2002.arena.BattleUI;

import java.util.List;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.item.Inventory;

public interface BattleUI {
    void printMessage(String message);

    void printRoundHeader(int roundNumber);

    void printTurnHeader(Combatant combatant);

    void printActionResult(ActionResult result);

    void printCannotAct(Combatant combatant);

    void printBackupSpawn(List<Enemy> backup);

    void printRoundSummary(BattleContext context);

    void printVictoryScreen(BattleContext context);

    void printDefeatScreen(BattleContext context);

    CombatAction promptPlayerAction(Player player, List<CombatAction> availableActions, List<Enemy> livingEnemies);

    Enemy promptTargetSelection(List<Enemy> livingEnemies);

    int promptItemSlotSelection(Player player, List<Inventory.InventorySlot> availableSlots);
}
