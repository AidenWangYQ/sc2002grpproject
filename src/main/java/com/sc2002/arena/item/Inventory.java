package com.sc2002.arena.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

/**
 * Represents a player's inventory that holds consumable items.
 *
 * Responsibilities:
 * - store and manage item slots
 * - track whether items are consumed
 * - execute item usage and return results
 */
public final class Inventory {

    /** Internal list of inventory slots and their states. */
    private final List<SlotState> slots = new ArrayList<>();

    /**
     * Constructs an inventory with initial items.
     *
     * @param startingItems initial items (must contain exactly 2 items)
     */
    public Inventory(List<Item> startingItems) {
        Objects.requireNonNull(startingItems, "startingItems cannot be null");

        if (startingItems.size() != 2) {
            throw new IllegalArgumentException("Inventory must start with exactly 2 single-use items.");
        }

        for (Item item : startingItems) {
            slots.add(new SlotState(Objects.requireNonNull(item, "item cannot be null")));
        }
    }

    /** @return immutable view of inventory slots */
    public List<InventorySlot> getSlots() {
        List<InventorySlot> view = new ArrayList<>();
        for (int index = 0; index < slots.size(); index++) {
            SlotState slot = slots.get(index);
            view.add(new InventorySlot(index, slot.item(), slot.consumed()));
        }
        return List.copyOf(view);
    }

    /** @return list of items that have not been consumed */
    public List<Item> getUsableItems() {
        List<Item> items = new ArrayList<>();
        for (SlotState slot : slots) {
            if (!slot.consumed()) {
                items.add(slot.item());
            }
        }
        return List.copyOf(items);
    }

    /** @return true if at least one item is still usable */
    public boolean hasUsableItems() {
        for (SlotState slot : slots) {
            if (!slot.consumed()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Uses an item from the specified slot.
     *
     * @param slotIndex index of item slot
     * @param context execution data
     * @return result of item usage
     */
    public ActionResult useItem(int slotIndex, ActionContext context) {
        if (slotIndex < 0 || slotIndex >= slots.size()) {
            throw new IllegalArgumentException("Invalid inventory slot: " + slotIndex);
        }

        SlotState slot = slots.get(slotIndex);

        if (slot.consumed()) {
            throw new IllegalStateException("Item in slot " + slotIndex + " has already been consumed.");
        }

        ActionResult result = slot.item().use(context);

        slots.set(slotIndex, slot.consume());

        result.recordConsumedItem(slot.item().getName(), slotIndex);

        return result;
    }

    /**
     * Immutable view representation of an inventory slot.
     */
    public record InventorySlot(int index, Item item, boolean consumed) {
        /** @return true if item can still be used */
        public boolean isUsable() {
            return !consumed;
        }
    }

    /**
     * Internal state representation of a slot.
     */
    private record SlotState(Item item, boolean consumed) {

        private SlotState(Item item) {
            this(item, false);
        }

        /** @return new state marking item as consumed */
        private SlotState consume() {
            return new SlotState(item, true);
        }
    }
}