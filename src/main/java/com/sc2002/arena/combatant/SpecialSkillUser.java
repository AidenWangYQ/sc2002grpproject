package com.sc2002.arena.combatant;

import com.sc2002.arena.skill.SpecialSkill;

public interface SpecialSkillUser {
    SpecialSkill getSpecialSkill();

    int getSpecialSkillCooldown();

    void setSpecialSkillCooldown(int cooldown);

    int getSpecialSkillCooldownDuration();

    void tickSpecialSkillCooldown();
}
