package com.sc2002.arena.combatant;

import java.util.*;
import com.sc2002.arena.action.*;
import com.sc2002.arena.item.*;
import com.sc2002.arena.skill.*;

public abstract class Player extends Combatant {
    private final Inventory inventory;
    private final SpecialSkill specialSkill;

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
    }

    public Inventory getInventory() {
        return inventory;
    }

    public SpecialSkill getSpecialSkill() {
        return specialSkill;
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
