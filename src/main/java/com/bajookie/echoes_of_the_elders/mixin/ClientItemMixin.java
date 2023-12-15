package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.util.CooldownUtil;
import net.minecraft.client.MinecraftClient;
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
public class ClientItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendGenericTooltips(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        var item = stack.getItem();
        if (item instanceof ICooldownReduction iCooldownReduction) {
            var p = iCooldownReduction.getCooldownReductionPercentage(stack);
            tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown_reduction", Math.round(p * 100)));
        }

        if (item instanceof IHasCooldown iHasCooldown) {
            var cd = CooldownUtil.getReducedCooldown(MinecraftClient.getInstance().player, stack.getItem(), iHasCooldown.getCooldown()) / 20f;
            var n = Math.floor(cd) == cd ? (int) cd : Text.translatable("number.echoes_of_the_elders.f1", cd);

            if (iHasCooldown.canReduceCooldown()) {
                tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown.reduceable", n));
            } else {
                tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown.static", n));
            }
        }
    }
}
