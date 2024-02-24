package com.bajookie.echoes_of_the_elders.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class GlowLayer {
    private static final Function<Identifier, RenderLayer> GLOW = Util.memoize(texture -> {
        RenderPhase.Texture texture2 = new RenderPhase.Texture((Identifier) texture, false, false);
        return RenderLayer.of("eote_glow", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.EYES_PROGRAM).texture(texture2).cull(RenderPhase.DISABLE_CULLING).depthTest(RenderPhase.EQUAL_DEPTH_TEST)/*.transparency(GLINT_TRANSPARENCY)*/.transparency(RenderPhase.ADDITIVE_TRANSPARENCY).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).writeMaskState(RenderPhase.COLOR_MASK).build(false));
    });

    private static final Function<Identifier, RenderLayer> PORTAL = Util.memoize(texture -> {
        RenderPhase.Texture texture2 = new RenderPhase.Texture((Identifier) texture, false, false);
        return RenderLayer.of("eote_portal", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.END_PORTAL_PROGRAM).texture(RenderPhase.Textures.create().add(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false).add(EndPortalBlockEntityRenderer.PORTAL_TEXTURE, false, false).build()).cull(RenderPhase.DISABLE_CULLING).depthTest(RenderPhase.EQUAL_DEPTH_TEST)/*.transparency(GLINT_TRANSPARENCY)*/.transparency(RenderPhase.ADDITIVE_TRANSPARENCY).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).writeMaskState(RenderPhase.COLOR_MASK).build(false));
    });

    public static RenderLayer getGlow(Identifier texture) {
        return GLOW.apply(texture);
    }

    public static RenderLayer getGlowFromBaseTexture(ItemStack itemStack, EquipmentSlot equipmentSlot, LivingEntity livingEntity, int layer) {
        var ret = ItemStackCustomArmorTextures.getBaseTexture(itemStack, equipmentSlot, livingEntity);
        if (ret == null) return null;

        return getGlow(ItemStackCustomArmorTextures.toArmorLayerTexture(ret.withSuffixedPath("_glow"), layer));
    }
}
