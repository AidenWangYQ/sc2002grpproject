package com.sc2002.arena.combatant;

import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.skill.ArcaneBlastSkill;

/**
 * Concrete player class representing the Wizard archetype.
 *
 * Wizard configuration:
 * - lower HP/defense than Warrior
 * - higher base attack
 * - Arcane Blast as the special skill
 *
 * This class only provides Wizard-specific stats and skill choice.
 * Common player behaviour remains in Player.
 */
public final class Wizard extends Player {
    /**
     * Construct a Wizard with the chosen starting inventory.
     */
    public Wizard(Inventory inventory) {
        super("Wizard", 200, 50, 10, 20, inventory, new ArcaneBlastSkill());
    }
}