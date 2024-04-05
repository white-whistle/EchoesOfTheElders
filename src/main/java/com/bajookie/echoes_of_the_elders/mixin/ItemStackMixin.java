package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.events.ItemstackDecrementEvent;
import com.bajookie.echoes_of_the_elders.item.IEmptyClick;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.custom.IUpgradeItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin implements ItemstackDecrementEvent.ModItemstackExtension {


    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    private void onArtifactClicked(ItemStack other, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        var stack = (ItemStack) (Object) (this);
        var item = stack.getItem();

        if (clickType == ClickType.RIGHT && other.isEmpty() && item instanceof IEmptyClick iEmptyClick) {
            if (iEmptyClick.onEmptyClick(player, stack, cursorStackReference)) {
                cir.setReturnValue(true);
                return;
            }
        }

        var clicked = IUpgradeItem.handleClick(other, stack, slot, clickType, player, cursorStackReference);
        switch (clicked) {
            case SUCCESS, FAILURE -> cir.setReturnValue(true);

            case FORWARD -> {
                if (item instanceof IArtifact iArtifact) {
                    var ret = iArtifact.onArtifactClicked(stack, other, slot, clickType, player, cursorStackReference);
                    cir.setReturnValue(ret);
                }
            }
        }
    }

    @Inject(method = "decrement", at = @At("HEAD"))
    private void onStackDecrement(int amount, CallbackInfo ci) {
        var stack = (ItemStack) (Object) (this);

        if (decrementListener != null) {
            decrementListener.accept(stack);
        }
    }

    @Unique
    private Consumer<ItemStack> decrementListener;

    @Override
    public void echoesOfTheElders$setDecrementHandler(Consumer<ItemStack> consumer) {
        decrementListener = consumer;
    }
}
