package com.sc2002.arena.BattleUI;  // Corrected package

import com.sc2002.arena.battle.domain.Combatant;  // Corrected import
import com.sc2002.arena.battle.domain.Player;     // Corrected import
import com.sc2002.arena.battle.domain.BattleContext;  // Corrected import
import com.sc2002.arena.battle.domain.Item;       // Corrected import
import com.sc2002.arena.battle.domain.Enemy;      // Corrected import
import com.sc2002.arena.battle.domain.ActionType; // Corrected import

import java.util.List;

public interface BattleUI {

    // Display general messages (e.g., victory, defeat)
    void printMessage(String message);

    void printEliminated(Combatant target);

    // Display the current round header
    void printRoundHeader(int roundNumber);

    // Display the summary of the current round (e.g., remaining enemies)
    void printRoundSummary(BattleContext context);

    // Display the result of a basic attack (damage dealt)
    void printAttack(Combatant attacker, Combatant target, int damage);

    // Display the result of a defend action
    void printDefend(Player player);

    // Display the victory screen after player wins
    void printVictoryScreen(BattleContext context);

    // Display the defeat screen after player loses
    void printDefeatScreen(BattleContext context);

    // Prompt the player to choose an action (e.g., basic attack, defend)
    ActionType promptActionChoice(Player player, List<Enemy> enemies);

    // Prompt the player to select an item from their inventory
    Item promptItemSelection(List<Item> usableItems);

    // Prompt the player to select a target (for skills or attacks)
    Enemy promptTargetSelection(List<Enemy> enemies);

    // Display the cooldown status for the player's special skill
    void printCooldownStatus(Player player);

    // Display any active effects on the player or enemies
    void printEffectSummary(Combatant combatant);

    void printSmokeBombBlock(Enemy enemy,Player player);

    void printTurnHeader(Combatant combatant);

    void printStunSkip(Combatant combatant);

    void printBackupSpawn(List<Enemy> backup);
}