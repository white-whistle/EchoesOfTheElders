package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;
import java.util.List;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

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

        if (isHeld) {
            var item = stack.getItem();
            if (item instanceof IHasUpscaledModel iHasUpscaledModel) {
                if (iHasUpscaledModel.shouldUseUpscaledModel(stack)) {
                    var id = Registries.ITEM.getId(item);

                    var baseUpscaledModel = getCustomItemModel(id.withSuffixedPath("_x32").getPath());

                    return baseUpscaledModel.getOverrides().apply(baseUpscaledModel, stack, null, null, 0);
                }
            }

            if (stack.isOf(ModItems.ANCIENT_STONE_SWORD)) {
                return getCustomItemModel("ancient_stone_sword_3d");
            }
            if (stack.isOf(ModItems.SHINY_ANCIENT_STONE_SWORD)) {
                return getCustomItemModel("shiny_ancient_stone_sword_3d");
            }
            if (item == ModItems.ARC_LIGHTNING){
                return getCustomItemModel("arc_lightning_3d");
            }
            if (item == ModItems.SCORCHERS_MITTS){
                return getCustomItemModel("scorchers_mitts_3d");
            }

        }

        return value;
    }

    @Unique
    private BakedModel getCustomItemModel(String name) {
        return ((ItemRendererAccessor) this).getModels().getModelManager().getModel(new ModelIdentifier(MOD_ID, name, "inventory"));
    }
}