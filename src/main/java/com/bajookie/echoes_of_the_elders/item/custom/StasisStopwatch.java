package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StasisStopwatch extends Item {
    public StasisStopwatch() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        NbtCompound nbt = stack.getOrCreateNbt();
        if (nbt != null) {
            if (!nbt.contains("used_ticks")) {
                nbt.putInt("used_ticks", 0);
                nbt.putBoolean("is_active", false);
                nbt.putInt("active_x", 0);
                nbt.putInt("active_y", 0);
                nbt.putInt("active_z", 0);
            }
            if (nbt.getBoolean("is_active")) {

                nbt.putInt("used_tick", nbt.getInt("used_tick") + 1);
                if (nbt.getInt("used_tick") >= 7 * 20) {
                    if (entity instanceof PlayerEntity player) {
                        if (!world.isClient) {
                            backToStasis(player, world, stack);
                        }
                    }
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        NbtCompound nbt = user.getStackInHand(hand).getNbt();
        if (nbt != null) {
            if (!user.getItemCooldownManager().isCoolingDown(user.getStackInHand(hand).getItem())) {
                if (nbt.getBoolean("is_active")) {
                    backToStasis(user, world, user.getStackInHand(hand));
                } else {
                    nbt.putBoolean("is_active", true);
                    BlockPos pos = user.getBlockPos();
                    nbt.putInt("active_x", pos.getX());
                    nbt.putInt("active_y", pos.getY());
                    nbt.putInt("active_z", pos.getZ());
                    user.getItemCooldownManager().set(this, 10);
                }
            }
        }
        return super.use(world, user, hand);
    }

    private void backToStasis(PlayerEntity player, World world, ItemStack stack) {
        player.getItemCooldownManager().set(this, 20 * 15);
        BlockPos pos = player.getBlockPos();
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            EOTE.LOGGER.info("back to stasis");
            nbt.putBoolean("is_active", false);
            nbt.putInt("used_tick", 0);
            int x = nbt.getInt("active_x");
            int y = nbt.getInt("active_y");
            int z = nbt.getInt("active_z");
            player.teleport(x, y, z);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        var nbt = stack.getNbt();

        if (nbt == null) return false;

        return nbt.getBoolean("is_active");
    }
}
