package com.bajookie.echoes_of_the_elders.screen;

import com.bajookie.echoes_of_the_elders.item.custom.PandorasBag;
import com.bajookie.echoes_of_the_elders.system.ItemStack.RaidReward;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class PandorasBagScreenHandler extends ScreenHandler {
    private static final int INVENTORY_SIZE = 27;
    private final Inventory inventory;
    private final ItemStack bag;

    public PandorasBagScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ItemStack.EMPTY);
    }

    public PandorasBagScreenHandler(int syncId, PlayerInventory playerInventory, ItemStack bag) {
        super(ModScreenHandlerTypes.PANDORAS_BAG, syncId);
        int l;
        int k;
        this.bag = bag;
        this.inventory = new SimpleInventory(INVENTORY_SIZE) {
            @Override
            public void onClose(PlayerEntity player) {
                super.onClose(player);

                if (this.isEmpty()) {
                    bag.decrement(1);
                    return;
                }

                var invNbt = this.toNbtList();
                var nbt = bag.getNbt();
                if (nbt != null) {
                    nbt.put(PandorasBag.BAG_INVENTORY, invNbt);
                }
            }
        };

        var nbt = bag.getNbt();
        var tempInv = new SimpleInventory(INVENTORY_SIZE);
        if (nbt != null) {
            var invNbt = nbt.getList(PandorasBag.BAG_INVENTORY, NbtElement.COMPOUND_TYPE);
            tempInv.readNbtList(invNbt);
        }

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            if (this.inventory.getStack(i).isEmpty()) {
                var prevItem = tempInv.getStack(i);
                if (!prevItem.isEmpty()) {
                    this.inventory.setStack(i, prevItem);
                    continue;
                }

                var item = RaidReward.dequeueItem(bag);
                if (item != null) {
                    this.inventory.setStack(i, item);
                }
            }
        }

        PandorasBag.setBagInventory(bag, (SimpleInventory) inventory);
        inventory.onOpen(playerInventory.player);

        for (k = 0; k < 3; ++k) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new SlotUtil.TakeOnly(inventory, l + k * 9, 8 + l * 18, 18 + k * 18) {
                    @Override
                    public void onTakeItem(PlayerEntity player, ItemStack stack) {
                        super.onTakeItem(player, stack);

                        var next = RaidReward.dequeueItem(bag);
                        if (next != null) {
                            setStack(next);
                        }

                        var invNbt = ((SimpleInventory) inventory).toNbtList();
                        var nbt = bag.getNbt();
                        if (nbt != null) {
                            nbt.put(PandorasBag.BAG_INVENTORY, invNbt);
                        }
                    }

                    @Override
                    public void onQuickTransfer(ItemStack newItem, ItemStack original) {
                        super.onQuickTransfer(newItem, original);

                        var next = RaidReward.dequeueItem(bag);
                        if (next != null) {
                            setStack(next);
                        }

                        var invNbt = ((SimpleInventory) inventory).toNbtList();
                        var nbt = bag.getNbt();
                        if (nbt != null) {
                            nbt.put(PandorasBag.BAG_INVENTORY, invNbt);
                        }
                    }
                });
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
                var next = RaidReward.dequeueItem(bag);
                slot2.setStack(next != null ? next : ItemStack.EMPTY);
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
