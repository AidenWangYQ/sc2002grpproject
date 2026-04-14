package com.sc2002.arena.engine;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import java.util.ArrayList;
import java.util.List;

 
/**
 * Immutable-ish snapshot of the current battle state.
 *
 * BattleEngine reads and writes through this object rather than holding
 * state fields directly. This means BattleEngine has a single reason to
 * change (orchestration logic), while state shape changes live here (SRP).
 *
 * Also makes future features (save/load, replay) straightforward since
 * all battle state is in one place.
 */
public class BattleContext {
 
    private final Player player;
    // All enemies ever spawned this level (for end-of-game stats)
    private final List<Enemy> allEnemies = new ArrayList<>();
 
    // Currently active enemies (initial wave + any backup spawned so far)
    private final List<Enemy> activeEnemies = new ArrayList<>();
 
    private int roundNumber = 0;
    private boolean backupSpawned = false;
    private boolean battleOver = false;
    private boolean playerWon = false;
 
    public BattleContext(Player player) {
        this.player = player;
    }
 
    public void addInitialEnemy(Enemy e) {
        allEnemies.add(e);
        activeEnemies.add(e);
    }
 
    public void addBackupEnemies(List<Enemy> backup) {
        allEnemies.addAll(backup);
        activeEnemies.addAll(backup);
        backupSpawned = true;
    }

    public Player getPlayer() { return player; }
 
    /** All enemies currently alive and in the fight. */
    public List<Enemy> getLivingEnemies() {
        return activeEnemies.stream()
                .filter(Combatant::isAlive)
                .toList();
    }
 
    /** All combatants (player + active enemies) who are still alive. */
    public List<Combatant> getLivingCombatants() {
        List<Combatant> all = new ArrayList<>();
        if (player.isAlive()) all.add(player);
        all.addAll(getLivingEnemies());
        return all;
    }
 
    /** True when every enemy in the active roster is dead. */
    public boolean allActiveEnemiesDefeated() {
        return activeEnemies.stream().noneMatch(Combatant::isAlive);
    }
 
    public void incrementRound() { roundNumber++; }
    public int getRoundNumber()  { return roundNumber; }
 
    public boolean isBackupSpawned() { return backupSpawned; }
 
    public boolean isBattleOver()  { return battleOver; }
    public boolean didPlayerWin()  { return playerWon; }
 
    public void endBattle(boolean playerWon) {
        this.battleOver = true;
        this.playerWon  = playerWon;
    }
 
    // ── Stats for end screen ──────────────────────────────────────────────
 
    public int getTotalEnemiesDefeated() {
        return (int) allEnemies.stream().filter(e -> !e.isAlive()).count();
    }
 
    public int getRemainingEnemyCount() {
        return (int) allEnemies.stream().filter(Combatant::isAlive).count();
    }
}
 