package com.sc2002.arena.level;

import com.sc2002.arena.engine.BattleContext;
import com.sc2002.arena.model.combatant.Enemy;
import com.sc2002.arena.ui.BattleUI;
import sc2002.battle.domain.Enemy;
import com.sc2002.arena.ui.BattleUI;
import com.sc2002.arena.level.Level;
import java.util.List;
 
/**
 * Manages enemy wave spawning: initial wave at battle start, and backup
 * wave triggered once the initial wave is completely defeated.
 *
 * SRP  – sole responsibility: decide when to spawn and register enemies
 *         into BattleContext. No combat logic lives here.
 * OCP  – a three-wave level could be supported by adding a thirdWave field
 *         to Level and a third spawn check here, without touching BattleEngine.
 */

public class SpawnManager {
 
    private final Level level;
    private final BattleUI ui;
 
    public SpawnManager(Level level, BattleUI ui) {
        this.level = level;
        this.ui    = ui;
    }
 
    public void spawnInitialWave(BattleContext context) {
        for (Enemy e : level.getInitialWave().getEnemies()) {
            context.addInitialEnemy(e);
        }
        ui.printMessage("  Initial wave spawned: " +
                describeEnemies(level.getInitialWave().getEnemies()));
    }

    public boolean triggerBackupIfReady(BattleContext context) {
        if (context.isBackupSpawned()) return false;
        if (!level.hasBackupWave())    return false;
        if (!context.allActiveEnemiesDefeated()) return false;
 
        // All conditions met — spawn backup
        List<Enemy> backup = level.getBackupWave().getEnemies();
        context.addBackupEnemies(backup);
        ui.printBackupSpawn(backup);
        return true;
    }

    private String describeEnemies(List<Enemy> enemies) {
        return enemies.stream()
                .map(Enemy::getName)
                .reduce((a, b) -> a + ", " + b)
                .orElse("(none)");
    }
}