package com.sc2002.arena.common;

import com.sc2002.arena.combatant.Combatant;

public final class DamageCalculator {

    private DamageCalculator() {
        // Prevent instantiation
    }

    public static int calculateBasicDamage(Combatant attacker, Combatant target) {
        return Math.max(0, attacker.getAttack() - target.getDefense());
    }

    public static int calculateDamage(int attackValue, int defenseValue) {
        return Math.max(0, attackValue - defenseValue);
    }
}