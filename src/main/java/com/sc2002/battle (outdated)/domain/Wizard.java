package com.sc2002.battle.domain;

public final class Wizard extends Player {
    public Wizard(Inventory inventory) {
        super("Wizard", 200, 50, 10, 20, inventory, new ArcaneBlastSkill());
    }
}
