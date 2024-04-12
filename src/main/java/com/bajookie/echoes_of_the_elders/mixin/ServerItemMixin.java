package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModGenericTooltip;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class ServerItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendGenericTooltips(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        ModGenericTooltip.appendGenericTooltips(stack, world, tooltip, context, false);
    }
}
