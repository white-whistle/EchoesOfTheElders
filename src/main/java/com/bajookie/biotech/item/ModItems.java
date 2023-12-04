package com.bajookie.biotech.item;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.custom.AncientStoneSwordItem;
import com.bajookie.biotech.item.custom.MidasHammerItem;
import com.bajookie.biotech.item.custom.VitalityPumpItem;
import com.bajookie.biotech.item.custom.RadiantLotusItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModItems {
    public static final ToolMaterial ARTIFACT_BASE_MATERIAL = ToolMaterialBuilder.copyOf(ToolMaterials.IRON).repairIngredient(null).durability(0);
    public static final Item RADIANT_LOTUS = registerItem("radiant_lotus_item",new RadiantLotusItem(new FabricItemSettings().maxCount(1)));

    public static final Item EXPLORER_FRUIT = registerItem("explorers_fruit",new Item(new FabricItemSettings().food(ModFoodComponents.EXPLORERS_FRUIT).maxCount(16)));
    public static final Item ANCIENT_STONE_SWORD = registerItem("ancient_stone_sword",new AncientStoneSwordItem(10, -1.5f, 5, 0, +0.50f, -0.25f));
    public static final Item SHINY_ANCIENT_STONE_SWORD = registerItem("shiny_ancient_stone_sword",new AncientStoneSwordItem(15, -1.5f, 5, 2, +1f, -0.25f));
    public static final Item MIDAS_HAMMER = registerItem("midas_hammer",new MidasHammerItem(ARTIFACT_BASE_MATERIAL, 10, -2f, new FabricItemSettings().maxCount(1).maxDamage(120)));
    public static final Item VITALITY_PUMP = registerItem("vitality_pump",new VitalityPumpItem(2));



    //Register methods
    public static Item registerItem(String name,Item item){
        return Registry.register(Registries.ITEM,new Identifier(MOD_ID,name),item);
    }
    public static void registerModItems(){

        BioTech.LOGGER.info("Register Items for:"+MOD_ID);
    }
}
