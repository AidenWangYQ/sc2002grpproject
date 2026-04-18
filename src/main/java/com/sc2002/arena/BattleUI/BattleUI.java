package com.sc2002.arena.battleUI;

import java.util.List;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.engine.BattleState;
import com.sc2002.arena.item.Inventory;

public interface BattleUI {
    void printMessage(String message);

    void printRoundHeader(int roundNumber);

    void printTurnHeader(Combatant combatant);

    void printActionResult(ActionResult result);

    void printCannotAct(Combatant combatant);

    void printBackupSpawn(List<Enemy> backup);

    void printRoundSummary(BattleState context);

    void printVictoryScreen(BattleState context);

    void printDefeatScreen(BattleState context);

    CombatAction promptPlayerAction(Player player, List<CombatAction> availableActions, List<Enemy> livingEnemies);

    Enemy promptTargetSelection(List<Enemy> livingEnemies);

    int promptItemSlotSelection(Player player, List<Inventory.InventorySlot> availableSlots);
}
