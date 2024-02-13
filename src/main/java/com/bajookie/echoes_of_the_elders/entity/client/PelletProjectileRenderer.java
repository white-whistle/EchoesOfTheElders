package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.MagmaBullet;
import com.bajookie.echoes_of_the_elders.entity.custom.PelletProjectile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class PelletProjectileRenderer extends EntityRenderer<PelletProjectile> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/magma_bullet.png");
    protected PelletProjectileModel model;
    public PelletProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new PelletProjectileModel(context.getPart(ModModelLayers.PELLET_PROJECTILE_LAYER));
    }

    @Override
    public Identifier getTexture(PelletProjectile entity) {
        return TEXTURE;
    }

    @Override
    public void render(PelletProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        //this.model.render(matrices,vertexConsumers.getBuffer(this.model.getLayer(TEXTURE)),light, OverlayTexture.DEFAULT_UV,1f,1f,1f,0f);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
