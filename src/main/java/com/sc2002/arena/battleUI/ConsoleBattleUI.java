package com.sc2002.arena.battleUI;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.combatant.SpecialSkillUser;
import com.sc2002.arena.engine.BattleState;
import com.sc2002.arena.item.Inventory;

public class ConsoleBattleUI implements BattleUI {
    private final Scanner scanner;

    public ConsoleBattleUI(Scanner scanner) {
        this.scanner = scanner;
    } //Used to allow for inputs into the console, such as choosing actions, targets and item slots.

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    } //Used to print any message required

    @Override
    public void printRoundHeader(int roundNumber) {
        System.out.println();
        System.out.println("Round " + roundNumber); 
        System.out.println("==================================");
    } //Used to print the current round number

    @Override
    public void printTurnHeader(Combatant combatant) {       //Used to print whose turn it currently is
        System.out.println();
        System.out.println("----------------------------------");
        System.out.println("Your Turn: " + combatant.getName());
        System.out.println(formatCombatantStatus(combatant));
        System.out.println("----------------------------------");
    } 
    

    @Override
    public void printActionResult(ActionResult result) {     //Used to print the result of an action by any individual for different situations such as damage done or received or items used or special skills used.
        System.out.println();
        System.out.println("-->" + result.getActor().getName() + " used " + result.getActionName() + ".");
        System.out.println();
        System.out.println("--------------Action Result------------------");
        System.out.println();
        for (String note: result.getNotes()) {
            System.out.println("  " + note);
        }
        for (ActionResult.EffectEvent event : result.getEffectEvents()) {
            System.out.printf("  %s gained effect: %s.%n", event.target().getName(), event.effectName());
        }
        for (ActionResult.DamageEvent event : result.getDamageEvents()) {
            System.out.printf("-->%s took %d damage (%d -> %d).%n",
                    event.target().getName(),
                    event.appliedDamage(),
                    event.beforeHp(),
                    event.afterHp());
        }
        for (ActionResult.HealEvent event : result.getHealEvents()) {
            System.out.printf("  %s healed %d HP (%d -> %d).%n",
                    event.target().getName(),
                    event.healedAmount(),
                    event.beforeHp(),
                    event.afterHp());
        }
        for (ActionResult.DefeatEvent event : result.getDefeatEvents()) {
            System.out.println("  " + event.target().getName() + " was defeated.");
        }
        if (result.getConsumedItem() != null) {
            System.out.printf("  Consumed item: %s (slot %d).%n",
                    result.getConsumedItem().itemName(),
                    result.getConsumedItem().slotIndex() + 1);
        }
        if (result.getCooldownChange() != null) {
            System.out.printf("  Cooldown: %d -> %d.%n",
                    result.getCooldownChange().before(),
                    result.getCooldownChange().after());
        }
    }

    @Override
    public void printCannotAct(Combatant combatant) {   //Used to inform the user that the combatant is stunned
        System.out.println("-->"+ combatant.getName() + " cannot act this turn.");
    }

    @Override
    public void printBackupSpawn(List<Enemy> backup) {  //Used to inform the user that another wave of enemies have arrived and show the different enemies.
        System.out.println("Reinforcements have arrived!");
        for (Enemy enemy : backup) {
            System.out.println("+" + enemy.getName());
        }
    }

    @Override
    public void printRoundSummary(BattleState context) {  //Used to summarise the end of the game where they show the current HP and effects of the player and the enemies, as well as how many enemies are remaining.
        System.out.println("Player Status:");
        System.out.println("  " + formatCombatantStatus(context.getPlayer()));
        System.out.println("Enemy Status:");
        List<Enemy> livingEnemies = context.getLivingEnemies();
        if (livingEnemies.isEmpty()) {
            System.out.println("  None");
        } else {
            for (Enemy enemy : livingEnemies) {
                System.out.println("  " + formatCombatantStatus(enemy));
            }
        }
        System.out.printf("Remaining enemies: %d%n", context.getRemainingEnemyCount());
    }

    @Override
    public void printVictoryScreen(BattleState context) { //Used to print to declare the user as the winner in how many rounds and the user's current HP.
        System.out.printf("Victory in %d round(s). Remaining HP: %d/%d%n",
                context.getRoundNumber(),
                context.getPlayer().getCurrentHp(),
                context.getPlayer().getMaxHp());
    }

    @Override
    public void printDefeatScreen(BattleState context) {  //Used to print to declare that the user has lost after how mnay rounds and how many enemies are remaining.
        System.out.printf("Defeat after %d round(s). Enemies remaining: %d%n",
                context.getRoundNumber(),
                context.getRemainingEnemyCount());
    }

    @Override
    public CombatAction promptPlayerAction(Player player, List<CombatAction> availableActions, List<Enemy> livingEnemies) {  //Used to invoke the user for an action to perform and read the user's input.
        System.out.println("Choose an action:");
        for (int index = 0; index < availableActions.size(); index++) {
            System.out.printf("%d) %s%n", index + 1, availableActions.get(index).getName());
        }
        return availableActions.get(readIntInRange(1, availableActions.size()) - 1);
    }

    @Override
    public Enemy promptTargetSelection(List<Enemy> livingEnemies) {  //Used to ask the user for a target of who they want to use the action on.
        if (livingEnemies.isEmpty()) {
            throw new IllegalStateException("No living enemies available for targeting.");
        }
        System.out.println("Choose a target:");
        for (int index = 0; index < livingEnemies.size(); index++) {
            Enemy enemy = livingEnemies.get(index);
            System.out.printf("%d) %s (HP %d)%n", index + 1, enemy.getName(), enemy.getCurrentHp());
        }
        return livingEnemies.get(readIntInRange(1, livingEnemies.size()) - 1);
    }

    @Override
    public int promptItemSlotSelection(Player player, List<Inventory.InventorySlot> availableSlots) {   //Used to ask the user for which item the user wants to use
        if (availableSlots.isEmpty()) {
            throw new IllegalStateException("No usable item slots available.");
        }
        System.out.println("Choose an item slot:");
        for (int index = 0; index < availableSlots.size(); index++) {
            Inventory.InventorySlot slot = availableSlots.get(index);
            System.out.printf("%d) Slot %d - %s%n", index + 1, slot.index() + 1, slot.item().getName());
        }
        return availableSlots.get(readIntInRange(1, availableSlots.size()) - 1).index();
    }

    public int readIntInRange(int min, int max) {   //Used to read an integer input from the user and ensure that it is within a specified range. It will keep prompting the user until a valid input is received.
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Enter a number between %d and %d.%n", min, max);
        }
    }

    public String formatCombatantStatus(Combatant combatant) {  //Used to format the status of a combatant, showing their name, current HP, max HP, skill cooldown if they have one and any active effects.
        StringBuilder status = new StringBuilder();
        status.append(combatant.getName())
                .append(" HP ")
                .append(combatant.getCurrentHp())
                .append("/")
                .append(combatant.getMaxHp());

        if (combatant instanceof SpecialSkillUser skillUser) {
            status.append(" | Skill CD: ").append(skillUser.getSpecialSkillCooldown());
        }

        String effects = combatant.getActiveEffects().stream()
                .map(effect -> effect.getName())
                .collect(Collectors.joining(", "));
        status.append(" | Effects: ").append(effects.isEmpty() ? "None" : effects);
        return status.toString();
    }
}
