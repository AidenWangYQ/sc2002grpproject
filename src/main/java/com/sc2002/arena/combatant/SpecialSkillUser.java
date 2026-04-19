package com.sc2002.arena.combatant;

import com.sc2002.arena.skill.SpecialSkill;

/**
 * Capability interface for combatants that can use special skills.
 *
 * Design notes:
 * - Demonstrates ISP: only entities that actually have skills implement this
 *   interface, instead of forcing all Combatants to expose skill APIs.
 * - Used by TurnManager / UseSpecialSkillAction to interact with skill-bearing
 *   entities in a generic way.
 */
public interface SpecialSkillUser {
    /** @return the skill owned by this combatant */
    SpecialSkill getSpecialSkill();

    /** @return remaining turns until the skill is available again */
    int getSpecialSkillCooldown();

    /** Explicitly set cooldown after skill use or other effects. */
    void setSpecialSkillCooldown(int cooldown);

    /** @return standard cooldown duration for this entity's skill */
    int getSpecialSkillCooldownDuration();

    /** Reduce cooldown by one turn when appropriate. */
    void tickSpecialSkillCooldown();
}