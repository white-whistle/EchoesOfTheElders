package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.block.custom.entity.ModBlockEntities;
import com.bajookie.echoes_of_the_elders.block.custom.entity.SleepingBagEntityRenderer;
import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer FLOWER_DEFENSE_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "flower_defense_entity"), "main");
    public static final EntityModelLayer SPIRIT_ENTITY_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "spirit_entity"), "main");

    public static final EntityModelLayer ELDERMAN_ENTITY_LAYER = new EntityModelLayer(new Identifier(MOD_ID,"elderman_entity"),"main");

    public static final EntityModelLayer SLEEPING_BAG_HEAD = new EntityModelLayer(new Identifier(MOD_ID,"sleeping_bag_head"),"head_sleep");
    public static final EntityModelLayer SLEEPING_BAG_FOOT =new EntityModelLayer(new Identifier(MOD_ID,"sleeping_bag_foot"),"foot_sleep");

    /**
     * Register Model Layers here:
     */
    public static void registerModMobLayers() {
        EntityModelLayerRegistry.registerModelLayer(SLEEPING_BAG_HEAD,SleepingBagEntityRenderer::getHeadTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SLEEPING_BAG_FOOT,SleepingBagEntityRenderer::getFootTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FLOWER_DEFENSE_LAYER, FlowerDefenseModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.FLOWER_DEFENSE_ENTITY, FlowerDefenseRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SPIRIT_ENTITY_LAYER, SpiritItemEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SPIRIT_ENTITY_KEY, SpiritItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SECOND_SUN_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TELEPORT_EYE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ELDERMAN_ENTITY_LAYER,EldermanModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ELDERMAN_ENTITY, EldermanRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.SLEEPING_BAG_BLOCK_ENTITY_BLOCK_ENTITY_TYPE, SleepingBagEntityRenderer::new);
    }
}
