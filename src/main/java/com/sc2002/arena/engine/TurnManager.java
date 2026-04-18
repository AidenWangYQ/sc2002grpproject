package com.sc2002.arena.engine;

import java.util.List;

import com.sc2002.arena.BattleUI.BattleUI;
import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;
import com.sc2002.arena.action.CombatAction;
import com.sc2002.arena.action.UseItemAction;
import com.sc2002.arena.action.UseSpecialSkillAction;
import com.sc2002.arena.combatant.Combatant;
import com.sc2002.arena.combatant.Enemy;
import com.sc2002.arena.combatant.Player;
import com.sc2002.arena.item.Inventory;
import com.sc2002.arena.item.PowerStone;
import com.sc2002.arena.strategy.TurnOrderStrategy;

public class TurnManager {
    private final TurnOrderStrategy strategy;
    private final ActionResolver actionResolver;
    private final BattleUI ui;

    public TurnManager(TurnOrderStrategy strategy, ActionResolver actionResolver, BattleUI ui) {
        this.strategy = strategy;
        this.actionResolver = actionResolver;
        this.ui = ui;
    }

    public List<Combatant> generateTurnOrder(BattleContext context) {
        return strategy.getOrder(context.getLivingCombatants());
    }

    public TurnResolution processTurn(Combatant current, BattleContext context) {
        if (!current.isAlive()) {
            return TurnResolution.skipped(current);
        }

        ui.printTurnHeader(current);
        current.onTurnStart(context);

        if (!current.canAct()) {
            ui.printCannotAct(current);
            current.onTurnEnd(context);
            current.removeExpiredEffects();
            return TurnResolution.skipped(current);
        }

        CombatAction action;
        ActionContext actionContext;
        if (current instanceof Player player) {
            action = ui.promptPlayerAction(player, player.getAvailableActions(), context.getLivingEnemies());
            actionContext = buildPlayerActionContext(player, action, context);
        } else if (current instanceof Enemy enemy) {
            action = enemy.chooseAction(context);
            actionContext = buildEnemyActionContext(enemy, action, context);
        } else {
            throw new IllegalStateException("Unsupported combatant type: " + current.getClass().getName());
        }

        ActionResult result = actionResolver.resolve(action, actionContext);
        current.onTurnEnd(context);
        current.removeExpiredEffects();
        return TurnResolution.performed(current, result);
    }

    private ActionContext buildPlayerActionContext(Player player, CombatAction action, BattleContext context) {
        if (action instanceof UseItemAction) {
            List<Inventory.InventorySlot> availableSlots = player.getInventory().getSlots().stream()
                    .filter(Inventory.InventorySlot::isUsable)
                    .toList();
            int slotIndex = ui.promptItemSlotSelection(player, availableSlots);
            Inventory.InventorySlot selectedSlot = findSlotByIndex(availableSlots, slotIndex);
            boolean needsTarget = selectedSlot.item() instanceof PowerStone
                    && player.getSpecialSkill().requiresTarget();
            Combatant target = needsTarget ? ui.promptTargetSelection(context.getLivingEnemies()) : null;
            return ActionContext.forItemAction(player, target, slotIndex, context);
        }

        boolean needsTarget = action.requiresTarget()
                || (action instanceof UseSpecialSkillAction && player.getSpecialSkill().requiresTarget());
        if (needsTarget) {
            return ActionContext.forTargetedAction(player, ui.promptTargetSelection(context.getLivingEnemies()), context);
        }
        return ActionContext.forUntargetedAction(player, context);
    }

    private ActionContext buildEnemyActionContext(Enemy enemy, CombatAction action, BattleContext context) {
        if (action.requiresTarget()) {
            return ActionContext.forTargetedAction(enemy, enemy.chooseTarget(context), context);
        }
        return ActionContext.forUntargetedAction(enemy, context);
    }

    private Inventory.InventorySlot findSlotByIndex(List<Inventory.InventorySlot> availableSlots, int slotIndex) {
        return availableSlots.stream()
                .filter(slot -> slot.index() == slotIndex)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Selected item slot is not currently usable: " + slotIndex));
    }
}
