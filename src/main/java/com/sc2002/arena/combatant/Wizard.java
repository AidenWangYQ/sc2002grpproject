package com.sc2002.arena.combatant;

import com.sc2002.arena.item.*;
import com.sc2002.arena.skill.*;

public final class Wizard extends Player {
    public Wizard(Inventory inventory) {
        super("Wizard", 200, 50, 10, 20, inventory, new ArcaneBlastSkill());
    }
}
