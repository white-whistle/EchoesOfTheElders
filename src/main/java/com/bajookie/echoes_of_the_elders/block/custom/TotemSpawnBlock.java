package com.bajookie.echoes_of_the_elders.block.custom;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class TotemSpawnBlock extends Block implements IPrismActionable {
    public TotemSpawnBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).strength(-1.0f, 3600000.0f).dropsNothing());
    }

    @Override
    public boolean onPrism(ItemStack itemStack, PlayerEntity user, World world, BlockPos blockPos, Direction side) {
        if (side == Direction.UP) {
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

            if (!world.isClient) {
                var totem = ModEntities.RAID_TOTEM_ENTITY.create(world);
                if (totem != null) {
                    var x = blockPos.getX() + 0.5;
                    var y = blockPos.getY() + 0.5;
                    var z = blockPos.getZ() + 0.5;

                    totem.setPos(x, y, z);
                    world.spawnEntity(totem);
                    world.playSound(x, y, z, SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1f, 0.5f, true);
                }
            }

            return true;
        }

        return false;
    }
}
