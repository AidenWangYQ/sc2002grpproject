package com.sc2002.arena.combatant;

import java.util.List;
import java.util.Objects;

import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.effect.StatusEffectManager;

import com.sc2002.arena.strategy.BattleContext;

public abstract class Combatant {
    private static final double BASE_CRITICAL_CHANCE = 0.10;

    private final String name;
    private final int maxHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int speed;
    private final StatusEffectManager statusEffectManager;
    private int currentHp;

    protected Combatant(String name, int maxHp, int baseAttack, int baseDefense, int speed) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        validateNonNegative(maxHp, "maxHp");
        validateNonNegative(baseAttack, "baseAttack");
        validateNonNegative(baseDefense, "baseDefense");
        validateNonNegative(speed, "speed");
        if (maxHp == 0) {
            throw new IllegalArgumentException("maxHp must be greater than 0.");
        }
        this.maxHp = maxHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.speed = speed;
        this.currentHp = maxHp;
        this.statusEffectManager = new StatusEffectManager();
    }

    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getSpeed() {
        return speed;
    }

    public int getEffectiveAttack(BattleContext context) {
        return statusEffectManager.modifyAttack(this, baseAttack, context);
    }

    public int getEffectiveDefense(BattleContext context) {
        return statusEffectManager.modifyDefense(this, baseDefense, context);
    }

    public double getCriticalChance(BattleContext context) {
        return statusEffectManager.modifyCriticalChance(this, BASE_CRITICAL_CHANCE, context);
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public boolean canAct() {
        return isAlive() && !statusEffectManager.preventsAction();
    }

    public int heal(int amount) {
        validateNonNegative(amount, "amount");
        int previousHp = currentHp;
        currentHp = Math.min(maxHp, currentHp + amount);
        return currentHp - previousHp;
    }

    public int receiveDamage(int rawDamage, Combatant attacker, BattleContext context) {
        validateNonNegative(rawDamage, "rawDamage");
        int adjustedDamage = statusEffectManager.modifyIncomingDamage(this, attacker, rawDamage, context);
        int previousHp = currentHp;
        currentHp = Math.max(0, currentHp - adjustedDamage);
        return previousHp - currentHp;
    }

    public void applyEffect(StatusEffect effect, BattleContext context) {
        statusEffectManager.addEffect(effect, this, context);
    }

    public List<StatusEffect> getActiveEffects() {
        return statusEffectManager.getActiveEffects();
    }

    public void clearStatusEffects() {
        statusEffectManager.clear();
    }

    public void onTurnStart(BattleContext context) {
        statusEffectManager.onTurnStart(this, context);
    }

    public void onTurnEnd(BattleContext context) {
        statusEffectManager.onTurnEnd(this, context);
    }

    public void onRoundEnd(BattleContext context) {
        statusEffectManager.onRoundEnd(this, context);
    }

    public void removeExpiredEffects() {
        statusEffectManager.removeExpiredEffects();
    }

    private void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }
}
