package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedList;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@SuppressWarnings("unused")
public class ModItems {
    public static final LinkedList<Item> registeredModItems = new LinkedList<>();

    public static final ToolMaterial ARTIFACT_BASE_MATERIAL = ToolMaterialBuilder.copyOf(ToolMaterials.IRON).repairIngredient(null).durability(0);

    public static final Item RADIANT_LOTUS = registerItem("radiant_lotus_item", new RadiantLotusItem());
    public static final Item EXPLORER_FRUIT = registerItem("explorers_fruit", new Item(new FabricItemSettings().food(ModFoodComponents.EXPLORERS_FRUIT).maxCount(16)));
    // public static final Item ANCIENT_STONE_SWORD = registerItem("ancient_stone_sword", new AncientStoneSwordItem(10, -1.5f, 5, 0, +0.50f, -0.25f));
    // public static final Item SHINY_ANCIENT_STONE_SWORD = registerItem("shiny_ancient_stone_sword", new AncientStoneSwordItem(15, -1.5f, 5, 2, +1f, -0.25f));
    public static final Item MIDAS_HAMMER = registerItem("midas_hammer", new MidasHammerItem());
    public static final Item VITALITY_PUMP = registerItem("vitality_pump", new VitalityPumpItem());
    public static final Item PORTAL_RING = registerItem("portal_ring", new PortalRingItem());
    public static final Item GALE_QUIVER = registerItem("gale_quiver", new GaleQuiverItem());
    public static final Item GALE_ARROW = registerItem("gale_arrow", new GaleQuiverItem.Arrow());
    // public static final Item SILENT_FIRE = registerItem("silent_fire", new AuraItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), StatusEffects.GLOWING, 25, 0));
    public static final Item SCORCHERS_MITTS = registerItem("scorchers_mitts", new ScorchersMittsItem());
    public static final Item DOOMSTICK_ITEM = registerItem("doomstick_item", new DoomstickItem());
    public static final PotionMirageItem POTION_MIRAGE = registerItem("potion_mirage_item", new PotionMirageItem());
    public static final Item QUICKENING_BAND = registerItem("quickening_band", new QuickeningBand());
    public static final Item OLD_KEY = registerItem("old_key_item", new OldKeyItem());
    public static final WithersBulwark WITHERS_BULWARK = registerItem("wither_scales_item", new WithersBulwark());
    public static final Item ORB_OF_LIGHTNING = registerItem("orb_of_lightning", new OrbOfLightning());
    public static final Item ECHOING_SWORD = registerItem("echoing_sword", new EchoingSword());
    public static final Item GODSLAYER = registerItem("godslayer", new GodslayerItem());
    public static final GunheelsItem GUNHEELS = registerItem("gunheels", new GunheelsItem());
    public static final Item REALITY_PICK = registerItem("reality_pick", new RealityPick());
    public static final Item ELDER_PRISM = registerItem("elder_prism", new ElderPrismItem());
    public static final Item TIME_GLYPH = registerItem("time_token", new TimeGlyph());
    public static final Item WTF_TOKEN = registerItem("wtf_token", new WTFGlyph());
    // public static final Item STASIS_STOPWATCH = registerItem("stasis_stopwatch", new StasisStopwatch());
    // public static final Item STAT_FRUIT_HP = registerItem("stat_fruit_hp", new StatFruit(StatFruit.Type.HP));
    public static final Item RAID_DEBUG_ITEM = registerItem("raid_debug_item", new RaidDebugItem());
    public static final Item SPARKING_MITTS = registerItem("sparking_mitts", new SparkingMitts());
    public static final Item SKY_WARD = registerItem("sky_ward", new SkyWard());
    public static final Item SKY_WARD_SHOT = registerItem("sky_ward_shot", new Item(new FabricItemSettings()));
    public static final Item POCKET_GALAXY = registerItem("pocket_galaxy", new PocketGalaxy());
    public static final Item CORRUPTED_KEY = registerItem("corrupted_key", new Item(new FabricItemSettings()));
    public static final Item MOLTEN_CHAMBER = registerItem("molten_chamber", new MoltenChamber());
    public static final Item MOLTEN_CHAMBER_SHOT = registerItem("molten_chamber_shot", new Item(new FabricItemSettings()));
    public static final Item ORB_OF_ANNIHILATION = registerItem("orb_of_annihilation", new OrbOfAnnihilation());
    public static final Item PANDORAS_BAG = registerItem("pandoras_bag", new PandorasBag());
    // public static final Item EARTH_SPIKE_RELIC = registerItem("earth_spike_relic", new EarthSpikeRelic()); //ignore for rls
    public static final Item ICICLE_RELIC = registerItem("icicle_staff", new IcicleStaff());
    // public static final Item ANCIENT_MINIGUN = registerItem("ancient_minigun", new AncientMinigun()); //ignore for rls
    public static final Item MASK_OF_DAWN = registerItem("gangway", new MaskOfDawn());
    // public static final Item MONOLOOK_SPAWN = registerItem("monolook_spawn", new MonolookSpawn());
    public static final Item COWPLATE = registerItem("cowplate", new Cowplate());
    public static final Item HARELEAP_STRIDERS = registerItem("hareleap_striders", new HareleapStriders());
    public static final Item ATLAS_GREAVES = registerItem("atlas_greaves", new AtlasGreaves());
    public static final Item HEAT_STONE = registerItem("heat_stone", new HeatStone());
    public static final Item STEPPING_STONE = registerItem("stepping_stone", new SteppingStone());
    public static final Item STARFALL_BOW = registerItem("starfall_bow", new StarfallBow());
    public static final Item SPIRAL_SWORD = registerItem("spiral_sword", new SpiralSword());

    // spawn eggs
    public static final Item SPIRIT_SPAWN_EGG = registerItem("spirit_spawn_egg", new RainbowSpawnEgg(ModEntities.SPIRIT_ENTITY_KEY, 0xb4d4e1, new FabricItemSettings()));
    public static final Item ELDERMAN_SPAWN_EGG = registerItem("elderman_spawn_egg", new SpawnEggItem(ModEntities.ELDERMAN_ENTITY, 0x341061, 0x3c3940, new FabricItemSettings()));
    public static final Item ZOMBEE_SPAWN_EGG = registerItem("zombee_spawn_egg", new SpawnEggItem(ModEntities.ZOMBEE_ENTITY_TYPE, 0x4a854e, 0x3c3940, new FabricItemSettings()));
    public static final Item RAID_TOTEM_EGG = registerItem("raid_totem_egg", new SpawnEggItem(ModEntities.RAID_TOTEM_ENTITY, 0x8b778c, 0x403341, new FabricItemSettings()));

    public static final Item MAGIC_HAMMER = registerItem("magic_hammer", new MagicHammer());
    public static final Item ARTIFACT_HAMMER = registerItem("artifact_hammer", new ArtifactHammer());
    public static final Item WTF_HAMMER = registerItem("wtf_hammer", new WTFHammer());
    public static final Item VOID_RAY = registerItem("void_ray", new VoidRayItem());

    public static final Item RELICOMICON = registerItem("relicomicon",new Relicomicon());

    // Register methods
    public static <T extends Item> T registerItem(String name, T item) {
        registeredModItems.push(item);
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {

        EOTE.LOGGER.info("Register Items for:" + MOD_ID);
    }
}
