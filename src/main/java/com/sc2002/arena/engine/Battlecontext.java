package com.sc2002.arena.engine;

import java.util.ArrayList;
import java.util.List;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;

public class BattleContext implements com.sc2002.arena.strategy.BattleContext {
    private final Player player;
    private final List<Enemy> allEnemies = new ArrayList<>();
    private final List<Enemy> activeEnemies = new ArrayList<>();

    private int roundNumber = 0;
    private boolean backupSpawned = false;

    public BattleContext(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
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
        if (player.isAlive()) {
            combatants.add(player);
        }
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
    public List<Combatant> getAliveEnemiesOf(Combatant actor) {
        validateActor(actor);
        if (actor == player) {
            return List.copyOf(getLivingEnemies());
        }
        return player.isAlive() ? List.of(player) : List.of();
    }

    @Override
    public List<Combatant> getAlliesOf(Combatant actor) {
        validateActor(actor);
        if (actor == player) {
            return player.isAlive() ? List.of(player) : List.of();
        }
        return activeEnemies.stream().filter(Combatant::isAlive).map(Combatant.class::cast).toList();
    }

    private void validateActor(Combatant actor) {
        if (actor == player) {
            return;
        }
        if (activeEnemies.contains(actor)) {
            return;
        }
        throw new IllegalArgumentException("Actor is not part of this battle context.");
    }
}
