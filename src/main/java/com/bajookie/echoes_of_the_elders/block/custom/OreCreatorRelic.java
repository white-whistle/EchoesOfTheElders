package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.WorldGenerationProgressLogger;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.gen.OreVeinSampler;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OreCreatorRelic extends Block {
    public OreCreatorRelic() {
        super(AbstractBlock.Settings.create().ticksRandomly().strength(90000f).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().pistonBehavior(PistonBehavior.IGNORE));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) != 0) {
            return;
        }
        Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = null;
        if (this.canGrowIn(blockState)) {
            block = randomResourceCrystal(random.nextBetween(0,100));
        }
        if (block != null) {
            BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(ResourceCrystalBlock.FACING, direction)).with(ResourceCrystalBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
            world.setBlockState(blockPos, blockState2);
        }

    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
    }
    private boolean canGrowIn(BlockState state) {
        return state.getBlock() == Blocks.AMETHYST_CLUSTER;
    }
    private Block randomResourceCrystal(int res){
        if (res>=90){
            return ModBlocks.EMERALD_CRYSTAL;
        } else if (res>=80){
            return ModBlocks.DIAMOND_CRYSTAL;
        } else if (res>=60){
            return ModBlocks.GOLD_CRYSTAL;
        } else if (res>=30){
            return ModBlocks.IRON_CRYSTAL;
        } else {
            return ModBlocks.COAL_CRYSTAL;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.ore_creator_block.effect"));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
