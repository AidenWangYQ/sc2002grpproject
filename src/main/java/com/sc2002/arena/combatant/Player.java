package com.sc2002.arena.combatant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sc2002.arena.action.BasicAttackAction;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.action.DefendAction;
import com.sc2002.arena.action.UseItemAction;
import com.sc2002.arena.action.UseSpecialSkillAction;
import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.skill.SpecialSkill;

/**
 * Abstract base class for all player-controlled combatants.
 *
 * Responsibilities:
 * - store player-specific resources such as inventory and special skill
 * - manage special skill cooldown state
 * - determine which actions are currently available to the player
 *
 * Design notes:
 * - Implements SpecialSkillUser because players can use skills with cooldown.
 * - The action list is generated dynamically so UI only shows valid choices.
 */
public abstract class Player extends Combatant implements SpecialSkillUser {
    /** All player skills share the same cooldown duration in this implementation. */
    private static final int SPECIAL_SKILL_COOLDOWN_DURATION = 3;

    /** Player-owned inventory containing the two starting consumable items. */
    private final Inventory inventory;

    /** Character-specific special skill, e.g. Shield Bash or Arcane Blast. */
    private final SpecialSkill specialSkill;

    /** Remaining cooldown turns before skill can be used again. */
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

    /** @return player's inventory */
    public Inventory getInventory() {
        return inventory;
    }

    /** @return player's assigned special skill */
    @Override
    public SpecialSkill getSpecialSkill() {
        return specialSkill;
    }

    /** @return remaining cooldown turns */
    @Override
    public int getSpecialSkillCooldown() {
        return specialSkillCooldown;
    }

    /**
     * Update cooldown value explicitly.
     * Used after a skill is triggered.
     */
    @Override
    public void setSpecialSkillCooldown(int cooldown) {
        if (cooldown < 0) {
            throw new IllegalArgumentException("specialSkillCooldown cannot be negative.");
        }
        this.specialSkillCooldown = cooldown;
    }

    /** @return fixed cooldown duration for player skills */
    @Override
    public int getSpecialSkillCooldownDuration() {
        return SPECIAL_SKILL_COOLDOWN_DURATION;
    }

    /**
     * Decrease skill cooldown by 1 after a valid turn if cooldown is active.
     */
    @Override
    public void tickSpecialSkillCooldown() {
        if (specialSkillCooldown > 0) {
            specialSkillCooldown--;
        }
    }

    /**
     * Build the list of actions currently available to the player.
     *
     * Logic:
     * - dead players cannot act
     * - basic attack and defend are always available while alive
     * - item action only appears if there are usable items left
     * - special skill only appears if cooldown is 0
     *
     * This keeps UI logic simple and prevents invalid choices from being shown.
     */
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