package com.sc2002.arena.item;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

public interface Item {
    String getName();

    ActionResult use(ActionContext context);
}
