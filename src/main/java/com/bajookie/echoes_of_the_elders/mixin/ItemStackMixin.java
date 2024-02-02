package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    private void onArtifactClicked(ItemStack other, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        var stack = (ItemStack) (Object) (this);
        var item = stack.getItem();

        if (item instanceof IArtifact iArtifact) {
            var ret = iArtifact.onArtifactClicked(stack, other, slot, clickType, player, cursorStackReference);
            cir.setReturnValue(ret);
        }
    }

}
