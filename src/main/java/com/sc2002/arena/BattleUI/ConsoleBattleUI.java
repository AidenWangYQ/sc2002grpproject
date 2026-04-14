package com.sc2002.arena.BattleUI;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.DefendAction;
import com.sc2002.arena.action.UseSpecialSkillAction;
import com.sc2002.arena.action.UseItemAction;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("  ── ROUND " + roundNumber + " ─────────────────────────");
    }

    @Override
    public void printRoundSummary(BattleContext context) {
        System.out.println("Round Summary:");
        System.out.printf("  Round %d - Player HP: %d/%d%n",
                context.getRoundNumber(), context.getPlayer().getCurrentHp(), context.getPlayer().getMaxHp());
        System.out.printf("  Remaining Enemies: %d%n", context.getRemainingEnemyCount());
    }

    @Override
    public void printAttack(Combatant attacker, Combatant target, int damage) {
        System.out.printf("  %s attacks %s for %d damage.%n", attacker.getName(), target.getName(), damage);
    }

    public void printEliminated(Combatant target) {
        System.out.println(target.getName() + " has been defeated!");
    }

    @Override
    public void printDefend(Player player) {
        System.out.printf("  %s defends this turn, increasing defense by 10.%n", player.getName());
    }

    @Override
    public void printVictoryScreen(BattleContext context) {
        System.out.println("Congratulations, you have defeated all enemies!");
        System.out.printf("Remaining HP: %d/%d | Total Rounds: %d%n",
                context.getPlayer().getCurrentHp(), context.getPlayer().getMaxHp(), context.getRoundNumber());
    }

    @Override
    public void printDefeatScreen(BattleContext context) {
        System.out.println("Don’t give up, try again!");
        System.out.printf("Enemies remaining: %d | Total Rounds Survived: %d%n",
                context.getRemainingEnemyCount(), context.getRoundNumber());
    }
    public void printSmokeBombBlock(Enemy enemy, Player player){
        System.out.printf("Enemy's attach has been blocked by %s Smokebomb", player.getName());
    }

    @Override
    public CombatAction promptActionChoice(Player player, List<Enemy> enemies) {
    System.out.println("Choose an action for player:");
    System.out.println("1) Basic Attack");
    System.out.println("2) Defend");
    System.out.println("3) Special Skill");
    System.out.println("4) Use Item");

    int choice = readIntInRange(1, 4);
    switch (choice) {
        case 1: return new BasicAttackAction();
        case 2: return new DefendAction();
        case 3: return new UseSpecialSkillAction();
        case 4: return new UseItemAction();
        default: return new BasicAttackAction();
        }
    }

    @Override
    public Item promptItemSelection(List<Item> usableItems) {
        System.out.println("Choose an item:");
        for (int i = 0; i < usableItems.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, usableItems.get(i).getName());
        }
        return usableItems.get(readIntInRange(1, usableItems.size()) - 1);
    }

    public void printStunSkip(Combatant combatant) {
        System.out.printf("%s have been stunned, and thus turn has been skipped");
    }
    @Override
    public Enemy promptTargetSelection(List<Enemy> enemies) {
        System.out.println("Select a target:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, enemies.get(i).getName());
        }
        return enemies.get(readIntInRange(1, enemies.size()) - 1);
    }

    @Override
    public void printCooldownStatus(Player player) {
        if (player.getSpecialCooldownRemaining() == 0) {
            System.out.printf("%s's special skill is ready!%n", player.getName());
        } else {
            System.out.printf("%s's special skill cooldown: %d turn(s)%n",
                    player.getName(), player.getSpecialCooldownRemaining());
        }
    }

    @Override
public void printEffectSummary(Combatant combatant) {
    System.out.print("Active Effects: ");
    List<StatusEffect> effects = combatant.getStatusEffects();
    if (effects.isEmpty()) {
        System.out.println("None.");
    } else {
        for (StatusEffect effect : effects) {
            System.out.printf("%s ", effect.getName());
        }
        System.out.println();
    }
}

    @Override
    public void printTurnHeader(Combatant combatant) {
       System.out.printf("It is %s's turn",combatant.getName());
    }

    private int readIntInRange(int min, int max) {
        while (true) {
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    public void printBackupSpawn(List<Enemy> backup) {
        // Print a message indicating that the backup enemies have arrived
        System.out.println("Backup wave has arrived!");

        // Optionally print details of the backup enemies
        System.out.println("Enemies in the backup wave:");
        for (Enemy enemy : backup) {
            System.out.println("  - " + enemy.getName() + " (HP: " + enemy.getCurrentHp() + ")");
        }
    }
}