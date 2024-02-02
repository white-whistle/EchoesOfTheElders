package com.bajookie.echoes_of_the_elders.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PandorasBagScreenHandler extends ScreenHandler {
    private static final int INVENTORY_SIZE = 27;
    private final Inventory inventory;

    public PandorasBagScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(INVENTORY_SIZE));
    }

    public PandorasBagScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlerTypes.PANDORAS_BAG, syncId);
        int l;
        int k;
        ShulkerBoxScreenHandler.checkSize(inventory, INVENTORY_SIZE);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        
        for (k = 0; k < 3; ++k) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new SlotUtil.TakeOnly(inventory, l + k * 9, 8 + l * 18, 18 + k * 18));
            }
        }
        for (k = 0; k < 3; ++k) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }
        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < this.inventory.size() ? !this.insertItem(itemStack2, this.inventory.size(), this.slots.size(), true) : !this.insertItem(itemStack2, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

}
