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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useRubyStaffModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (stack.isOf(ModItems.ANCIENT_STONE_SWORD) && renderMode != ModelTransformationMode.GUI) {
            return getCustomItemModel("ancient_stone_sword_3d");
        }

        return value;
    }

    private BakedModel getCustomItemModel(String name) {
        return  ((ItemRendererAccessor) this).getModels().getModelManager().getModel(new ModelIdentifier(BioTech.MOD_ID, name, "inventory"));
    }
}