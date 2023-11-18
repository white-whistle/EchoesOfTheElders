package com.bajookie.biotech.mixin;

import com.bajookie.biotech.BioTech;
import com.bajookie.biotech.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
        return  ((ItemRendererAccessor) this).getModels().getModelManager().getModel(new ModelIdentifier(BioTech.MOD_ID, name, "inventory"));
    }
}