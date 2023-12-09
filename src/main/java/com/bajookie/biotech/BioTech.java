package com.bajookie.biotech;

import com.bajookie.biotech.block.ModBlocks;
import com.bajookie.biotech.entity.ModEntities;
import com.bajookie.biotech.item.ModItemGroups;
import com.bajookie.biotech.item.ModItems;
import com.bajookie.biotech.util.ModLootTablesModifiers;
import com.bajookie.biotech.util.ModWorldGenerator;
import com.bajookie.biotech.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BioTech implements ModInitializer {
	public static final String MOD_ID = "biotech";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerGroups();
		ModLootTablesModifiers.modifyLootTables();
		ModWorldGeneration.generateModWorldGen();

		ModEntities.registerMobAttributes();
	}


}