package com.sc2002.battle.domain;

public interface CombatAction {
    String getName();

    boolean requiresTarget();

    ActionResult execute(ActionContext context);
}
