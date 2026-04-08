package sc2002.battle.domain;

public interface StatusEffect {
    String getName();

    default void onApply(Combatant target, BattleContext context) {
    }

    default void onTurnStart(Combatant target, BattleContext context) {
    }

    default void onTurnEnd(Combatant target, BattleContext context) {
    }

    default void onRoundEnd(Combatant target, BattleContext context) {
    }

    default int modifyIncomingDamage(Combatant target, Combatant attacker, int damage, BattleContext context) {
        return damage;
    }

    default int modifyAttack(Combatant target, int attack, BattleContext context) {
        return attack;
    }

    default int modifyDefense(Combatant target, int defense, BattleContext context) {
        return defense;
    }

    default boolean preventsAction() {
        return false;
    }

    boolean isExpired();
}
