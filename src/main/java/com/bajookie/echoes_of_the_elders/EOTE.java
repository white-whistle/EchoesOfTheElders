package com.bajookie.echoes_of_the_elders;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.ModItemGroups;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.util.ModLootTablesModifiers;
import com.bajookie.echoes_of_the_elders.world.gen.ModWorldGeneration;
import com.bajookie.echoes_of_the_elders.world.tree.ModTrunkPlacerTypes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EOTE implements ModInitializer {
    public static final String MOD_ID = "echoes_of_the_elders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");
        ModItems.registerModItems();
        ModBlocks.registerModBlocks();
        ModItemGroups.registerGroups();
        ModLootTablesModifiers.modifyLootTables();
        ModWorldGeneration.generateModWorldGen();
        ModEffects.registerEffects();

        ModEntities.registerMobAttributes();
        ModParticles.registerParticles();
        ModSounds.registerSounds();
        ModTrunkPlacerTypes.register();
        ServerNetworking.init();
    }


}