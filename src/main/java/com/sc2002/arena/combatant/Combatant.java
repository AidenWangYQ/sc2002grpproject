package com.sc2002.arena.combatant;

import java.util.List;
import java.util.Objects;

import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.effect.StatusEffectManager;
import com.sc2002.arena.strategy.BattleContext;

/**
 * Base abstraction for every entity that can participate in battle.
 *
 * Responsibilities:
 * - store shared combat stats (HP, ATK, DEF, SPD)
 * - handle common lifecycle behaviour for battle turns/rounds
 * - delegate all status-effect related logic to StatusEffectManager
 *
 * Design notes:
 * - This class supports LSP: both Player and Enemy can be treated uniformly
 *   as Combatant by the battle engine and turn manager.
 * - Status effect behaviour is intentionally delegated rather than hardcoded
 *   here, keeping Combatant focused on shared combat state.
 */
public abstract class Combatant {
    /**
     * Default critical hit chance before any effects modify it.
     * Rage-like effects may increase this through StatusEffectManager.
     */
    private static final double BASE_CRITICAL_CHANCE = 0.10;

    /** Display name used in the UI and action results. */
    private final String name;

    /** Maximum HP cap for this combatant. */
    private final int maxHp;

    /** Base offensive stat before buffs/debuffs are applied. */
    private final int baseAttack;

    /** Base defensive stat before buffs/debuffs are applied. */
    private final int baseDefense;

    /** Speed determines turn order through TurnOrderStrategy. */
    private final int speed;

    /**
     * Dedicated manager for all active effects on this combatant.
     * This keeps effect storage/modification logic out of Combatant itself.
     */
    private final StatusEffectManager statusEffectManager;

    /** Current HP, clamped between 0 and maxHp. */
    private int currentHp;

    /**
     * Common constructor used by all Player and Enemy subclasses.
     *
     * Validation is done here so that all combatants obey the same rules.
     */
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

    /** @return combatant display name */
    public String getName() {
        return name;
    }

    /** @return maximum HP */
    public int getMaxHp() {
        return maxHp;
    }

    /** @return current HP */
    public int getCurrentHp() {
        return currentHp;
    }

    /** @return unmodified base attack */
    public int getBaseAttack() {
        return baseAttack;
    }

    /** @return unmodified base defense */
    public int getBaseDefense() {
        return baseDefense;
    }

    /** @return speed stat used for turn order */
    public int getSpeed() {
        return speed;
    }

    /**
     * Computes effective attack after applying all active effects.
     * Example: Arcane Blast attack buff or critical multipliers.
     */
    public int getEffectiveAttack(BattleContext context) {
        return statusEffectManager.modifyAttack(this, baseAttack, context);
    }

    /**
     * Computes effective defense after applying all active effects.
     * Example: DefendEffect increases defense temporarily.
     */
    public int getEffectiveDefense(BattleContext context) {
        return statusEffectManager.modifyDefense(this, baseDefense, context);
    }

    /**
     * Computes effective critical hit chance after applying effects.
     * Example: RageEffect increases critical chance.
     */
    public double getCriticalChance(BattleContext context) {
        return statusEffectManager.modifyCriticalChance(this, BASE_CRITICAL_CHANCE, context);
    }

    /** @return true if HP is above 0 */
    public boolean isAlive() {
        return currentHp > 0;
    }

    /**
     * A combatant can act only if it is alive and not blocked by an effect
     * such as stun.
     */
    public boolean canAct() {
        return isAlive() && !statusEffectManager.preventsAction();
    }

    /**
     * Restore HP, capped at maxHp.
     *
     * @param amount requested heal amount
     * @return actual HP restored (useful for ActionResult logging)
     */
    public int heal(int amount) {
        validateNonNegative(amount, "amount");
        int previousHp = currentHp;
        currentHp = Math.min(maxHp, currentHp + amount);
        return currentHp - previousHp;
    }

    /**
     * Apply incoming damage after status effects modify it.
     * Example: Smoke Bomb invulnerability may reduce enemy damage to 0.
     *
     * @param rawDamage pre-effect damage
     * @return actual HP lost
     */
    public int receiveDamage(int rawDamage, Combatant attacker, BattleContext context) {
        validateNonNegative(rawDamage, "rawDamage");
        int adjustedDamage = statusEffectManager.modifyIncomingDamage(this, attacker, rawDamage, context);
        int previousHp = currentHp;
        currentHp = Math.max(0, currentHp - adjustedDamage);
        return previousHp - currentHp;
    }

    /**
     * Add a new status effect to this combatant.
     * The effect manager owns the lifecycle after this point.
     */
    public void applyEffect(StatusEffect effect, BattleContext context) {
        statusEffectManager.addEffect(effect, this, context);
    }

    /** @return immutable snapshot of currently active effects */
    public List<StatusEffect> getActiveEffects() {
        return statusEffectManager.getActiveEffects();
    }

    /** Clears all active effects. Useful for reset or cleanup scenarios. */
    public void clearStatusEffects() {
        statusEffectManager.clear();
    }

    /** Trigger all effect hooks that should happen at the start of a turn. */
    public void onTurnStart(BattleContext context) {
        statusEffectManager.onTurnStart(this, context);
    }

    /** Trigger all effect hooks that should happen at the end of a turn. */
    public void onTurnEnd(BattleContext context) {
        statusEffectManager.onTurnEnd(this, context);
    }

    /** Trigger all effect hooks that should happen at the end of a round. */
    public void onRoundEnd(BattleContext context) {
        statusEffectManager.onRoundEnd(this, context);
    }

    /** Remove expired effects after lifecycle updates are applied. */
    public void removeExpiredEffects() {
        statusEffectManager.removeExpiredEffects();
    }

    /** Shared validation helper for constructor/stat updates. */
    private void validateNonNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }
}