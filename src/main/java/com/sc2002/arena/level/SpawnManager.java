package com.sc2002.arena.level;

import java.util.List;

import com.sc2002.arena.battleUI.BattleUI;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.engine.BattleState;

public class SpawnManager {
    private final Level level;
    private final BattleUI ui;

    public SpawnManager(Level level, BattleUI ui) {
        this.level = level;
        this.ui = ui;
    }

    public void spawnInitialWave(BattleState context) {
        for (Enemy enemy : level.getInitialWave().getEnemies()) {
            context.addInitialEnemy(enemy);
        }
        ui.printMessage("Initial wave spawned: " + describeEnemies(level.getInitialWave().getEnemies()));
    }

    public boolean triggerBackupIfReady(BattleState context) {
        if (!hasBackupWave() || context.isBackupSpawned() || !context.allActiveEnemiesDefeated()) {
            return false;
        }

        List<Enemy> backup = level.getBackupWave().getEnemies();
        context.addBackupEnemies(backup);
        ui.printBackupSpawn(backup);
        return true;
    }

    public boolean hasBackupWave() {
        return level.hasBackupWave();
    }

    private String describeEnemies(List<Enemy> enemies) {
        return enemies.stream().map(Enemy::getName).reduce((a, b) -> a + ", " + b).orElse("(none)");
    }
}
