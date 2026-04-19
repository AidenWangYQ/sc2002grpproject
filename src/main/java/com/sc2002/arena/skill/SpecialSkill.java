package com.sc2002.arena.skill;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

/**
 * Represents a special skill that a combatant can use during battle.
 *
 * Responsibilities:
 * - define skill identity and targeting rules
 * - execute skill logic and return results
 */
public interface SpecialSkill {

    /** @return skill name for UI and logging */
    String getName();

    /** @return true if the skill requires a target */
    boolean requiresTarget();

    /**
     * Executes the special skill.
     *
     * @param context execution data
     * @param mode how the skill is triggered
     * @return result of skill execution
     */
    ActionResult use(ActionContext context, SkillUseMode mode);
}