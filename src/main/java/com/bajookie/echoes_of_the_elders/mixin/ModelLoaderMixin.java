package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.IHasUpscaledModel;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void addModel(ModelIdentifier modelId);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;addModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 3, shift = At.Shift.AFTER))
    public void addCustomModels(BlockColors blockColors, Profiler profiler, Map<Identifier, JsonUnbakedModel> jsonUnbakedModels, Map<Identifier, List<ModelLoader.SourceTrackedData>> blockStates, CallbackInfo ci) {
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "ancient_stone_sword_3d", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID, "shiny_ancient_stone_sword_3d", "inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID,"arc_lightning_3d","inventory"));
        this.addModel(new ModelIdentifier(EOTE.MOD_ID,"scorchers_mitts_3d","inventory"));

        ModItems.registeredModItems.forEach(item -> {
            if (item instanceof IHasUpscaledModel) {
                Identifier identifier = Registries.ITEM.getId(item);

                this.addModel(new ModelIdentifier(EOTE.MOD_ID, identifier.withSuffixedPath("_x32").getPath(), "inventory"));
            }
        });
    }
}