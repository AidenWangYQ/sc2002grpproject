package com.sc2002.arena.engine;

import java.util.ArrayList;
import java.util.List;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;

/**
 * Holds the complete mutable state of a running battle.
 *
 * BattleEngine reads and writes through this object rather than holding
 * state fields directly, keeping BattleEngine focused on orchestration (SRP).
 *
 * Implements BattleContext so that skills and enemy strategies can query
 * living combatants without gaining access to the mutation methods
 * (addInitialEnemy, incrementRound, etc.) — those are only visible on
 * the concrete BattleState type used by BattleEngine and SpawnManager.
 *
 * SOLID:
 *   SRP – sole responsibility is holding and exposing battle state.
 *   ISP – exposes only getLivingOpponentsOf/getLivingAlliesOf/getRoundNumber
 *          through the BattleContext interface; mutation methods stay hidden
 *          from skills and strategies that only need to read state.
 */

public class BattleState implements com.sc2002.arena.strategy.BattleContext {
    private final List<Player> players = new ArrayList<>();
    private final List<Enemy> allEnemies = new ArrayList<>();
    private final List<Enemy> activeEnemies = new ArrayList<>();

    private int roundNumber = 0;
    private boolean backupSpawned = false;

    public BattleState(Player player) {
        players.add(player);
    }

    public Player getPlayer() {
        return players.getFirst();
    }

    public List<Player> getLivingPlayers() {
        return players.stream().filter(Combatant::isAlive).toList();
    }

    public void addInitialEnemy(Enemy enemy) {
        allEnemies.add(enemy);
        activeEnemies.add(enemy);
    }

    public void addBackupEnemies(List<Enemy> backup) {
        allEnemies.addAll(backup);
        activeEnemies.addAll(backup);
        backupSpawned = true;
    }

    public List<Enemy> getLivingEnemies() {
        return activeEnemies.stream().filter(Combatant::isAlive).toList();
    }

    public List<Combatant> getLivingCombatants() {
        List<Combatant> combatants = new ArrayList<>();
        combatants.addAll(getLivingPlayers());
        combatants.addAll(getLivingEnemies());
        return List.copyOf(combatants);
    }

    public boolean allActiveEnemiesDefeated() {
        return activeEnemies.stream().noneMatch(Combatant::isAlive);
    }

    public void incrementRound() {
        roundNumber++;
    }

    @Override
    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isBackupSpawned() {
        return backupSpawned;
    }

    public int getTotalEnemiesDefeated() {
        return (int) allEnemies.stream().filter(enemy -> !enemy.isAlive()).count();
    }

    public int getRemainingEnemyCount() {
        return (int) allEnemies.stream().filter(Combatant::isAlive).count();
    }

    @Override
    public List<Combatant> getLivingOpponentsOf(Combatant actor) {
        validateActor(actor);
        if (players.contains(actor)) {
            return List.copyOf(getLivingEnemies());
        }
        return getLivingPlayers().stream().map(Combatant.class::cast).toList();
    }

    @Override
    public List<Combatant> getLivingAlliesOf(Combatant actor) {
        validateActor(actor);
        if (players.contains(actor)) {
            return getLivingPlayers().stream().map(Combatant.class::cast).toList();
        }
        return activeEnemies.stream().filter(Combatant::isAlive).map(Combatant.class::cast).toList();
    }

    private void validateActor(Combatant actor) {
        if (players.contains(actor)) {
            return;
        }
        if (activeEnemies.contains(actor)) {
            return;
        }
        throw new IllegalArgumentException("Actor is not part of this battle context.");
    }
}
