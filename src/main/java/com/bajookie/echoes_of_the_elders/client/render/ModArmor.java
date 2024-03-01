package com.bajookie.echoes_of_the_elders.client.render;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModArmor {

    public static void init() {

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getInnerArmor, getStackedLayer(ModArmor::armorCutout, "gunheel")));
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(EffectLayer::getGlow, "gunheel_glow")));
        }), ModItems.GUNHEELS);

        ArmorRenderer.register(new CustomArmor((ctx) -> {
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(ModArmor::armorCutout, "gangway")));
            ctx.parts.add(new CustomArmor.Part(ctx::getOuterArmor, getStackedLayer(EffectLayer::getGlow, "gangway_glow")));
        }), ModItems.GANGWAY);
    }

    public static Identifier armorIdentifier(String name) {
        return new ModIdentifier("textures/models/armor/" + name + ".png");
    }

    public static Function<ItemStack, RenderLayer> getStackedLayer(Function<Identifier, RenderLayer> renderLayerGetter, String name) {
        return (stack) -> renderLayerGetter.apply(armorIdentifier(name + IStackPredicate.stackAppendix(stack)));
    }

    public static RenderLayer armorCutout(Identifier identifier) {
        return RenderLayer.getArmorCutoutNoCull(identifier);
    }
}
