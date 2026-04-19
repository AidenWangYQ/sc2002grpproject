package com.sc2002.arena.level;

import java.util.List;

import com.sc2002.arena.battleUI.BattleUI;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.engine.BattleState;

public class SpawnManager {
    private final Level level;
    private final BattleUI ui;

    public SpawnManager(Level level, BattleUI ui) {  //used to initialise the spawn manager with level and battleUI to spawn the different waves of enemies during different conditions
        this.level = level;
        this.ui = ui;
    }

    public void spawnInitialWave(BattleState context) { //to spawn the starting wave of enemies and inform the user about them.
        for (Enemy enemy : level.getInitialWave().getEnemies()) {
            context.addInitialEnemy(enemy);
        }
        ui.printMessage("Initial wave spawned: " + describeEnemies(level.getInitialWave().getEnemies()));
    }

    public boolean triggerBackupIfReady(BattleState context) { //to trigger the backup wave of enemies when conditions are met (all defeated)
        if (!hasBackupWave() || context.isBackupSpawned() || !context.allActiveEnemiesDefeated()) {
            return false;
        }

        List<Enemy> backup = level.getBackupWave().getEnemies();
        context.addBackupEnemies(backup);
        ui.printBackupSpawn(backup);
        return true;
    }

    public boolean hasBackupWave() { //to check if there is a backup wave of enemies for the current level
        return level.hasBackupWave();
    }

    private String describeEnemies(List<Enemy> enemies) { //to return string description of the enemies in the wave for user to see
        return enemies.stream().map(Enemy::getName).reduce((a, b) -> a + ", " + b).orElse("(none)");
    }
}
