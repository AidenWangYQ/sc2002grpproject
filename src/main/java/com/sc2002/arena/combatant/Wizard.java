package com.sc2002.arena.combatant;

import com.sc2002.arena.item.Inventory;

import com.sc2002.arena.skill.ArcaneBlastSkill;

public final class Wizard extends Player {
    public Wizard(Inventory inventory) {
        super("Wizard", 200, 50, 10, 20, inventory, new ArcaneBlastSkill());
    }
}
