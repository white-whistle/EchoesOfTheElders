package com.bajookie.echoes_of_the_elders.block.custom.entity;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModBlockEntities {
    public static final BlockEntityType<SleepingBagBlockEntity> SLEEPING_BAG_BLOCK_ENTITY_BLOCK_ENTITY_TYPE =
            Registry.register(Registries.BLOCK_ENTITY_TYPE,new Identifier(MOD_ID,"sleeping_bag_block_entity_type"),
                    FabricBlockEntityTypeBuilder.create(SleepingBagBlockEntity::new, ModBlocks.SLEEPING_BAG_BLOCK).build());
    public static void registerBlockEntities(){
        EOTE.LOGGER.info("creating block entities for --->"+MOD_ID);
    }
}
