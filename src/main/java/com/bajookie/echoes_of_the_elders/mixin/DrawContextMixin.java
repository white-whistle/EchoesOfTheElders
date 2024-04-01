package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Shadow
    public abstract int drawText(TextRenderer textRenderer, @Nullable String text, int x, int y, int color, boolean shadow);

    @Unique
    private ItemStack currentItemStack;

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "HEAD"))
    public void drawTextBefore(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        currentItemStack = stack;
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getCount()I", ordinal = 0))
    public int drawTextProxy(ItemStack instance) {
        // trick artifacts into going to itemstack count rendering
        var level = StackLevel.get(currentItemStack);
        if (level > 1) return 2;

        return instance.getCount();
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"))
    public int drawTextProxy(DrawContext instance, TextRenderer textRenderer, String text, int x, int y, int color, boolean shadow) {
        var level = StackLevel.get(currentItemStack);
        var isRenderingDamageBar = currentItemStack.getDamage() > 0;

        if (currentItemStack == null || level <= 1) {
            return this.drawText(textRenderer, text, x, y, color, shadow);
        }

        currentItemStack = null;

        var mat = instance.getMatrices();
        mat.push();

        var scale = 0.5f;
        mat.scale(scale, scale, 1);

        if (isRenderingDamageBar) {
            mat.translate(0, -8, 0);
        }

        var nText = "✦" + level;
        var v = (int) (textRenderer.getWidth(text) - 1 - (textRenderer.getWidth(nText) * 0.5f));

        var ret = this.drawText(textRenderer, nText, (int) ((x + v) / scale), (int) ((y + 3) / scale), color, shadow);

        mat.pop();

        return ret;
    }

}
