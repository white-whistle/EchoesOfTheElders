package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.client.animation.AnimationUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.FernBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SpiritalGrass extends FernBlock {
    public SpiritalGrass(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextBetween(0, 40) < 2) {
            world.addParticle(ParticleTypes.END_ROD, pos.getX(), pos.getY() + 1, pos.getZ(), random.nextBetween(-3, 3) * 0.01, 0.05, random.nextBetween(-3, 3) * 0.01);
        }
    }
}
