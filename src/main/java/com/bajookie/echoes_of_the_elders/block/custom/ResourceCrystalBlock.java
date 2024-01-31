package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.item.custom.StatFruit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ResourceCrystalBlock extends AmethystClusterBlock {
    private final Type type;

    public ResourceCrystalBlock(Type type) {
        super(7, 3, AbstractBlock.Settings.create().solid().nonOpaque().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5f).pistonBehavior(PistonBehavior.DESTROY));
        this.type = type;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Block.dropStack(world, pos, getStackForType());
        super.onBreak(world, pos, state, player);
    }

    private ItemStack getStackForType() {
        ItemStack stack = new ItemStack(Items.CAKE);
        switch (this.type) {
            case GOLD -> stack = new ItemStack(Items.GOLD_INGOT);
            case IRON -> stack = new ItemStack(Items.IRON_INGOT);
            case EMERALD -> stack = new ItemStack(Items.EMERALD);
            case DIAMOND -> stack = new ItemStack(Items.DIAMOND);
            case COAL -> stack = new ItemStack(Items.COAL);
        }
        return stack;
    }

    public enum Type {
        GOLD,
        DIAMOND,
        EMERALD,
        IRON,
        COAL
    }
}
