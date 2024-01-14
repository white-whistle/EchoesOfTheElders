package com.bajookie.echoes_of_the_elders.villager;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModVillagers {
    public static final RegistryKey<PointOfInterestType> RELIC_POI_KEY = registerPoiKey("relicpoi");
    public static final RegistryKey<PointOfInterestType> REWARD_POI_KEY = registerPoiKey("reward_bag_poi");

    public static final PointOfInterestType RELIC_POI = registerPoi("relicpoi", ModBlocks.ARTIFACT_VAULT);
    public static final PointOfInterestType REWARD_POI = registerPoi("reward_bag_poi", Blocks.DIAMOND_BLOCK); // needed to be replaced

    public static final VillagerProfession RELIC_MASTER = registerProfession("relic_master", RELIC_POI_KEY);
    public static final VillagerProfession REWARD_MASTER = registerProfession("reward_bag_trader", REWARD_POI_KEY);


    public static void registerVillagers() {
        EOTE.LOGGER.info("registering villagers");
    }

    public static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(MOD_ID, name),
                new VillagerProfession(name, (entry) -> entry.matchesKey(type), (entry) -> entry.matchesKey(type),
                        ImmutableSet.of(), ImmutableSet.of(), SoundEvents.ENTITY_VILLAGER_WORK_ARMORER));
    }

    public static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(MOD_ID, name), 1, 1, block);
    }

    public static RegistryKey<PointOfInterestType> registerPoiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, new Identifier(MOD_ID, name));
    }

    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(REWARD_MASTER, 1,
                factories -> {
                    factories.add(((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.ELDER_PRISM,8),
                            new ItemStack(ModItems.OLD_KEY,1),2,6,0.08f
                    )));
                }
        );
        TradeOfferHelper.registerVillagerOffers(RELIC_MASTER, 1,
                factories -> {
            factories.add(((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD,16),
                    new ItemStack(ModItems.OLD_KEY,1),2,6,0.08f
            )));
                }
        );
    }

}