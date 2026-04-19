package com.sc2002.arena.combatant;

import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.skill.ShieldBashSkill;

/**
 * Concrete player class representing the Warrior archetype.
 *
 * Warrior configuration:
 * - high HP
 * - solid defense
 * - Shield Bash as the special skill
 *
 * This class only supplies character-specific configuration.
 * All common player logic is inherited from Player.
 */
public final class Warrior extends Player {
    /**
     * Construct a Warrior with the chosen starting inventory.
     */
    public Warrior(Inventory inventory) {
        super("Warrior", 260, 40, 20, 30, inventory, new ShieldBashSkill());
    }
}