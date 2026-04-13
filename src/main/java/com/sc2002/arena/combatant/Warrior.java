package com.sc2002.arena.combatant;

import com.sc2002.arena.item.*;
import com.sc2002.arena.skill.*;

public final class Warrior extends Player {
    public Warrior(Inventory inventory) {
        super("Warrior", 260, 40, 20, 30, inventory, new ShieldBashSkill());
    }
}
