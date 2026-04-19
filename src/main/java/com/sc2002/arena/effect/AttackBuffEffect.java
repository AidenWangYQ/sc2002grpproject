package com.sc2002.arena.effect;

import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.strategy.BattleContext;

/**
 * A status effect that increases the target's attack by a fixed amount.
 *
 * Responsibilities:
 * - apply a flat attack bonus
 * - provide non-expiring buff behaviour
 */
public final class AttackBuffEffect extends StatusEffect {

    /** Flat attack bonus applied by this effect. */
    private final int attackBonus;

    /**
     * Constructs an attack buff effect.
     *
     * @param attackBonus amount of attack increase
     */
    public AttackBuffEffect(int attackBonus) {
        super("Attack Buff");
        if (attackBonus <= 0) {
            throw new IllegalArgumentException("attackBonus must be greater than 0.");
        }
        this.attackBonus = attackBonus;
    }

    /**
     * Increases attack by a fixed bonus.
     */
    @Override
    public int modifyAttack(Combatant target, int attack, BattleContext context) {
        return attack + attackBonus;
    }

    /** @return attack bonus value */
    public int getAttackBonus() {
        return attackBonus;
    }

    /** @return always false as this effect does not expire */
    @Override
    public boolean isExpired() {
        return false;
    }
}