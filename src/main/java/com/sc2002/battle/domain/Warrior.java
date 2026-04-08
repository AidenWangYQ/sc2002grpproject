package sc2002.battle.domain;

public final class Warrior extends Player {
    public Warrior(Inventory inventory) {
        super("Warrior", 260, 40, 20, 30, inventory, new ShieldBashSkill());
    }
}
