package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.GaleArrowProjectile;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GaleArrowEntityRenderer extends ProjectileEntityRenderer<GaleArrowProjectile> {
    public static final Identifier TEXTURE = new ModIdentifier("textures/entity/projectiles/gale_arrow.png");
    public static final Identifier TEXTURE_MAXED = new ModIdentifier("textures/entity/projectiles/gale_arrow_01.png");

    public GaleArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(GaleArrowProjectile arrowEntity) {
        return arrowEntity.isMaxed() ? TEXTURE_MAXED : TEXTURE;
    }

    @Override
    public void render(GaleArrowProjectile persistentProjectileEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, 0xF00000);
    }
}
