package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.MoltenChamberShotProjectile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class MagmaBulletEntityRenderer extends EntityRenderer<MoltenChamberShotProjectile> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/magma_bullet.png");
    protected MagmaBulletModel model;

    public MagmaBulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new MagmaBulletModel(context.getPart(ModModelLayers.MAGMA_BULLET_LAYER));
    }

    @Override
    public Identifier getTexture(MoltenChamberShotProjectile entity) {
        return TEXTURE;
    }

    @Override
    public void render(MoltenChamberShotProjectile entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
