package com.sc2002.arena.combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.action.DefendAction;
import com.sc2002.arena.action.UseItemAction;
import com.sc2002.arena.action.UseSpecialSkillAction;

import com.sc2002.arena.combatant.Player;

import com.sc2002.arena.skill.SpecialSkill;

import com.sc2002.arena.item.Inventory;

public abstract class Player extends Combatant implements SpecialSkillUser {
    private static final int SPECIAL_SKILL_COOLDOWN_DURATION = 3;

    private final Inventory inventory;
    private final SpecialSkill specialSkill;
    private int specialSkillCooldown;

    protected Player(
            String name,
            int maxHp,
            int baseAttack,
            int baseDefense,
            int speed,
            Inventory inventory,
            SpecialSkill specialSkill
    ) {
        super(name, maxHp, baseAttack, baseDefense, speed);
        this.inventory = Objects.requireNonNull(inventory, "inventory cannot be null");
        this.specialSkill = Objects.requireNonNull(specialSkill, "specialSkill cannot be null");
        this.specialSkillCooldown = 0;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    @Override
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    @Override
    public void setSpecialSkillCooldown(int cooldown) {
        if (cooldown < 0) {
            throw new IllegalArgumentException("specialSkillCooldown cannot be negative.");
        }
        this.specialSkillCooldown = cooldown;
    }

    @Override
    public int getSpecialSkillCooldownDuration() {
        return SPECIAL_SKILL_COOLDOWN_DURATION;
    }

    @Override
    public void tickSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    public List<CombatAction> getAvailableActions() {
        if (!isAlive()) {
            return List.of();
        }
        List<CombatAction> actions = new ArrayList<>();
        actions.add(new BasicAttackAction());
        actions.add(new DefendAction());
        if (inventory.hasUsableItems()) {
            actions.add(new UseItemAction());
        }
        if (getSpecialSkillCooldown() == 0) {
            actions.add(new UseSpecialSkillAction());
        }
        return List.copyOf(actions);
    }
}
