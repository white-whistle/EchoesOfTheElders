package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.models.MinigunModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer RAID_TOTEM_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "flower_defense_entity"), "main");
    public static final EntityModelLayer SPIRIT_ENTITY_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "spirit_entity"), "main");

    public static final EntityModelLayer ELDERMAN_ENTITY_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "elderman_entity"), "main");
    public static final EntityModelLayer ZOMBEE_ENTITY_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "zombee_entity"), "main");
    public static final EntityModelLayer MAGMA_BULLET_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "magma_bullet_entity"), "main");
    public static final EntityModelLayer PELLET_PROJECTILE_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "pellet_entity"), "main");
    public static final EntityModelLayer MINIGUN_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "minigun_layer"), "main");

    /**
     * Register Model Layers here:
     */
    public static void registerModMobLayers() {
        EntityModelLayerRegistry.registerModelLayer(RAID_TOTEM_LAYER, RaidTotemModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.RAID_TOTEM_ENTITY, RaidTotemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SPIRIT_ENTITY_LAYER, SpiritItemEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SPIRIT_ENTITY_KEY, SpiritItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SECOND_SUN_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.VACUUM_PROJECTILE_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TELEPORT_EYE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIR_SWEEPER_PROJECTILE_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TV_ARROW_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICICLE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MAGMA_BULLET_LAYER,MagmaBulletModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MAGMA_BULLET_ENTITY_TYPE, MagmaBulletEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PELLET_PROJECTILE_LAYER,PelletProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PELLET_ENTITY_TYPE, PelletProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ELDERMAN_ENTITY_LAYER, EldermanModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ELDERMAN_ENTITY, EldermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAR_ARROW_ENTITY, ArrowEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ZOMBEE_ENTITY_LAYER, ZomBeeModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ZOMBEE_ENTITY_TYPE, ZomBeeRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MINIGUN_LAYER, MinigunModel::getTexturedModelData);}
}
