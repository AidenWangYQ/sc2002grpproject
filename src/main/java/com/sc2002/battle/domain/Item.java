package sc2002.battle.domain;

public interface Item {
    String getName();

    ActionResult use(ActionContext context);
}
