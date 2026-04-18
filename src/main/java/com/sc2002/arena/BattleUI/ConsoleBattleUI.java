package com.sc2002.arena.BattleUI;

import java.util.List;
import java.util.Scanner;

import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.item.Inventory;

public class ConsoleBattleUI implements BattleUI {
    private final Scanner scanner;

    public ConsoleBattleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printRoundHeader(int roundNumber) {
        System.out.printf("\n=== Round %d ===\n", roundNumber);
    }

    @Override
    public void printTurnHeader(Combatant combatant) {
<<<<<<< Updated upstream
        System.out.println();
        System.out.println("Turn: " + combatant.getName());
=======
        System.out.printf("------------- Turn------------\n");
        System.out.println(combatant.getName());
        System.out.println("  " + formatCombatantStatus(combatant));
>>>>>>> Stashed changes
    }

    @Override
    public void printActionResult(ActionResult result) {
        System.out.println(" "+result.getActor().getName() + " used " + result.getActionName() + ".");
        for (ActionResult.DamageEvent event : result.getDamageEvents()) {
            System.out.printf("  %s took %d damage (%d -> %d).%n",
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
        for (ActionResult.EffectEvent event : result.getEffectEvents()) {
            System.out.printf("  %s gained effect: %s.%n", event.target().getName(), event.effectName());
        }
        for (ActionResult.DefeatEvent event : result.getDefeatEvents()) {
            System.out.println(event.target().getName() + " was defeated.");
        }
        for (String note : result.getNotes()) {
            System.out.println(note);
        }
        if (result.getConsumedItem() != null) {
            System.out.printf("Consumed item: %s (slot %d).%n",
                    result.getConsumedItem().itemName(),
                    result.getConsumedItem().slotIndex() + 1);
        }
        if (result.getCooldownChange() != null) {
            System.out.printf("Cooldown: %d -> %d.%n",
                    result.getCooldownChange().before(),
                    result.getCooldownChange().after());
        }
    }

    @Override
    public void printCannotAct(Combatant combatant) {
        System.out.println(combatant.getName() + " cannot act this turn.");
    }

    @Override
    public void printBackupSpawn(List<Enemy> backup) {
        System.out.println("Backup wave has arrived.");
        for (Enemy enemy : backup) {
            System.out.println(enemy.getName());
        }
    }

    @Override
<<<<<<< Updated upstream
    public void printRoundSummary(BattleContext context) {
        System.out.printf("Player HP: %d/%d | Remaining enemies: %d%n",
                context.getPlayer().getCurrentHp(),
                context.getPlayer().getMaxHp(),
                context.getRemainingEnemyCount());
=======
    public void printRoundSummary(BattleState context) {
        System.out.println("\nRound Summary:");
        System.out.println("Player Status:");
        System.out.println(formatCombatantStatus(context.getPlayer()));
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
>>>>>>> Stashed changes
    }

    @Override
    public void printVictoryScreen(BattleContext context) {
        System.out.printf("Victory in %d round(s). Remaining HP: %d/%d%n",
                context.getRoundNumber(),
                context.getPlayer().getCurrentHp(),
                context.getPlayer().getMaxHp());
    }

    @Override
    public void printDefeatScreen(BattleContext context) {
        System.out.printf("Defeat after %d round(s). Enemies remaining: %d%n",
                context.getRoundNumber(),
                context.getRemainingEnemyCount());
    }

    @Override
    public CombatAction promptPlayerAction(Player player, List<CombatAction> availableActions, List<Enemy> livingEnemies) {
        System.out.println("Choose an action:");
        for (int index = 0; index < availableActions.size(); index++) {
            System.out.printf("%d) %s%n", index + 1, availableActions.get(index).getName());
        }
        return availableActions.get(readIntInRange(1, availableActions.size()) - 1);
    }

    @Override
    public Enemy promptTargetSelection(List<Enemy> livingEnemies) {
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
    public int promptItemSlotSelection(Player player, List<Inventory.InventorySlot> availableSlots) {
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

    public int readIntInRange(int min, int max) {
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
<<<<<<< Updated upstream
=======

    public String formatCombatantStatus(Combatant combatant) {
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
>>>>>>> Stashed changes
}
