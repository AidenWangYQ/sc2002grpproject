package com.sc2002.arena.item;

import com.sc2002.arena.action.*;

public interface Item {
    String getName();

    ActionResult use(ActionContext context);
}
