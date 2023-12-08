package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.custom.*;
import com.bajookie.biotech.item.custom.AncientStoneSwordItem;
import com.bajookie.biotech.item.custom.MidasHammerItem;
import com.bajookie.biotech.item.custom.PortalRingItem;
import com.bajookie.biotech.item.custom.VitalityPumpItem;
import com.bajookie.biotech.item.custom.RadiantLotusItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.LinkedList;

import static com.bajookie.biotech.BioTech.MOD_ID;

@SuppressWarnings("unused")
public class ModItems {
    public static final LinkedList<Item> registeredModItems = new LinkedList<>();

    public static final ToolMaterial ARTIFACT_BASE_MATERIAL = ToolMaterialBuilder.copyOf(ToolMaterials.IRON).repairIngredient(null).durability(0);

    public static final Item RADIANT_LOTUS = registerItem("radiant_lotus_item", new RadiantLotusItem(new FabricItemSettings().maxCount(1)));
    public static final Item EXPLORER_FRUIT = registerItem("explorers_fruit", new Item(new FabricItemSettings().food(ModFoodComponents.EXPLORERS_FRUIT).maxCount(16)));
    public static final Item ANCIENT_STONE_SWORD = registerItem("ancient_stone_sword", new AncientStoneSwordItem(10, -1.5f, 5, 0, +0.50f, -0.25f));
    public static final Item SHINY_ANCIENT_STONE_SWORD = registerItem("shiny_ancient_stone_sword", new AncientStoneSwordItem(15, -1.5f, 5, 2, +1f, -0.25f));
    public static final Item MIDAS_HAMMER = registerItem("midas_hammer", new MidasHammerItem(ARTIFACT_BASE_MATERIAL, 10, -2f, new FabricItemSettings().maxCount(1).maxDamage(120)));
    public static final Item VITALITY_PUMP = registerItem("vitality_pump", new VitalityPumpItem(2));
    public static final Item PORTAL_RING = registerItem("portal_ring", new PortalRingItem());
    public static final Item GALE_CORE = registerItem("gale_core", new GaleCoreItem());
    public static final Item SILENT_FIRE = registerItem("silent_fire", new AuraItem(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC), StatusEffects.GLOWING, 25, 0));
    public static final Item FIRE_SNAP = registerItem("fire_snap", new FireSnapItem());
    public static final Item DAGON = registerItem("dagon_item", new DagonItem());
    public static final Item POTION_MIRAGE = registerItem("potion_mirage_item", new Item(new FabricItemSettings().maxCount(1)));


    // Register methods
    public static Item registerItem(String name, Item item) {
        registeredModItems.push(item);
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, name), item);
    }

    public static void registerModItems() {

        BioTech.LOGGER.info("Register Items for:" + MOD_ID);
    }
}
