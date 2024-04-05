package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.ModEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class ModEntityRenderers {


    public static void init() {
        EntityRendererRegistry.register(ModEntities.RAID_TOTEM_ENTITY, RaidTotemRenderer::new);
        EntityRendererRegistry.register(ModEntities.SPIRIT_ENTITY_KEY, SpiritItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SECOND_SUN_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.VACUUM_PROJECTILE_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.TELEPORT_EYE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.AIR_SWEEPER_PROJECTILE_ENTITY_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.ORB_OF_ANNIHILATION_ENTITY_TYPE, ModModelLayers.FarFlyingItemRenderer::new);
        EntityRendererRegistry.register(ModEntities.ICICLE_PROJECTILE_ENTITY_TYPE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOLTEN_CHAMBER_SHOT_PROJECTILE_ENTITY_TYPE, GlowingFlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.PELLET_ENTITY_TYPE, PelletProjectileRenderer::new);
        EntityRendererRegistry.register(ModEntities.ELDERMAN_ENTITY, EldermanRenderer::new);
        EntityRendererRegistry.register(ModEntities.STAR_ARROW_ENTITY, ArrowEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.GALE_ARROW_ENTITY, GaleArrowEntityRenderer::new);
    }

    private static class GlowingFlyingItemEntityRenderer extends FlyingItemEntityRenderer {

        public GlowingFlyingItemEntityRenderer(EntityRendererFactory.Context ctx, float scale, boolean lit) {
            super(ctx, scale, lit);
        }

        public GlowingFlyingItemEntityRenderer(EntityRendererFactory.Context context) {
            super(context);
        }

        @Override
        public void render(Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, 0xF00000);
        }
    }

}
