package com.sc2002.arena.effect;

import java.util.ArrayList;
import java.util.List;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

/**
 * Manages all active status effects on a combatant.
 *
 * Responsibilities:
 * - store and track active status effects
 * - apply effect modifications to combat stats
 * - trigger lifecycle hooks (turn/round events)
 * - remove expired effects
 *
 * Design notes:
 * - Uses a centralized manager to avoid effect-handling logic inside Combatant
 * - Iterates over a defensive copy to prevent concurrent modification issues
 * - Chains multiple effects in sequence for cumulative stat modification
 */
public final class StatusEffectManager {

    /** List of currently active status effects. */
    private final List<StatusEffect> activeEffects = new ArrayList<>();

    /**
     * Adds a new effect and triggers its apply hook.
     */
    public void addEffect(StatusEffect effect, Combatant target, BattleContext context) {
        activeEffects.add(effect);
        effect.onApply(target, context);
        removeExpiredEffects();
    }

    /** @return immutable list of active effects */
    public List<StatusEffect> getActiveEffects() {
        return List.copyOf(activeEffects);
    }

    /**
     * Applies all effects to incoming damage.
     */
    public int modifyIncomingDamage(Combatant target, Combatant attacker, int damage, BattleContext context) {
        int modifiedDamage = damage;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedDamage = effect.modifyIncomingDamage(target, attacker, modifiedDamage, context);
        }
        return Math.max(0, modifiedDamage);
    }

    /**
     * Applies all effects to critical chance.
     */
    public double modifyCriticalChance(Combatant target, double criticalChance, BattleContext context) {
        double modifiedCriticalChance = criticalChance;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedCriticalChance = effect.modifyCriticalChance(target, modifiedCriticalChance, context);
        }
        return Math.max(0.0, Math.min(1.0, modifiedCriticalChance));
    }

    /**
     * Applies all effects to attack value.
     */
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        int modifiedAttack = attack;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedAttack = effect.modifyAttack(target, modifiedAttack, context);
        }
        return modifiedAttack;
    }

    /**
     * Applies all effects to defense value.
     */
    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        int modifiedDefense = defense;
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            modifiedDefense = effect.modifyDefense(target, modifiedDefense, context);
        }
        return modifiedDefense;
    }

    /**
     * Checks if any effect prevents action execution.
     */
    public boolean preventsAction() {
        for (StatusEffect effect : activeEffects) {
            if (effect.preventsAction()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers turn start hooks for all effects.
     */
    public void onTurnStart(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onTurnStart(target, context);
        }
        removeExpiredEffects();
    }

    /**
     * Triggers turn end hooks for all effects.
     */
    public void onTurnEnd(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onTurnEnd(target, context);
        }
        removeExpiredEffects();
    }

    /**
     * Triggers round end hooks for all effects.
     */
    public void onRoundEnd(Combatant target, BattleContext context) {
        for (StatusEffect effect : List.copyOf(activeEffects)) {
            effect.onRoundEnd(target, context);
        }
        removeExpiredEffects();
    }

    /** Clears all active effects. */
    public void clear() {
        activeEffects.clear();
    }

    /** Removes effects that have expired. */
    public void removeExpiredEffects() {
        activeEffects.removeIf(StatusEffect::isExpired);
    }
}