package com.sc2002.arena.effect;

import java.util.Objects;

import com.sc2002.arena.combatant.Combatant;

import com.sc2002.arena.strategy.BattleContext;

/**
 * Base class for all status effects in the combat system.
 *
 * Responsibilities:
 * - define lifecycle hooks for different battle phases
 * - provide default behaviour for stat modifications
 * - enforce a common interface for all concrete effects
 *
 * Design notes:
 * - Uses method overriding to allow selective effect behaviour per subclass
 * - Default implementations are no-op or identity functions to ensure safety
 *   when a subclass does not override a method
 */
public abstract class StatusEffect {

    /** Effect name used for UI and debugging. */
    private final String name;

    /**
     * Constructs a status effect with a given name.
     *
     * @param name effect identifier
     */
    protected StatusEffect(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    /** @return effect name */
    public String getName() {
        return name;
    }

    /** Called when the effect is applied to a combatant */
    public void onApply(Combatant target, BattleContext context) {}

    /** Called at the start of a turn */
    public void onTurnStart(Combatant target, BattleContext context) {}

    /** Called at the end of a turn */
    public void onTurnEnd(Combatant target, BattleContext context) {}

    /** Called at the end of a round */
    public void onRoundEnd(Combatant target, BattleContext context) {}

    /** @return modified critical chance (default: unchanged) */
    public double modifyCriticalChance(Combatant target, double criticalChance, BattleContext context) {
        return criticalChance;
    }

    /** @return modified incoming damage (default: unchanged) */
    public int modifyIncomingDamage(Combatant target, Combatant attacker, int damage, BattleContext context) {
        return damage;
    }

    /** @return modified attack value (default: unchanged) */
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return attack;
    }

    /** @return modified defense value (default: unchanged) */
    public int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense;
    }

    /** @return true if this effect prevents the combatant from acting */
    public boolean preventsAction() {
        return false;
    }

    /** @return true if the effect should be removed */
    public abstract boolean isExpired();
}