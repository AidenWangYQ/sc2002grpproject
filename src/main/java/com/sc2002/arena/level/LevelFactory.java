package com.sc2002.arena.level;

import com.sc2002.arena.combatant.Dragon;
import com.sc2002.arena.combatant.Goblin;
import com.sc2002.arena.combatant.Wolf;
 
import java.util.List;
 
/**
 * Constructs Level instances 
 *
 * SRP  – sole responsibility: map difficulty to a fully-configured Level.
 * OCP  – add a new level by adding one factory method; Level/Wave unchanged.
 * DIP  – callers receive a Level object; concrete enemy classes are only
 *         referenced here, not scattered through BattleEngine.
 *
 *   Level 1 (Easy)   – Initial: 3 Goblins         | No backup
 *   Level 2 (Medium) – Initial: 1 Goblin + 1 Wolf  | Backup: 2 Wolves
 *   Level 3 (Hard)   – Initial: 2 Goblins          | Backup: 1 Goblin + 2 Wolves
 */
public class LevelFactory {

    public static Level create(int levelNumber) {
        return switch (levelNumber) {
            case 1 -> createEasy();
            case 2 -> createMedium();
            case 3 -> createHard();
            case 4 -> createBoss();
            default -> throw new IllegalArgumentException(
                    "Unknown level number: " + levelNumber);
        };
    }

    private static Level createEasy() {
        Wave initial = new Wave(List.of(
                new Goblin("Goblin A"),
                new Goblin("Goblin B"),
                new Goblin("Goblin C")
        ));
        Wave backup = new Wave(List.of()); // no backup
        return new Level(1, Level.Difficulty.EASY, initial, backup);
    }

    private static Level createMedium() {
        Wave initial = new Wave(List.of(
                new Goblin("Goblin"),
                new Wolf("Wolf")
        ));
        Wave backup = new Wave(List.of(
                new Wolf("Wolf A"),
                new Wolf("Wolf B")
        ));
        return new Level(2, Level.Difficulty.MEDIUM, initial, backup);
    }

    private static Level createHard() {
        Wave initial = new Wave(List.of(
                new Goblin("Goblin A"),
                new Goblin("Goblin B")
        ));
        Wave backup = new Wave(List.of(
                new Goblin("Goblin C"),
                new Wolf("Wolf A"),
                new Wolf("Wolf B")
        ));
        return new Level(3, Level.Difficulty.HARD, initial, backup);
    }

    private static Level createBoss() {
        Wave initial = new Wave(List.of(
                new Dragon("Ancient Dragon")
        ));
        Wave backup = new Wave(List.of());
        return new Level(4, Level.Difficulty.BOSS, initial, backup);
    }
}
 
