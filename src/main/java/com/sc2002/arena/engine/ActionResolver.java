package com.sc2002.arena.engine;

import java.util.Objects;
import java.util.function.DoubleSupplier;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.effect.CriticalEffect;
import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.strategy.BattleContext;

/**
 * Provides low-level combat resolution utilities used by CombatAction implementations.
 *
 * ActionResolver handles:
 *   - Delegating action execution to the CombatAction itself (resolve)
 *   - Rolling for critical hits and applying the CriticalEffect (prepareAttack)
 *   - Computing and applying damage to a target (applyDamage)
 *   - Applying status effects to a target (applyStatus)
 *
 * The random source is injectable for testability — tests can inject a fixed
 * DoubleSupplier to guarantee or suppress critical hits without relying on
 * actual randomness.
 *
 * SOLID:
 *   SRP – sole responsibility: resolve action execution and compute damage.
 *   DIP – depends on CombatAction/StatusEffect interfaces, not concrete classes.
 *   OCP – new action types implement CombatAction and call these helpers;
 *         ActionResolver itself does not need to change.
 */

public class ActionResolver {
    private static final DoubleSupplier DEFAULT_RANDOM_SOURCE = Math::random;

    private static DoubleSupplier configuredRandomSource = DEFAULT_RANDOM_SOURCE;

    private final DoubleSupplier randomSource;

    public ActionResolver() {
        this(configuredRandomSource);
    }

    public ActionResolver(DoubleSupplier randomSource) {
        this.randomSource = Objects.requireNonNull(randomSource, "randomSource cannot be null");
    }

    public ActionResult resolve(CombatAction action, ActionContext context) {
        return action.execute(context);
    }

    public static void setConfiguredRandomSource(DoubleSupplier randomSource) {
        configuredRandomSource = Objects.requireNonNull(randomSource, "randomSource cannot be null");
    }

    public static void resetConfiguredRandomSource() {
        configuredRandomSource = DEFAULT_RANDOM_SOURCE;
    }

    public PreparedAttack prepareAttack(Combatant attacker, BattleContext context, ActionResult result) {
        boolean criticalTriggered = randomSource.getAsDouble() < attacker.getCriticalChance(context);
        if (criticalTriggered) {
            return prepareGuaranteedCriticalAttack(
                    attacker,
                    context,
                    result,
                    new CriticalEffect(),
                    "Critical x2.5",
                    "Critical hit triggered."
            );
        }
        return new PreparedAttack(attacker.getEffectiveAttack(context), criticalTriggered);
    }

    public PreparedAttack prepareGuaranteedCriticalAttack(
            Combatant attacker,
            BattleContext context,
            ActionResult result,
            StatusEffect criticalEffect,
            String effectName,
            String note
    ) {
        attacker.applyEffect(criticalEffect, context);
        if (result != null) {
            result.recordEffect(attacker, effectName);
            result.recordNote(note);
        }
        return new PreparedAttack(attacker.getEffectiveAttack(context), true);
    }

    public DamageResolution applyDamage(Combatant attacker, Combatant target, int attackValue, BattleContext context) {
        int rawDamage = Math.max(0, attackValue - target.getEffectiveDefense(context));
        int beforeHp = target.getCurrentHp();
        int appliedDamage = target.receiveDamage(rawDamage, attacker, context);
        int afterHp = target.getCurrentHp();
        return new DamageResolution(beforeHp, rawDamage, appliedDamage, afterHp);
    }

    public void applyStatus(StatusEffect effect, Combatant target, BattleContext context) {
        target.applyEffect(effect, context);
    }

    public record PreparedAttack(int attackValue, boolean criticalTriggered) {
    }

    public record DamageResolution(int beforeHp, int rawDamage, int appliedDamage, int afterHp) {
    }
}
