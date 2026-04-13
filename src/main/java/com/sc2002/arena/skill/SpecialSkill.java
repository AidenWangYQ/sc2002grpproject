package com.sc2002.arena.skill;

import com.sc2002.arena.action.*;

public interface SpecialSkill {
    String getName();

    boolean requiresTarget();

    ActionResult use(ActionContext context, SkillUseMode mode);
}
