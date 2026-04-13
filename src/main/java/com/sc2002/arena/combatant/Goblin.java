package com.sc2002.arena.combatant;

import com.sc2002.arena.strategy.BasicAttackEnemyActionStrategy;

public final class Goblin extends Enemy {
    public Goblin() {
        this("Goblin");
    }

    public Goblin(String name) {
        super(name, 55, 35, 15, 25, new BasicAttackEnemyActionStrategy());
    }
}
