package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.client.outline.ItemOutlineRenderer;
import com.bajookie.echoes_of_the_elders.client.outline.OutlineQuad;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.random.Random;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Unique
    private final List<ModelTransformationMode> HELD_RENDER_MODES = Arrays.asList(
            ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
            ModelTransformationMode.FIRST_PERSON_RIGHT_HAND,
            ModelTransformationMode.THIRD_PERSON_LEFT_HAND,
            ModelTransformationMode.THIRD_PERSON_RIGHT_HAND
    );

    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useCustomModels(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        boolean isHeld = HELD_RENDER_MODES.contains(renderMode);

        if (stack.isOf(ModItems.ANCIENT_STONE_SWORD) && isHeld) {
            return getCustomItemModel("ancient_stone_sword_3d");
        }
        if (stack.isOf(ModItems.SHINY_ANCIENT_STONE_SWORD) && isHeld) {
            return getCustomItemModel("shiny_ancient_stone_sword_3d");
        }

        return value;
    }

    @Unique
    private BakedModel getCustomItemModel(String name) {
        return ((ItemRendererAccessor) this).getModels().getModelManager().getModel(new ModelIdentifier(EOTE.MOD_ID, name, "inventory"));
    }

    @Unique
    private static final List<Vector3f> cardinalOffsets = List.of(
            new Vector3f(0, -1, 0),
            new Vector3f(1, 0, 0),
            new Vector3f(0, 1, 0),
            new Vector3f(-1, 0, 0),

            new Vector3f(-1, -1, 0),
            new Vector3f(1, 1, 0),
            new Vector3f(0, 1, -1),
            new Vector3f(-1, -1, 0)
    );

    @Shadow
    protected abstract void renderBakedItemQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay);

//    @Inject(method = "renderBakedItemModel", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
//    public void renderItemOutline(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices, CallbackInfo ci, Random random) {
//        cardinalOffsets.forEach(o -> {
//            random.setSeed(42L);
//            matrices.push();
////            var offset = new Vector3f(o).mul(1f / (16f * 2f));
//            var offset = new Vector3f(o).mul(1f / (16f));
//            matrices.translate(offset.x, offset.y, offset.z);
//            matrices.scale(1, 1, 0.9987f);
////            var outlineQuads = model.getQuads(null, null, random).stream().map(OutlineQuad::new).toList();
//
//            this.renderBakedItemQuads(matrices, vertices, model.getQuads(null, null, random), stack, 0, overlay);
//            matrices.pop();
//        });
//    }

    @Inject(method = "renderBakedItemQuads", at = @At("TAIL"))
    public void renderOutline(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, ItemStack stack, int light, int overlay, CallbackInfo ci) {
        ItemOutlineRenderer.renderItemOutline(matrices, vertices, quads, stack, light, overlay);
    }
}