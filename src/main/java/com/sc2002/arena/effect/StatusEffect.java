package com.sc2002.arena.effect;

import java.util.*;
import com.sc2002.arena.combatant.*;
import com.sc2002.arena.strategy.BattleContext;

public abstract class StatusEffect {
    private final String name;

    protected StatusEffect(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public String getName() {
        return name;
    }

    public void onApply(Combatant target, BattleContext context) {
    }

    public void onTurnStart(Combatant target, BattleContext context) {
    }

    public void onTurnEnd(Combatant target, BattleContext context) {
    }

    public void onRoundEnd(Combatant target, BattleContext context) {
    }

    public int modifyIncomingDamage(Combatant target, Combatant attacker, int damage, BattleContext context) {
        return damage;
    }

    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return attack;
    }

    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense;
    }

    public boolean preventsAction() {
        return false;
    }

    public abstract boolean isExpired();
}
