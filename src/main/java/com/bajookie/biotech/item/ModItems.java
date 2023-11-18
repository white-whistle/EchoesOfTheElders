package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.custom.AncientStoneSwordItem;
import com.bajookie.biotech.item.custom.MortarAndPestleItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

@SuppressWarnings("SpellCheckingInspection")
public class ModItems {
    //items list
//    public static final Item FLUID_CELL = registerItem("fluid_cell",new Item(new FabricItemSettings()));
//    public static final Item SULFUR = registerItem("sulfur",new Item(new FabricItemSettings()));
//    public static final Item ORANGE = registerItem("orange",new Item(new FabricItemSettings().food(ModFoodComponents.ORANGE)));
//    public static final Item LEMON = registerItem("lemon",new Item(new FabricItemSettings().food(ModFoodComponents.LEMON)));
//    public static final Item CITRUSJUICE = registerItem("bottle_of_citrus_juice",new Item(new FabricItemSettings().food(ModFoodComponents.CITRUS_JUICE)));
//    public static final Item UNFERMENTEDBIOMASS = registerItem("unfermented_biomass",new Item(new FabricItemSettings()));
//    public static final Item FERMENTEDBIOMASS = registerItem("fermented_biomass",new Item(new FabricItemSettings()));
//    public static final Item MORTAR_AND_PESTLE = registerItem("mortar_and_pestle",new MortarAndPestleItem(new FabricItemSettings().maxDamage(100)));
    // legacy ^
    public static final ToolMaterial ARTIFACT_BASE_MATERIAL = ToolMaterialBuilder.copyOf(ToolMaterials.IRON).repairIngredient(null).durability(0);

    public static final Item EXPLORER_FRUIT = registerItem("explorers_fruit",new Item(new FabricItemSettings().food(ModFoodComponents.EXPLORERS_FRUIT).maxCount(16)));
    public static final Item MINERS_FRUIT = registerItem("miners_fruit",new Item(new FabricItemSettings().food(ModFoodComponents.MINERS_FRUIT).maxCount(16)));
    public static final Item ANCIENT_STONE_SWORD = registerItem("ancient_stone_sword",new AncientStoneSwordItem(10, -1.5f, 5, 0, +0.50f, -0.25f));
    public static final Item SHINY_ANCIENT_STONE_SWORD = registerItem("shiny_ancient_stone_sword",new AncientStoneSwordItem(15, -1.5f, 5, 2, +1f, -0.25f));



    //Register methods
    public static Item registerItem(String name,Item item){
        return Registry.register(Registries.ITEM,new Identifier(MOD_ID,name),item);
    }
    public static void registerModItems(){

        // FuelRegistry.INSTANCE.add(FERMENTEDBIOMASS,6400);
        BioTech.LOGGER.info("Register Items for:"+MOD_ID);
    }
}
