package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.session.report.ReporterEnvironment;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.Vibrations;

public class SunRuneBlock extends Block {
    public SunRuneBlock() {
        super(FabricBlockSettings.copyOf(Blocks.SHROOMLIGHT).luminance(10).ticksRandomly());
    }


    @Override
    public boolean hasRandomTicks(BlockState state) {
        return true;
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int x = random.nextBetween(-3,3);
        int y = random.nextBetween(-3,3);
        int z = random.nextBetween(-3,3);
        world.spawnParticles(ParticleTypes.GLOW,pos.getX()+x,pos.getY()+y,pos.getZ()+z,1,0,0.1,0,0.1);
        super.randomTick(state, world, pos, random);
    }
}
