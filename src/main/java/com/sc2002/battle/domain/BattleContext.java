package sc2002.battle.domain;

import java.util.List;
import java.util.ArrayList;
import sc2002.battle.domain.Combatant;
import sc2002.battle.domain.Enemy;
import sc2002.battle.domain.Player;

public class BattleContext {
    private final Player player;
    private final List<Enemy> allEnemies;
    private List<Enemy> activeEnemies;  // List of enemies currently active in the battle
    private int roundNumber;
    private boolean battleOver;

    public BattleContext(Player player, List<Enemy> allEnemies) {
        this.player = player;
        this.allEnemies = allEnemies;
        this.activeEnemies = new ArrayList<>(allEnemies);  // Initialize active enemies as all enemies
        this.roundNumber = 1;
    }

    // Method to get the total number of enemies defeated
    public int getTotalEnemiesDefeated() {
        return (int) allEnemies.stream().filter(enemy -> !enemy.isAlive()).count();
    }

    // Method to get the number of remaining enemies
    public int getRemainingEnemyCount() {
        return (int) activeEnemies.stream().filter(Combatant::isAlive).count();
    }

    // Method to get the remaining turns/rounds
    public int getRemainingTurns() {
        return 10 - roundNumber;
    }

    // Method to get all living enemies of the actor
    public List<Combatant> getAliveEnemiesOf(Combatant actor) {
        List<Combatant> aliveEnemies = new ArrayList<>();
        for (Enemy enemy : activeEnemies) {
            if (enemy.isAlive() && enemy != actor) {
                aliveEnemies.add(enemy);
            }
        }
        return aliveEnemies;  // Return the list of living enemies
    }

    // Method to increment the round number
    public void incrementRound() {
        roundNumber++;
    }

    // Method to end the battle and declare a winner
    public void endBattle(boolean playerWon) {
        this.battleOver = true;
    }

    // Method to check if all active enemies are defeated
    public boolean allActiveEnemiesDefeated() {
        return activeEnemies.stream().noneMatch(Combatant::isAlive);
    }

    // Method to remove defeated enemies from the active list
    public void removeDefeatedEnemies() {
        activeEnemies.removeIf(enemy -> !enemy.isAlive());  // Remove enemies that are defeated
    }

    // Getter methods for other fields if necessary
    public int getRoundNumber() {
        return roundNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Enemy> getAllEnemies() {
        return allEnemies;
    }

    public List<Enemy> getActiveEnemies() {
        return activeEnemies;
    }
}