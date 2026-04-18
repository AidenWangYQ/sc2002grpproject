package com.sc2002.arena.engine;

import com.sc2002.arena.combatant.Combatant;

public class CoolDownManager {
    public void tickCooldown(Combatant combatant) {
        combatant.onTurnEnd(null);
    }
}
