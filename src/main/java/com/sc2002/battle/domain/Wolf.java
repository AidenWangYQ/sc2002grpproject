package com.sc2002.battle.domain;

public final class Wolf extends Enemy {
    public Wolf() {
        this("Wolf");
    }

    public Wolf(String name) {
        super(name, 40, 45, 5, 35, new BasicAttackEnemyActionStrategy());
    }
}
