package com.sc2002.arena.skill;

/**
 * Defines how a special skill is triggered or executed.
 *
 * Responsibilities:
 * - distinguish normal skill usage from item-triggered usage
 */
public enum SkillUseMode {

    /** Standard manual activation of a skill */
    NORMAL,

    /** Skill activation triggered by Power Stone item */
    POWER_STONE_TRIGGER
}