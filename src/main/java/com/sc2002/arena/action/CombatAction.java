package com.sc2002.arena.action;

public interface CombatAction {
    String getName();

    boolean requiresTarget();

    ActionResult execute(ActionContext context);
}
