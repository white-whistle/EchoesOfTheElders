package com.bajookie.echoes_of_the_elders.block.custom.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class SleepingBagBlockEntity extends BlockEntity {
    private final DyeColor color = DyeColor.RED;
    public SleepingBagBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, pos, state);
    }
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public DyeColor getColor() {
        return this.color;
    }
}
