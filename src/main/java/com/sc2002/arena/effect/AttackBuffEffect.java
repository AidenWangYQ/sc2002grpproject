package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

public final class AttackBuffEffect extends StatusEffect {
    private final int attackBonus;

    public AttackBuffEffect(int attackBonus) {
        super("Attack Buff");
        if (attackBonus <= 0) {
            throw new IllegalArgumentException("attackBonus must be greater than 0.");
        }
        this.attackBonus = attackBonus;
    }

    @Override
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return attack + attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
}
