package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    @Final
    private static Identifier CROSSHAIR_TEXTURE;
    @Unique
    private static final Identifier KEYHOLE_TEXTURE = new ModIdentifier("hud/keyhole");

    @Shadow
    private int scaledWidth;

    @Shadow
    private int scaledHeight;

    @Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
    private void drawGuiTexture(DrawContext instance, Identifier texture, int x, int y, int width, int height) {
        var mc = MinecraftClient.getInstance();

        customRender:
        {
            if (!(mc.targetedEntity instanceof LivingEntity livingEntity)) break customRender;

            var raidObjective = ModCapabilities.RAID_OBJECTIVE.tryGetCapability(livingEntity);
            if (raidObjective == null) break customRender;

            if (!raidObjective.canAcceptKeyFromPlayer(mc.player)) break customRender;

            instance.drawGuiTexture(KEYHOLE_TEXTURE, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 15, 15);
            return;
        }

        instance.drawGuiTexture(CROSSHAIR_TEXTURE, (this.scaledWidth - 15) / 2, (this.scaledHeight - 15) / 2, 15, 15);
    }
}
