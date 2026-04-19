package com.sc2002.arena.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sc2002.arena.combatant.Combatant;

/**
 * Represents the outcome of a single action execution.
 *
 * Responsibilities:
 * - capture all events produced during an action (damage, healing, effects, defeats)
 * - provide structured data for UI rendering and logging
 * - support aggregation of multiple sub-results into a single result
 *
 * Design notes:
 * - This class follows a "result aggregation" pattern, where all side effects
 *   of an action are recorded rather than executed directly in the UI layer.
 * - Immutable views (List.copyOf) are exposed to prevent external mutation.
 * - Event records (DamageEvent, HealEvent, etc.) provide a structured and
 *   extensible way to represent different action outcomes.
 */
public final class ActionResult {

    /** The combatant who performed the action. */
    private final Combatant actor;

    /** The name of the action executed (used for display/logging). */
    private final String actionName;

    /** All damage-related events produced during the action. */
    private final List<DamageEvent> damageEvents = new ArrayList<>();

    /** All healing-related events produced during the action. */
    private final List<HealEvent> healEvents = new ArrayList<>();

    /** All status effect applications during the action. */
    private final List<EffectEvent> effectEvents = new ArrayList<>();

    /** Records of combatants defeated as a result of the action. */
    private final List<DefeatEvent> defeatEvents = new ArrayList<>();

    /** Additional textual notes for UI/logging (e.g., critical hits, misses). */
    private final List<String> notes = new ArrayList<>();

    /** Item consumed during the action, if applicable. */
    private ConsumedItem consumedItem;

    /** Cooldown change resulting from the action, if applicable. */
    private CooldownChange cooldownChange;

    /**
     * Internal constructor.
     * Use factory method for clarity and consistency.
     */
    private ActionResult(Combatant actor, String actionName) {
        this.actor = Objects.requireNonNull(actor, "actor cannot be null");
        this.actionName = Objects.requireNonNull(actionName, "actionName cannot be null");
    }

    /**
     * Creates a new result container for a specific action.
     */
    public static ActionResult forAction(Combatant actor, String actionName) {
        return new ActionResult(actor, actionName);
    }

    /** @return the acting combatant */
    public Combatant getActor() {
        return actor;
    }

    /** @return name of the executed action */
    public String getActionName() {
        return actionName;
    }

    /** @return immutable list of all damage events */
    public List<DamageEvent> getDamageEvents() {
        return List.copyOf(damageEvents);
    }

    /** @return immutable list of all healing events */
    public List<HealEvent> getHealEvents() {
        return List.copyOf(healEvents);
    }

    /** @return immutable list of all effect application events */
    public List<EffectEvent> getEffectEvents() {
        return List.copyOf(effectEvents);
    }

    /** @return immutable list of all defeat events */
    public List<DefeatEvent> getDefeatEvents() {
        return List.copyOf(defeatEvents);
    }

    /** @return immutable list of additional notes */
    public List<String> getNotes() {
        return List.copyOf(notes);
    }

    /** @return consumed item information, or null if none */
    public ConsumedItem getConsumedItem() {
        return consumedItem;
    }

    /** @return cooldown change information, or null if none */
    public CooldownChange getCooldownChange() {
        return cooldownChange;
    }

    /**
     * Records a damage event.
     *
     * @param attacker the source of damage
     * @param target the recipient of damage
     * @param beforeHp HP before damage is applied
     * @param calculatedDamage damage before mitigation/effects
     * @param appliedDamage final damage applied
     * @param afterHp HP after damage is applied
     */
    public void recordDamage(
            Combatant attacker,
            Combatant target,
            int beforeHp,
            int calculatedDamage,
            int appliedDamage,
            int afterHp
    ) {
        damageEvents.add(new DamageEvent(attacker, target, beforeHp, calculatedDamage, appliedDamage, afterHp));
    }

    /**
     * Records a healing event.
     *
     * @param target the combatant healed
     * @param beforeHp HP before healing
     * @param amount amount healed
     * @param afterHp HP after healing
     */
    public void recordHeal(Combatant target, int beforeHp, int amount, int afterHp) {
        healEvents.add(new HealEvent(target, beforeHp, amount, afterHp));
    }

    /**
     * Records a status effect application.
     *
     * @param target affected combatant
     * @param effectName name of the applied effect
     */
    public void recordEffect(Combatant target, String effectName) {
        effectEvents.add(new EffectEvent(target, effectName));
    }

    /**
     * Records a defeat event when a combatant reaches 0 HP.
     */
    public void recordDefeat(Combatant target) {
        defeatEvents.add(new DefeatEvent(target));
    }

    /**
     * Adds a descriptive note for UI/logging purposes.
     * Example: "Critical hit!", "Attack missed!"
     */
    public void recordNote(String note) {
        notes.add(Objects.requireNonNull(note, "note cannot be null"));
    }

    /**
     * Records item consumption during the action.
     */
    public void recordConsumedItem(String itemName, int slotIndex) {
        consumedItem = new ConsumedItem(itemName, slotIndex);
    }

    /**
     * Records cooldown change caused by the action.
     */
    public void recordCooldownChange(int before, int after) {
        cooldownChange = new CooldownChange(before, after);
    }

    /**
     * Merges another ActionResult into this one.
     *
     * Used when actions trigger additional sub-actions (e.g., multi-hit skills,
     * chained effects), allowing all outcomes to be aggregated into a single result.
     */
    public void merge(ActionResult other) {
        Objects.requireNonNull(other, "other cannot be null");
        damageEvents.addAll(other.damageEvents);
        healEvents.addAll(other.healEvents);
        effectEvents.addAll(other.effectEvents);
        defeatEvents.addAll(other.defeatEvents);
        notes.addAll(other.notes);
        if (other.consumedItem != null) {
            consumedItem = other.consumedItem;
        }
        if (other.cooldownChange != null) {
            cooldownChange = other.cooldownChange;
        }
    }

    /**
     * Represents a single damage instance applied during an action.
     */
    public record DamageEvent(
            Combatant attacker,
            Combatant target,
            int beforeHp,
            int calculatedDamage,
            int appliedDamage,
            int afterHp
    ) {
    }

    /**
     * Represents a healing instance applied during an action.
     */
    public record HealEvent(Combatant target, int beforeHp, int healedAmount, int afterHp) {
    }

    /**
     * Represents a status effect application event.
     */
    public record EffectEvent(Combatant target, String effectName) {
    }

    /**
     * Represents a combatant being defeated.
     */
    public record DefeatEvent(Combatant target) {
    }

    /**
     * Represents an item consumed during the action.
     */
    public record ConsumedItem(String itemName, int slotIndex) {
    }

    /**
     * Represents a cooldown change caused by an action.
     */
    public record CooldownChange(int before, int after) {
    }
}