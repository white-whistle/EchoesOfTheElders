package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.screen.client.RelicomiconScreen;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class Relicomicon extends Item {
    public Relicomicon() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        generateBaseBookNbt(user.getStackInHand(hand));
        if (world.isClient){
            MinecraftClient.getInstance().setScreen(new RelicomiconScreen(Text.literal("Relicon")));
        }
        return super.use(world, user, hand);
    }
    private void generateBaseBookNbt(ItemStack book){
        NbtCompound comp = book.getOrCreateNbt();
        if (!comp.contains("pages")){
        }
    }
    private void updatedBookNbt(){}

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
}
