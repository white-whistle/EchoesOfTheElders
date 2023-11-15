package com.bajookie.biotech;

import com.bajookie.biotech.item.ModItemGroups;
import com.bajookie.biotech.item.ModItems;
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
		ModItemGroups.registerGroups();
	}
}