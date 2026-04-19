package com.sc2002.arena.combatant;

import com.sc2002.arena.skill.DragonBreathSkill;
import com.sc2002.arena.skill.SpecialSkill;
import com.sc2002.arena.strategy.DragonEnemyActionStrategy;

/**
 * Boss enemy with both AI behaviour and a special skill.
 *
 * Responsibilities:
 * - configure Dragon-specific base stats
 * - provide Dragon-specific special skill and cooldown handling
 * - use a specialised enemy strategy that can choose between
 *   BasicAttack and Dragon Breath
 *
 * Design notes:
 * - Extends Enemy because it is AI-controlled.
 * - Implements SpecialSkillUser because, unlike Goblin/Wolf, it has a skill.
 */
public final class Dragon extends Enemy implements SpecialSkillUser {
    /** Dragon skill shares the same 3-turn cooldown convention. */
    private static final int SPECIAL_SKILL_COOLDOWN_DURATION = 3;

    /** Dragon Breath skill instance. */
    private final SpecialSkill specialSkill;

    /** Remaining cooldown turns before Dragon Breath can be used again. */
    private int specialSkillCooldown;

    /** Convenience constructor with default display name. */
    public Dragon() {
        this("Dragon");
    }

    /**
     * Named constructor used by LevelFactory for boss setup.
     */
    public Dragon(String name) {
        super(name, 320, 70, 25, 18, new DragonEnemyActionStrategy());
        this.specialSkill = new DragonBreathSkill();
        this.specialSkillCooldown = 0;
    }

    /** @return Dragon's special skill */
    @Override
    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    /** @return remaining cooldown turns */
    @Override
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    /** Update Dragon's skill cooldown value. */
    @Override
    public void setSpecialSkillCooldown(int cooldown) {
        if (cooldown < 0) {
            throw new IllegalArgumentException("specialSkillCooldown cannot be negative.");
        }
        this.specialSkillCooldown = cooldown;
    }

    /** @return fixed cooldown duration for Dragon Breath */
    @Override
    public int getSpecialSkillCooldownDuration() {
        return SPECIAL_SKILL_COOLDOWN_DURATION;
    }

    /** Reduce cooldown after a turn, if currently active. */
    @Override
    public void tickSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }
}