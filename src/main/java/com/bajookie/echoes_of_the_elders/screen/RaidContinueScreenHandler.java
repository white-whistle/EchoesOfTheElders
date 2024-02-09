package com.bajookie.echoes_of_the_elders.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;

public class RaidContinueScreenHandler extends ScreenHandler {
    private static final int INVENTORY_SIZE = 3;
    public final Inventory inventory;

    public RaidContinueScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(INVENTORY_SIZE));
    }

    public RaidContinueScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlerTypes.RAID_CONTINUE, syncId);
        int l = 0;
        int k = 0;
        ShulkerBoxScreenHandler.checkSize(inventory, INVENTORY_SIZE);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new SlotUtil.Readonly(inventory, 0, 69, 40));
        this.addSlot(new SlotUtil.Readonly(inventory, 1, 97, 40));
        this.addSlot(new SlotUtil.Data(inventory, 2, 0, 0));
    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

}
