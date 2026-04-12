package com.sc2002.battle.domain;
import sc2002.battle.domain.Combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import sc2002.battle.domain.DamageEvent;
import sc2002.battle.domain.HealEvent;
import sc2002.battle.domain.EffectEvent;
import sc2002.battle.domain.DefeatEvent;
import sc2002.battle.domain.ConsumedItem;
import sc2002.battle.domain.CooldownChange;

public final class ActionResult {
    private final Combatant actor;
    private final String actionName;
    private final List<DamageEvent> damageEvents = new ArrayList<>();
    private final List<HealEvent> healEvents = new ArrayList<>();
    private final List<EffectEvent> effectEvents = new ArrayList<>();
    private final List<DefeatEvent> defeatEvents = new ArrayList<>();
    private final List<String> notes = new ArrayList<>();
    private ConsumedItem consumedItem;
    private CooldownChange cooldownChange;

    private ActionResult(Combatant actor, String actionName) {
        this.actor = Objects.requireNonNull(actor, "actor cannot be null");
        this.actionName = Objects.requireNonNull(actionName, "actionName cannot be null");
    }

    public static ActionResult forAction(Combatant actor, String actionName) {
        return new ActionResult(actor, actionName);
    }

    public Combatant getActor() {
        return actor;
    }

    public String getActionName() {
        return actionName;
    }

    public List<DamageEvent> getDamageEvents() {
        return List.copyOf(damageEvents);
    }

    public List<HealEvent> getHealEvents() {
        return List.copyOf(healEvents);
    }

    public List<EffectEvent> getEffectEvents() {
        return List.copyOf(effectEvents);
    }

    public List<DefeatEvent> getDefeatEvents() {
        return List.copyOf(defeatEvents);
    }

    public List<String> getNotes() {
        return List.copyOf(notes);
    }

    public ConsumedItem getConsumedItem() {
        return consumedItem;
    }

    public CooldownChange getCooldownChange() {
        return cooldownChange;
    }

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

    public void recordHeal(Combatant target, int beforeHp, int amount, int afterHp) {
        healEvents.add(new HealEvent(target, beforeHp, amount, afterHp));
    }

    public void recordEffect(Combatant target, String effectName) {
        effectEvents.add(new EffectEvent(target, effectName));
    }

    public void recordDefeat(Combatant target) {
        defeatEvents.add(new DefeatEvent(target));
    }

    public void recordNote(String note) {
        notes.add(Objects.requireNonNull(note, "note cannot be null"));
    }

    public void recordConsumedItem(String itemName, int slotIndex) {
        consumedItem = new ConsumedItem(itemName, slotIndex);
    }

    public void recordCooldownChange(int before, int after) {
        cooldownChange = new CooldownChange(before, after);
    }

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

    public record DamageEvent(
            Combatant attacker,
            Combatant target,
            int beforeHp,
            int calculatedDamage,
            int appliedDamage,
            int afterHp
    ) {
    }

    public record HealEvent(Combatant target, int beforeHp, int healedAmount, int afterHp) {
    }

    public record EffectEvent(Combatant target, String effectName) {
    }

    public record DefeatEvent(Combatant target) {
    }

    public record ConsumedItem(String itemName, int slotIndex) {
    }

    public record CooldownChange(int before, int after) {
    }
}
