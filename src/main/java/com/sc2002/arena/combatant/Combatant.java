package com.sc2002.arena.combatant;

import java.util.List;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.skill.Skill;

public interface Combatant {
    String getName();

    int getMaxHp();
    int getCurrentHp();
    int getAttack();
    int getDefense();
    int getSpeed();

    boolean isAlive();

    void receiveDamage(int amount);
    void heal(int amount);

    void addStatusEffect(StatusEffect effect);
    void removeExpiredEffects();
    List<StatusEffect> getStatusEffects();

    boolean hasEffect(Class<? extends StatusEffect> effectType);

    List<Action> getAvailableActions();
    Skill getSpecialSkill();

    int getSpecialCooldownRemaining();
    void setSpecialCooldownRemaining(int turns);
    void decrementSpecialCooldownIfNeeded();

    boolean canAct();
}
