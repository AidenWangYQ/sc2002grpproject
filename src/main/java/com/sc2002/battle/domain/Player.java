package com.sc2002.battle.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Player extends Combatant {
    public static final int SKILL_COOLDOWN = 3;
    private final Inventory inventory;
    private final SpecialSkill specialSkill;
    private final List<StatusEffect> activeEffects = new ArrayList<>();  // Active status effects
    private boolean smokeBombActive = false;
    private int skillCooldown;

    protected Player(
            String name,
            int maxHp,
            int baseAttack,
            int baseDefense,
            int speed,
            Inventory inventory,
            SpecialSkill specialSkill
    ) {
        super(name, maxHp, baseAttack, baseDefense, speed);
        this.inventory = Objects.requireNonNull(inventory, "inventory cannot be null");
        this.specialSkill = Objects.requireNonNull(specialSkill, "specialSkill cannot be null");
    }

    public Inventory getInventory() {
        return inventory;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    public String getSpecialSkillName() {
        return specialSkill.getName();  // Return the name of the special skill
    }

    public void activateSmokeBomb() {
        this.smokeBombActive = true;  // Activate SmokeBomb effect
    }

    public void deactivateSmokeBomb() {
        this.smokeBombActive = false;  // Deactivate SmokeBomb effect
    }

     public boolean isSmokeBombActive() {
        return smokeBombActive;  // Return whether SmokeBomb is active
    }

    public List<CombatAction> getAvailableActions() {
        if (!isAlive()) {
            return List.of();
        }
        List<CombatAction> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        if (inventory.hasUsableItems()) {
            actions.add(new UseItemAction());
        }
        if (getSpecialSkillCooldown() == 0) {
            actions.add(new UseSpecialSkillAction());
        }
        return List.copyOf(actions);
    }
    public void tickCooldown() {
        if (skillCooldown > 0) {
            skillCooldown--;  // Decrease cooldown by 1 each round
        }
    }

    // Set the skill cooldown
    public void setSkillCooldown() {
        this.skillCooldown = SKILL_COOLDOWN;  // Set cooldown to the constant value
    }

    // Check if the skill is ready (cooldown is 0)
    public boolean isSkillReady() {
        return skillCooldown == 0;
    }

    // Get current skill cooldown
    public int getSkillCooldown() {
        return skillCooldown;
    }

    // Print the cooldown status
    public void printCooldownStatus() {
        if (isSkillReady()) {
            System.out.printf("    [%s special skill: READY]%n", getName());
        } else {
            System.out.printf("    [%s special skill cooldown: %d round(s)]%n",
                    getName(), getSkillCooldown());
        }
    }

    // Add effect to the player
    public void addEffect(StatusEffect effect) {
        activeEffects.add(effect);  // Add the effect to the list of active effects
    }

    // Check if the player has the defend effect active
    public boolean hasDefendActive() {
        for (StatusEffect effect : activeEffects) {
            if (effect instanceof DefendEffect) {
                return true;  // If the player has an active DefendEffect, return true
            }
        }
        return false;
    }

    // Method to get all active effects on the player
    public List<StatusEffect> getActiveEffects() {
        return List.copyOf(activeEffects);  // Return the list of active effects
    }
}
