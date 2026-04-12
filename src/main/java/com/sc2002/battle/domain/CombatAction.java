package com.sc2002.battle.domain;
import sc2002.battle.domain.ActionResult;
import sc2002.battle.domain.ActionContext;
public interface CombatAction {
    String getName();

    boolean requiresTarget();

    ActionResult execute(ActionContext context);
}
