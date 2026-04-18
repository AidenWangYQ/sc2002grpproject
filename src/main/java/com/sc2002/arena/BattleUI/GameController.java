package com.sc2002.arena.battleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.combatant.Warrior;
import com.sc2002.arena.combatant.Wizard;
import com.sc2002.arena.engine.ActionResolver;
import com.sc2002.arena.engine.BattleEngine;
import com.sc2002.arena.engine.BattleState;
import com.sc2002.arena.engine.TurnManager;
import com.sc2002.arena.item.HealPotion;
import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.item.Item;
import com.sc2002.arena.item.PowerStone;
import com.sc2002.arena.item.RagePotion;
import com.sc2002.arena.item.SmokeBomb;
import com.sc2002.arena.level.Level;
import com.sc2002.arena.level.LevelFactory;
import com.sc2002.arena.level.SpawnManager;
import com.sc2002.arena.strategy.SpeedBasedTurnOrderStrategy;

public class GameController {
    private final Scanner scanner;
    private final ConsoleBattleUI ui;

    public GameController() {
        this.scanner = new Scanner(System.in);
        this.ui = new ConsoleBattleUI(scanner);
    }

    public void run() {
        boolean playing = true;
        while (playing) {
            printLoadingScreen();

            Player player = promptPlayerChoice();
            Level level = promptDifficultyChoice();
            BattleEngine battleEngine = buildEngine(player, level);
            battleEngine.runBattle();

            System.out.println("Play again?");
            System.out.println("1) Yes");
            System.out.println("2) No");
            playing = readIntInRange(1, 2) == 1;
        }
        System.out.println("Thanks for playing.");
        scanner.close();
    }

    private BattleEngine buildEngine(Player player, Level level) {
        BattleState context = new BattleState(player);
        ActionResolver actionResolver = new ActionResolver();
        TurnManager turnManager = new TurnManager(new SpeedBasedTurnOrderStrategy(), actionResolver, ui);
        SpawnManager spawnManager = new SpawnManager(level, ui);
        return new BattleEngine(context, turnManager, spawnManager, ui);
    }

    private void printLoadingScreen() {
        System.out.println();
        System.out.println("TURN-BASED COMBAT ARENA");
        System.out.println("1) Warrior  HP:260 ATK:40 DEF:20 SPD:30");
        System.out.println("2) Wizard   HP:200 ATK:50 DEF:10 SPD:20");
        System.out.println("Items: Heal Potion, Power Stone, Smoke Bomb, Rage Potion");
    }

    private Player promptPlayerChoice() {
        System.out.println("Choose your combatant: 1) Warrior  2) Wizard");
        int choice = readIntInRange(1, 2);
        Inventory inventory = promptInventorySelection();
        return choice == 1 ? new Warrior(inventory) : new Wizard(inventory);
    }

    private Inventory promptInventorySelection() {
        String[] itemNames = { "Heal Potion", "Power Stone", "Smoke Bomb", "Rage Potion" };
        List<Item> startingItems = new ArrayList<>();
        System.out.println("Choose your first item:");
        printItemMenu(itemNames);
        startingItems.add(createItem(itemNames[readIntInRange(1, 4) - 1]));
        System.out.println("Choose your second item:");
        printItemMenu(itemNames);
        startingItems.add(createItem(itemNames[readIntInRange(1, 4) - 1]));
        return new Inventory(startingItems);
    }

    private void printItemMenu(String[] names) {
        for (int index = 0; index < names.length; index++) {
            System.out.printf("%d) %s%n", index + 1, names[index]);
        }
    }

    private Item createItem(String itemName) {
        return switch (itemName) {
            case "Heal Potion" -> new HealPotion();
            case "Power Stone" -> new PowerStone();
            case "Smoke Bomb" -> new SmokeBomb();
            case "Rage Potion" -> new RagePotion();
            default -> throw new IllegalArgumentException("Unknown item: " + itemName);
        };
    }

    private Level promptDifficultyChoice() {
        System.out.println("Choose difficulty:");
        System.out.println("1) Easy   - Three goblins.");
        System.out.println("2) Medium - Goblin and wolf, then two wolves.");
        System.out.println("3) Hard   - Two goblins, then a mixed backup wave.");
        System.out.println("4) Boss   - Ancient Dragon with Dragon Breath.");
        int choice = readIntInRange(1, 4);
        return LevelFactory.create(choice);
    }

    private int readIntInRange(int min, int max) {
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
}
