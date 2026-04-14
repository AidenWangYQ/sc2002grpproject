package com.sc2002.arena.controller;

import com.sc2002.arena.BattleUI.ConsoleBattleUI;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Warrior;
import com.sc2002.arena.combatant.Wizard;
import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.engine.Battleengine;
import com.sc2002.arena.engine.TurnManager;
import com.sc2002.arena.engine.CooldownManager;
import com.sc2002.arena.engine.EffectManager;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.level.Level;
import com.sc2002.arena.level.LevelFactory;
import com.sc2002.arena.level.SpawnManager;
import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.item.Potion;
import com.sc2002.arena.item.PowerStone;
import com.sc2002.arena.item.SmokeBomb;
import com.sc2002.arena.strategy.SpeedBasedTurnOrderStrategy;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

    public class GameController {
        private final Scanner scanner;
        public final ConsoleBattleUI ui;  // Changed from BattleUI to ConsoleBattleUI

        public GameController() {
            this.scanner = new Scanner(System.in);
            this.ui = new ConsoleBattleUI(scanner);  // This remains valid
        }

        public void run() {
            boolean playing = true;
            while (playing) {
                printLoadingScreen();

                // Initialize player and enemies
                Player player = promptPlayerChoice();
                Level level = promptDifficultyChoice();

                // Use buildEngine to create the BattleEngine
                Battleengine battleEngine = buildEngine(player, level);

                // Start the battle
                battleEngine.startBattle();

                // Ask the player if they want to replay
                playing = promptReplay();
            }
            System.out.println("Thanks for playing! Goodbye.");
            scanner.close();
        }

        // Place buildEngine() method here

        private Battleengine buildEngine(Player player, Level level)  {
            // Create the BattleContext using just the Player
            BattleContext context = new BattleContext(player);

            // Add enemies to the BattleContext
            List<Enemy> initialEnemies = level.getInitialWave().getEnemies();
            for (Enemy enemy : initialEnemies) {
                context.addInitialEnemy(enemy);  // Add enemies to the context
            }

            // Create the rest of the necessary components
            TurnManager turnManager = new TurnManager(new SpeedBasedTurnOrderStrategy());
            CooldownManager cooldownManager = new CooldownManager();
            EffectManager effectManager = new EffectManager();
            ActionResolver actionResolver = new ActionResolver(cooldownManager, ui);
            SpawnManager spawnManager = new SpawnManager(level, ui);

            // Return a new BattleEngine instance
            return new Battleengine(
                    context,          // Pass the BattleContext with player and enemies
                    turnManager,
                    actionResolver,
                    effectManager,
                    cooldownManager,
                    spawnManager,
                    ui);
        }

        // Other methods like printLoadingScreen(), promptPlayerChoice(), etc.

        private void printLoadingScreen() {
            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║       TURN-BASED COMBAT ARENA  (SC2002)          ║");
            System.out.println("╚══════════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("  ── PLAYERS ──────────────────────────────────────");
            System.out.println("  1) Warrior  HP:200 ATK:40 DEF:20 SPD:30");
            System.out.println("     Special: Shield Bash – stun one enemy for 2 turns");
            System.out.println("  2) Wizard   HP:200 ATK:50 DEF:10 SPD:20");
            System.out.println("     Special: Arcane Blast – hit all enemies; +10 ATK per kill");
            System.out.println();
            System.out.println("  ── ENEMIES ───────────────────────────────────────");
            System.out.println("  Goblin  HP:55  ATK:35 DEF:15 SPD:25");
            System.out.println("  Wolf    HP:40  ATK:45 DEF:5  SPD:35");
            System.out.println();
            System.out.println("  ── ITEMS ─────────────────────────────────────────");
            System.out.println("  1) Potion      – Heal 100 HP (capped at max HP)");
            System.out.println("  2) Power Stone – Free use of special skill (no cooldown)");
            System.out.println("  3) Smoke Bomb  – Enemy attacks deal 0 damage this turn + next");
            System.out.println();
            System.out.println("  ── DIFFICULTY ────────────────────────────────────");
            System.out.println("  1) Easy   – 3 Goblins");
            System.out.println("  2) Medium – 1 Goblin + 1 Wolf | Backup: 2 Wolves");
            System.out.println("  3) Hard   – 2 Goblins         | Backup: 1 Goblin + 2 Wolves");
            System.out.println();
        }

        private Player promptPlayerChoice() {
            System.out.println("  Choose your class (1 = Warrior, 2 = Wizard):");
            int choice = readIntInRange(1, 2);
            Inventory inventory=promptItemSelection();
            Player player = (choice == 1) ? new Warrior(inventory) : new Wizard(inventory);
            System.out.printf(" → %s selected.%n%n", player.getName());
            return player;
        }

        private Inventory promptItemSelection() {
        String[] itemNames = { "Potion", "Power Stone", "Smoke Bomb" };
        List<Item> startingItems = new ArrayList<>();

        // Prompt the player for their first item choice
        System.out.println("  Choose your first item:");
        printItemMenu(itemNames);
        Item firstItem = createItem(itemNames[readIntInRange(1, 3) - 1]);
        startingItems.add(firstItem);  // Add the first selected item

        // Prompt the player for their second item choice
        System.out.println("  Choose your second item (duplicates allowed):");
        printItemMenu(itemNames);
        Item secondItem = createItem(itemNames[readIntInRange(1, 3) - 1]);
        startingItems.add(secondItem);  // Add the second selected item

        return new Inventory(startingItems);  // Pass the list of 2 items to the Inventory constructor
    }

        // Helper method to create Item based on player selection using switch
        private Item createItem(String itemName) {
            switch (itemName) {
                case "Potion":
                    return new Potion();  // Return a new Potion object
                case "Power Stone":
                    return new PowerStone();  // Return a new Power Stone object
                case "Smoke Bomb":
                    return new SmokeBomb();  // Return a new Smoke Bomb object
                default:
                    throw new IllegalArgumentException("Unknown item: " + itemName);  // Handle invalid input
            }
        }

        // Helper method to print the player's current inventory
        private void printInventory(Inventory inventory) {
        List<Inventory.InventorySlot> slots = inventory.getSlots();
        if (slots.isEmpty()) {
            System.out.println("  No items in inventory.");
        } else {
            System.out.println("  Inventory:");
            int index = 1;
            for (Inventory.InventorySlot slot : slots) {
                String status = slot.isUsable() ? "Usable" : "Consumed";
                System.out.printf("  %d) %s - %s%n", index++, slot.item().getName(), status);
            }
        }
    }

        private void printItemMenu(String[] names) {
            for (int i = 0; i < names.length; i++) {
                System.out.printf("%d) %s%n", i + 1, names[i]);
            }
        }

        private Level promptDifficultyChoice() {
            System.out.println(" Choose difficulty (1 = Easy, 2 = Medium, 3 = Hard):");
            int choice = readIntInRange(1, 3);
            Level level = LevelFactory.create(choice);
            System.out.printf(" → %s selected.%n%n", level);
            return level;
        }

        private boolean promptReplay() {
            System.out.println("  Would you like to play again?");
            System.out.println("    1) Play again");
            System.out.println("    2) Exit");
            int choice = readIntInRange(1, 2);
            return choice == 1;
        }

        private int readIntInRange(int min, int max) {
            while (true) {
                System.out.printf("  Enter choice (%d–%d): ", min, max);
                try {

                    int val = Integer.parseInt(scanner.nextLine().trim());
                    if (val >= min && val <= max) return val;
                    System.out.printf("  Please enter a number between %d and %d.%n", min, max);
                } catch (NumberFormatException e) {
                    System.out.println("  Invalid input. Please enter a number.");
                }
            }
        }
    }
