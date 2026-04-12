package sc2002.battle.domain;
import sc2002.battle.domain.ActionResult;
import sc2002.battle.domain.ActionContext;

import java.util.List;

public interface Item {
    String getName();

    ActionResult use(ActionContext context);

    void consume();

    void use(sc2002.battle.domain.Player player, List<sc2002.battle.domain.Combatant> targets);

    boolean isUsed();

    // Mark the item as used
    void use();
}
