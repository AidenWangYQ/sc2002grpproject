package com.sc2002.arena.level;

import com.sc2002.arena.model.combatant.Enemy;
import java.util.List;
 
/**
 * Represents one wave of enemies (initial or backup).
 *
 * SRP – holds only the enemy list for one spawn event.
 */

public class Wave {
 
    private final List<Enemy> enemies;
 
    public Wave(List<Enemy> enemies) {
        this.enemies = List.copyOf(enemies);
    }
 
    public List<Enemy> getEnemies() {
        return enemies;
    }
 
    public boolean isEmpty() {
        return enemies.isEmpty();
    }
}
 