package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.client.tooltip.TooltipComponentSlot;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TooltipComponent.class)
public interface TooltipComponentMixin {
    @Inject(method = "of(Lnet/minecraft/text/OrderedText;)Lnet/minecraft/client/gui/tooltip/TooltipComponent;", at = @At("HEAD"), cancellable = true)
    private static void handleTooltipComponentSlot(OrderedText text, CallbackInfoReturnable<TooltipComponent> cir) {
        if (text instanceof TooltipComponentSlot tooltipComponentSlot) {
            cir.setReturnValue(TooltipComponent.of(tooltipComponentSlot.getTooltipData()));
        }
    }
}
