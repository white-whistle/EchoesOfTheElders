package com.bajookie.biotech.entity.client;

import com.bajookie.biotech.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer FLOWER_DEFENSE_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID,"flower_defense_entity"),"main");

    /**
     * Register Model Layers here:
     */
    public static void registerModMobLayers(){
        EntityModelLayerRegistry.registerModelLayer(FLOWER_DEFENSE_LAYER,FlowerDefenseModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.FLOWER_DEFENSE_ENTITY,FlowerDefenseRenderer::new);
    }
}
