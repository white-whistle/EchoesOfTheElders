package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import com.bajookie.echoes_of_the_elders.item.models.MinigunModel;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModModelLayers {
    public static final EntityModelLayer RAID_TOTEM_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "flower_defense_entity"), "main");
    public static final EntityModelLayer SPIRIT_ENTITY_LAYER =
            new EntityModelLayer(new Identifier(MOD_ID, "spirit_entity"), "main");

    public static final EntityModelLayer ELDERMAN_ENTITY_LAYER = new EntityModelLayer(new ModIdentifier("elderman_entity"), "main");
    public static final EntityModelLayer ZOMBEE_ENTITY_LAYER = new EntityModelLayer(new ModIdentifier("zombee_entity"), "main");
    public static final EntityModelLayer MAGMA_BULLET_LAYER = new EntityModelLayer(new ModIdentifier("magma_bullet_entity"), "main");
    public static final EntityModelLayer HAT_BRIM = new EntityModelLayer(new ModIdentifier("hat_brim"), "main");
    public static final EntityModelLayer HALO_LAYER = new EntityModelLayer(new ModIdentifier("halo"), "main");
    public static final EntityModelLayer PELLET_PROJECTILE_LAYER = new EntityModelLayer(new ModIdentifier("pellet_entity"), "main");
    public static final EntityModelLayer MINIGUN_LAYER = new EntityModelLayer(new ModIdentifier("minigun_layer"), "main");
    public static final EntityModelLayer MONOLOOK_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "monolook_entity"), "main");

    static class FarFlyingItemRenderer extends FlyingItemEntityRenderer {
        public FarFlyingItemRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
            super(ctx, scale, lit);
        }

        public FarFlyingItemRenderer(EntityRendererFactory.Context context) {
            super(context);
        }

        @Override
        public boolean shouldRender(Entity entity, Frustum frustum, double x, double y, double z) {
            return true;
        }
    }

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
        EntityRendererRegistry.register(ModEntities.ORB_OF_ANNIHILATION_ENTITY_TYPE, FarFlyingItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICICLE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MAGMA_BULLET_LAYER, MagmaBulletModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MAGMA_BULLET_ENTITY_TYPE, MagmaBulletEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PELLET_PROJECTILE_LAYER, PelletProjectileModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.PELLET_ENTITY_TYPE, PelletProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ELDERMAN_ENTITY_LAYER, EldermanModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ELDERMAN_ENTITY, EldermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAR_ARROW_ENTITY, ArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GALE_ARROW_ENTITY, GaleArrowEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ZOMBEE_ENTITY_LAYER, ZomBeeModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.ZOMBEE_ENTITY_TYPE, ZomBeeRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MONOLOOK_LAYER, MonolookEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MONOLOOK_ENTITY_TYPE, MonolookEntityRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MINIGUN_LAYER, MinigunModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(HAT_BRIM, HaloModel.getTexturedModelData(0, 0, 0));
        EntityModelLayerRegistry.registerModelLayer(HALO_LAYER, HaloModel.getTexturedModelData((float) (Math.PI / 2f), 0, 0));

    }
}
