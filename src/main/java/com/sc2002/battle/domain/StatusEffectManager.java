package com.sc2002.battle.domain;

import java.util.ArrayList;
import java.util.List;

public final class StatusEffectManager {
    private final List<StatusEffect> activeEffects = new ArrayList<>();

    public void addEffect(StatusEffect effect, Combatant target, BattleContext context) {
        activeEffects.add(effect);
        effect.onApply(target, context);
        removeExpiredEffects();
    }

    public List<StatusEffect> getActiveEffects() {
        return List.copyOf(activeEffects);
    }

    public int modifyIncomingDamage(Combatant target, Combatant attacker, int damage, BattleContext context) {
        int modifiedDamage = damage;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedDamage = effect.modifyIncomingDamage(target, attacker, modifiedDamage, context);
        }
        return Math.max(0, modifiedDamage);
    }

    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        int modifiedAttack = attack;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedAttack = effect.modifyAttack(target, modifiedAttack, context);
        }
        return modifiedAttack;
    }

    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        int modifiedDefense = defense;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedDefense = effect.modifyDefense(target, modifiedDefense, context);
        }
        return modifiedDefense;
    }

    public boolean preventsAction() {
        for (StatusEffect effect : activeEffects) {
            if (effect.preventsAction()) {
                return true;
            }
        }
        return false;
    }

    public void onTurnStart(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onTurnStart(target, context);
        }
        removeExpiredEffects();
    }

    public void onTurnEnd(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onTurnEnd(target, context);
        }
        removeExpiredEffects();
    }

    public void onRoundEnd(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onRoundEnd(target, context);
        }
        removeExpiredEffects();
    }

    public void clear() {
        activeEffects.clear();
    }

    public void removeExpiredEffects() {
        activeEffects.removeIf(StatusEffect::isExpired);
    }
}
