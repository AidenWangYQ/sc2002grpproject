package com.sc2002.battle.domain;

public interface SpecialSkill {
    String getName();

    boolean requiresTarget();

    ActionResult use(ActionContext context, SkillUseMode mode);
}
