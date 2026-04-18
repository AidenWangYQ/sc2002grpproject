package com.sc2002.arena.combatant;

import com.sc2002.arena.skill.DragonBreathSkill;
import com.sc2002.arena.skill.SpecialSkill;
import com.sc2002.arena.strategy.DragonEnemyActionStrategy;

public final class Dragon extends Enemy implements SpecialSkillUser {
    private static final int SPECIAL_SKILL_COOLDOWN_DURATION = 3;

    private final SpecialSkill specialSkill;
    private int specialSkillCooldown;

    public Dragon() {
        this("Dragon");
    }

    public Dragon(String name) {
        super(name, 320, 70, 25, 18, new DragonEnemyActionStrategy());
        this.specialSkill = new DragonBreathSkill();
        this.specialSkillCooldown = 0;
    }

    @Override
    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    @Override
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    @Override
    public void setSpecialSkillCooldown(int cooldown) {
        if (cooldown < 0) {
            throw new IllegalArgumentException("specialSkillCooldown cannot be negative.");
        }
        this.specialSkillCooldown = cooldown;
    }

    @Override
    public int getSpecialSkillCooldownDuration() {
        return SPECIAL_SKILL_COOLDOWN_DURATION;
    }

    @Override
    public void tickSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }
}
