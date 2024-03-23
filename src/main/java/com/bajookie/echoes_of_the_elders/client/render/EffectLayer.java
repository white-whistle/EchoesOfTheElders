package com.bajookie.echoes_of_the_elders.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.EndPortalBlockEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class EffectLayer {
    private static final Function<Identifier, RenderLayer> GLOW = Util.memoize(texture -> {
        RenderPhase.Texture texture2 = new RenderPhase.Texture(texture, false, false);
        return RenderLayer.of("eote_glow", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.EYES_PROGRAM).texture(texture2).cull(RenderPhase.DISABLE_CULLING).depthTest(RenderPhase.EQUAL_DEPTH_TEST)/*.transparency(GLINT_TRANSPARENCY)*/.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).writeMaskState(RenderPhase.COLOR_MASK).build(false));
    });

    private static final Function<Identifier, RenderLayer> PORTAL_MASKED = Util.memoize(texture -> {
        var textures = RenderPhase.Textures.create().add(EndPortalBlockEntityRenderer.SKY_TEXTURE, false, false).add(EndPortalBlockEntityRenderer.PORTAL_TEXTURE, false, false).add(texture, false, false).build();
        var multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().program(ModShaders.END_PORTAL_MASK_PROGRAM).texture(textures).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).build(false);

        return RenderLayer.of("eote_portal_masked", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, false, true, multiPhaseParameters);
    });

    private static final Function<Identifier, RenderLayer> ARMOR_CUTOUT_WITH_CULLING = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.ARMOR_CUTOUT_NO_CULL_PROGRAM).texture(new RenderPhase.Texture(texture, false, false)).transparency(RenderPhase.NO_TRANSPARENCY).cull(RenderPhase.ENABLE_CULLING).lightmap(RenderPhase.ENABLE_LIGHTMAP).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).build(true);
        return RenderLayer.of("eote_armor_cutout_with_culling", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, false, multiPhaseParameters);
    });

    private static final Function<Identifier, RenderLayer> ITEM_ENTITY_TRANSLUCENT_FLAT = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters.builder().program(ModShaders.ITEM_ENTITY_TRANSLUCENT_SIMPLE).texture(new RenderPhase.Texture((Identifier) texture, false, false)).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).cull(RenderPhase.ENABLE_CULLING).target(RenderPhase.ITEM_ENTITY_TARGET).lightmap(RenderPhase.ENABLE_LIGHTMAP).overlay(RenderPhase.ENABLE_OVERLAY_COLOR).layering(RenderPhase.VIEW_OFFSET_Z_LAYERING).writeMaskState(RenderPhase.ALL_MASK).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).build(true);
        return RenderLayer.of("item_entity_translucent_with_cull", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    public static RenderLayer getGlow(Identifier texture) {
        return GLOW.apply(texture);
    }

    public static RenderLayer getPortalMasked(Identifier texture) {
        return PORTAL_MASKED.apply(texture);
    }

    public static RenderLayer getArmorCutoutWithCulling(Identifier texture) {
        return ARMOR_CUTOUT_WITH_CULLING.apply(texture);
    }

    public static RenderLayer getItemEntityWithCulling(Identifier texture) {
        return ITEM_ENTITY_TRANSLUCENT_FLAT.apply(texture);
    }
}
