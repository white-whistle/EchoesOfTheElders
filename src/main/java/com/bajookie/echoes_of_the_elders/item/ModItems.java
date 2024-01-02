package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.custom.*;
import com.bajookie.echoes_of_the_elders.item.entites.ChainLightningItem;
import com.bajookie.echoes_of_the_elders.item.entites.SecondSunItem;
import com.bajookie.echoes_of_the_elders.item.entites.TeleportEyeItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedList;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModItems {
    public static final LinkedList<Item> registeredModItems = new LinkedList<>();

    public static final ToolMaterial ARTIFACT_BASE_MATERIAL = ToolMaterialBuilder.copyOf(ToolMaterials.IRON).repairIngredient(null).durability(0);

    public static final Item RADIANT_LOTUS = registerItem("radiant_lotus_item", new RadiantLotusItem());
    public static final Item EXPLORER_FRUIT = registerItem("explorers_fruit", new Item(new FabricItemSettings().food(ModFoodComponents.EXPLORERS_FRUIT).maxCount(16)));
    public static final Item ANCIENT_STONE_SWORD = registerItem("ancient_stone_sword", new AncientStoneSwordItem(10, -1.5f, 5, 0, +0.50f, -0.25f));
    public static final Item SHINY_ANCIENT_STONE_SWORD = registerItem("shiny_ancient_stone_sword", new AncientStoneSwordItem(15, -1.5f, 5, 2, +1f, -0.25f));
    public static final Item MIDAS_HAMMER = registerItem("midas_hammer", new MidasHammerItem());
    public static final Item VITALITY_PUMP = registerItem("vitality_pump", new VitalityPumpItem());
    public static final Item PORTAL_RING = registerItem("portal_ring", new PortalRingItem());
    public static final Item GALE_CORE = registerItem("gale_core", new GaleCoreItem());
    public static final Item SILENT_FIRE = registerItem("silent_fire", new AuraItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), StatusEffects.GLOWING, 25, 0));
    public static final Item SCORCHERS_MITTS = registerItem("scorchers_mitts", new ScorchersMittsItem());
    public static final Item DOOMSTICK_ITEM = registerItem("doomstick_item", new DoomstickItem());
    public static final PotionMirageItem POTION_MIRAGE = registerItem("potion_mirage_item", new PotionMirageItem());
    public static final Item QUICKENING_BAND = registerItem("quickening_band", new QuickeningBand());
    public static final Item OLD_KEY = registerItem("old_key_item", new OldKeyItem());
    public static final Item WITHER_SCALES_ITEM = registerItem("wither_scales_item", new WitherScalesItem());
    public static final Item SECOND_SUN_ITEM = registerItem("second_sun_item", new SecondSunItem(new FabricItemSettings().maxCount(16)));
    public static final Item CHAIN_LIGHTNING_ITEM = registerItem("chain_lightning_item", new ChainLightningItem(new FabricItemSettings().maxCount(16)));
    public static final Item ECHOING_SWORD = registerItem("echoing_sword_item", new EchoingSword());
    public static final Item GODSLAYER = registerItem("godslayer", new GodslayerItem());
    public static final GunheelsItem GUNHEELS = registerItem("gunheels", new GunheelsItem());
    public static final Item REALITY_PICK = registerItem("reality_pick", new RealityPick());
    public static final Item TELEPORT_EYE_ITEM = registerItem("teleport_eye", new TeleportEyeItem());

    // spawn eggs
    public static final Item SPIRIT_SPAWN_EGG = registerItem("spirit_spawn_egg", new RainbowSpawnEgg(ModEntities.SPIRIT_ENTITY, 0xb4d4e1, new FabricItemSettings()));
    public static final Item ELDERMAN_SPAWN_EGG = registerItem("elderman_spawn_egg", new SpawnEggItem(ModEntities.ELDERMAN_ENTITY, 0x341061,0x3c3940, new FabricItemSettings()));


    // Register methods
    public static <T extends Item> T registerItem(String name, T item) {
        registeredModItems.push(item);
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {

        EOTE.LOGGER.info("Register Items for:" + MOD_ID);
    }
}
