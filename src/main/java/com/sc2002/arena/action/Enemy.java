package com.sc2002.arena.combatant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sc2002.arena.action.Action;
import com.sc2002.arena.effect.StatusEffect;
import com.sc2002.arena.skill.Skill;
import com.sc2002.arena.strategy.EnemyActionStrategy;

public abstract class Enemy implements Combatant {
    private final String name;
    private final int maxHp;
    private int currentHp;
    private final int attack;
    private final int defense;
    private final int speed;

    private final List<StatusEffect> statusEffects;
    private int specialCooldownRemaining;

    private final EnemyActionStrategy actionStrategy;

    protected Enemy(
            String name,
            int maxHp,
            int attack,
            int defense,
            int speed,
            EnemyActionStrategy actionStrategy
    ) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.actionStrategy = actionStrategy;
        this.statusEffects = new ArrayList<>();
        this.specialCooldownRemaining = 0;
    }

    public EnemyActionStrategy getActionStrategy() {
        return actionStrategy;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getCurrentHp() {
        return currentHp;
    }

    @Override
    public int getAttack() {
        return attack;
    }

    @Override
    public int getDefense() {
        return defense;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public boolean isAlive() {
        return currentHp > 0;
    }

    @Override
    public void receiveDamage(int amount) {
        currentHp = Math.max(0, currentHp - Math.max(0, amount));
    }

    @Override
    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + Math.max(0, amount));
    }

    @Override
    public void addStatusEffect(StatusEffect effect) {
        if (effect != null) {
            statusEffects.add(effect);
        }
    }

    @Override
    public void removeExpiredEffects() {
        Iterator<StatusEffect> iterator = statusEffects.iterator();
        while (iterator.hasNext()) {
            StatusEffect effect = iterator.next();
            if (effect.isExpired()) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<StatusEffect> getStatusEffects() {
        return List.copyOf(statusEffects);
    }

    @Override
    public boolean hasEffect(Class<? extends StatusEffect> effectType) {
        for (StatusEffect effect : statusEffects) {
            if (effectType.isInstance(effect) && !effect.isExpired()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Action> getAvailableActions() {
        return List.of();
    }

    @Override
    public Skill getSpecialSkill() {
        return null;
    }

    @Override
    public int getSpecialCooldownRemaining() {
        return specialCooldownRemaining;
    }

    @Override
    public void setSpecialCooldownRemaining(int turns) {
        this.specialCooldownRemaining = Math.max(0, turns);
    }

    @Override
    public void decrementSpecialCooldownIfNeeded() {
        if (specialCooldownRemaining > 0) {
            specialCooldownRemaining--;
        }
    }

    @Override
    public boolean canAct() {
        return isAlive();
    }
}