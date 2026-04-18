package com.sc2002.arena.controller;

import com.sc2002.arena.engine.*;
import com.sc2002.arena.level.Level;
import com.sc2002.arena.level.LevelFactory;
import com.sc2002.arena.level.SpawnManager;
import com.sc2002.arena.model.combatant.Player;
import com.sc2002.arena.model.combatant.Warrior;
import com.sc2002.arena.model.combatant.Wizard;
import com.sc2002.arena.model.item.Item;
import com.sc2002.arena.model.item.ItemFactory;
import com.sc2002.arena.strategy.SpeedBasedTurnOrderStrategy;
import com.sc2002.arena.ui.BattleUI;
import com.sc2002.arena.ui.ConsoleBattleUI;
 
import java.util.Scanner;
 
public class GameController {
 
    private final Scanner scanner;
    private final BattleUI ui;
 
    public GameController() {
        this.scanner = new Scanner(System.in);
        this.ui      = new ConsoleBattleUI(scanner);
    }
 
    // The game system where this allows for the game to run, therefore allowing for the game to fully run. Where they consist of the different engines and the different mechanisms of the game to compile together.
    public void run() {
        boolean playing = true;
        while (playing) {
            printLoadingScreen();
 
            Player  player = promptPlayerChoice();
            promptItemSelection(player);
            Level   level  = promptDifficultyChoice();
 
            BattleEngine engine = buildEngine(player, level);
            engine.startBattle();
 
            playing = promptReplay();
        }
 
        System.out.println("Thanks for playing! Goodbye.");
        scanner.close();
    }
 //Consist of the menu and the loading screen, showing what should be displayed
    private void printLoadingScreen() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       TURN-BASED COMBAT ARENA  (SC2002)          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("  ── PLAYERS ──────────────────────────────────────");
        System.out.println("  1) Warrior  HP:200 ATK:40 DEF:20 SPD:30");
        System.out.println("     Special: Shield Bash : stun one enemy for 2 turns");
        System.out.println("  2) Wizard   HP:200 ATK:50 DEF:10 SPD:20");
        System.out.println("     Special: Arcane Blast : hit all enemies; +10 ATK per kill");
        System.out.println();
        System.out.println("  ── ENEMIES ───────────────────────────────────────");
        System.out.println("  Goblin  HP:55  ATK:35 DEF:15 SPD:25");
        System.out.println("  Wolf    HP:40  ATK:45 DEF:5  SPD:35");
        System.out.println();
        System.out.println("  ── ITEMS ─────────────────────────────────────────");
        System.out.println("  1) Potion      : Heal 100 HP (capped at max HP)");
        System.out.println("  2) Power Stone : Free use of special skill (no cooldown)");
        System.out.println("  3) Smoke Bomb  : Enemy attacks deal 0 damage this turn + next");
        System.out.println();
        System.out.println("  ── DIFFICULTY ────────────────────────────────────");
        System.out.println("  1) Easy   : 3 Goblins");
        System.out.println("  2) Medium : 1 Goblin + 1 Wolf | Backup: 2 Wolves");
        System.out.println("  3) Hard   : 2 Goblins         | Backup: 1 Goblin + 2 Wolves");
        System.out.println();
    }
 //Asking the user to choose their class
    private Player promptPlayerChoice() {
        System.out.println("  Choose your class (1 = Warrior, 2 = Wizard):");
        int choice = readIntInRange(1, 2);
        Player player;
        if (choice == 1) {
            player = new Warrior("Player");
        } else {
            player = new Wizard("Player");
        }
        System.out.printf(" → %s selected.%n%n", player.getName());
        return player;
    }
 //prompting the user to choose the items they want and then adding them into the inventory
    private void promptItemSelection(Player player) {
        String[] itemNames = { "Potion", "Power Stone", "Smoke Bomb" };
 
        System.out.println("Choose your first item:");
        printItemMenu(itemNames);
        Item first = ItemFactory.create(itemNames[readIntInRange(1, 3) - 1]);
        player.addItem(first);
 
        System.out.println("  Choose your second item (duplicates allowed):");
        printItemMenu(itemNames);
        Item second = ItemFactory.create(itemNames[readIntInRange(1, 3) - 1]);
        player.addItem(second);
 
        System.out.printf("  → Items: %s + %s%n%n",
                first.getName(), second.getName());
    }
 
    private void printItemMenu(String[] names) {
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%d) %s%n", i + 1, names[i]);
        }
    }
 //asking the user to choose the difficulty level
    private Level promptDifficultyChoice() {
        System.out.println("  Choose difficulty (1 = Easy, 2 = Medium, 3 = Hard):");
        int choice = readIntInRange(1, 3);
        Level level = LevelFactory.create(choice);
        System.out.printf("→ %s selected.%n%n", level);
        return level;
    }
//compiling based on all the user choices then putting into battle engine to start the battle;
    private BattleEngine buildEngine(Player player, Level level) {
        BattleContext   context         = new BattleContext(player);
        TurnManager     turnManager     = new TurnManager(new SpeedBasedTurnOrderStrategy());
        CooldownManager cooldownManager = new CooldownManager();
        EffectManager   effectManager   = new EffectManager();
        ActionResolver  actionResolver  = new ActionResolver(cooldownManager, ui);
        SpawnManager    spawnManager    = new SpawnManager(level, ui);
 
        return new BattleEngine(
                context,
                turnManager,
                actionResolver,
                effectManager,
                cooldownManager,
                spawnManager,
                ui);
    }

    private boolean promptReplay() {
        System.out.println("Would you like to play again?");
        System.out.println("1) Play again");
        System.out.println("2) Exit");
        int choice = readIntInRange(1, 2);
        return choice == 1;
    }

    private int readIntInRange(int min, int max) {
        while (true) {
            System.out.printf("  Enter choice (%d–%d): ", min, max);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.printf("Please enter a number between %d and %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }
}
 