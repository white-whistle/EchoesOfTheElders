package com.bajookie.echoes_of_the_elders.entity.client;

import com.bajookie.echoes_of_the_elders.entity.custom.RaidTotemEntity;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class RaidTotemRenderer extends MobEntityRenderer<RaidTotemEntity, RaidTotemModel> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/raid_totem.png");
    private final ItemRenderer itemRenderer;

    public RaidTotemRenderer(EntityRendererFactory.Context context) {
        super(context, new RaidTotemModel(context.getPart(ModModelLayers.RAID_TOTEM_LAYER)), 0.6f); // float is for the shadow
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(RaidTotemEntity entity) {
        return TEXTURE;
    }

    @Override
    public boolean shouldRender(RaidTotemEntity mobEntity, Frustum frustum, double d, double e, double f) {
        var c = ModCapabilities.RAID_OBJECTIVE.tryGetCapability(mobEntity);
        if (c != null) {
            if (c.active) return true;
        }

        return super.shouldRender(mobEntity, frustum, d, e, f);
    }

    @Override
    public void render(RaidTotemEntity mobEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }
        var age = mobEntity.age + tickDelta;
        var offsetY = (float) (Math.sin((age / 100) * Math.PI * 2)) * 0.5f;

        matrixStack.push();
        matrixStack.translate(0, offsetY, 0);
        super.render(mobEntity, yaw, tickDelta, matrixStack, vertexConsumerProvider, i);


        long time = mobEntity.getWorld().getTime();

        ModCapabilities.RAID_OBJECTIVE.use(mobEntity, o -> {
            if (mobEntity.isDead()) return;
            
            if (o.active) {
                matrixStack.push();
                matrixStack.translate(-0.5, -0.5, -0.5);
                BeaconBlockEntityRenderer.renderBeam(matrixStack, vertexConsumerProvider, BeaconBlockEntityRenderer.BEAM_TEXTURE, yaw, 1f, time, 2, 256, DyeColor.MAGENTA.getColorComponents(), 0.15f, 0.175f);
                matrixStack.pop();
            }

            if (o.items.size() > 0) {
                matrixStack.push();
                var j = 0;
                var rad = 1;
                for (var stack : o.items) {
                    matrixStack.push();
                    var deg = ((j / (float) o.items.size()) * Math.PI * 2) + ((time + tickDelta) / 20f);
                    var c = Math.cos(deg);
                    var s = Math.sin(deg);
                    var x = c * rad;
                    var z = s * rad;
                    var y = s * c * rad * 0.2;

                    matrixStack.translate(x, y + 2.5, z);
                    matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(((time + tickDelta) / 200f) * 360));

                    itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, i, OverlayTexture.DEFAULT_UV, matrixStack, vertexConsumerProvider, mobEntity.getWorld(), 0);
                    matrixStack.pop();
                }
                matrixStack.pop();
            }
        });

        matrixStack.pop();

    }
}
