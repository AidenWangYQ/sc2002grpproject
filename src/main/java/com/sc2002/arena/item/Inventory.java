package com.sc2002.arena.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sc2002.arena.action.ActionContext;
import com.sc2002.arena.action.ActionResult;

public final class Inventory {
    private final List<SlotState> slots = new ArrayList<>();

    public Inventory(List<Item> startingItems) {
        Objects.requireNonNull(startingItems, "startingItems cannot be null");
        if (startingItems.size() != 2) {
            throw new IllegalArgumentException("Inventory must start with exactly 2 single-use items.");
        }
        for (Item item : startingItems) {
            slots.add(new SlotState(Objects.requireNonNull(item, "item cannot be null")));
        }
    }

    public List<InventorySlot> getSlots() {
        List<InventorySlot> view = new ArrayList<>();
        for (int index = 0; index < slots.size(); index++) {
            SlotState slot = slots.get(index);
            view.add(new InventorySlot(index, slot.item(), slot.consumed()));
        }
        return List.copyOf(view);
    }

    public List<Item> getUsableItems() {
        List<Item> items = new ArrayList<>();
        for (SlotState slot : slots) {
            if (!slot.consumed()) {
                items.add(slot.item());
            }
        }
        return List.copyOf(items);
    }

    public boolean hasUsableItems() {
        for (SlotState slot : slots) {
            if (!slot.consumed()) {
                return true;
            }
        }
        return false;
    }

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

    public record InventorySlot(int index, Item item, boolean consumed) {
        public boolean isUsable() {
            return !consumed;
        }
    }

    private record SlotState(Item item, boolean consumed) {
        private SlotState(Item item) {
            this(item, false);
        }

        private SlotState consume() {
            return new SlotState(item, true);
        }
    }
}
