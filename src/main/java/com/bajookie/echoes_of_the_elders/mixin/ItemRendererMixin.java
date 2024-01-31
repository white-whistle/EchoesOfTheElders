package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.ItemStack.Soulbound;
import net.minecraft.block.Block;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.MatrixUtil;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow
    protected abstract void renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertices);

    @Shadow
    @Final
    private ItemModels models;
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

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V", shift = At.Shift.AFTER))
    private void renderSoulbound(ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        var soulbound = Soulbound.getUuid(stack);
        if (soulbound == null) return;

        var mc = MinecraftClient.getInstance();
        if (mc.world == null) return;

        var player = mc.player;
        if (player == null) return;
        if (soulbound.equals(player.getUuid())) return;

        VertexConsumer vertexConsumer;
        Block block;
        boolean bl22 = renderMode == ModelTransformationMode.GUI || renderMode.isFirstPerson() || !(stack.getItem() instanceof BlockItem) || !((block = ((BlockItem) stack.getItem()).getBlock()) instanceof TransparentBlock) && !(block instanceof StainedGlassPaneBlock);
        RenderLayer renderLayer = RenderLayers.getItemLayer(stack, bl22);
        vertexConsumer = bl22 ? ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint()) : ItemRenderer.getItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());

        var nModel = getCustomItemModel("soulbound");

        renderBakedItemModel(nModel, stack, light, overlay, matrices, vertexConsumer);

    }

    @Unique
    private BakedModel getCustomItemModel(String name) {
        return ((ItemRendererAccessor) this).getModels().getModelManager().getModel(new ModelIdentifier(MOD_ID, name, "inventory"));
    }
}