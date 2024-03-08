package com.bajookie.echoes_of_the_elders.block.custom;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;

public class TotemSpawnBlock extends Block {
    public TotemSpawnBlock() {
        super(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).strength(-1.0f, 3600000.0f).dropsNothing());
    }
}
