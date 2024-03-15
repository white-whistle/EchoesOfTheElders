package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.client.render.EffectLayer;
import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class MonolookEyeFeatureRenderer extends EyesFeatureRenderer<MonolookEntity, MonolookEntityModel> {

    private static final RenderLayer EYE = RenderLayer.getEyes(new Identifier(MOD_ID,"textures/entity/monolook/monolook_eye.png"));

    public MonolookEyeFeatureRenderer(FeatureRendererContext<MonolookEntity, MonolookEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, MonolookEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getEyesTexture());
        float progress = (float)entity.getDataTracker().get(MonolookEntity.SHOOT_PROGRESS)/10;
        ((Model)this.getContextModel()).render(matrices, vertexConsumer, 0xF00000, OverlayTexture.DEFAULT_UV, progress, progress, progress, 1f);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return EYE;
    }
}
