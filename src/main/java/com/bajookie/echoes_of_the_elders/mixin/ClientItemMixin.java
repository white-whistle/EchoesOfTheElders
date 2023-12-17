package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.util.CooldownUtil;
import com.bajookie.echoes_of_the_elders.util.TextUtil;
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
        if (world == null){
            return;
        }
        var item = stack.getItem();

        if (item instanceof IArtifact) {
            var count = stack.getCount();
            var maxCount = stack.getMaxCount();
            var isSingleton = maxCount == count && count == 1;

            if (!isSingleton) {
                tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.artifact_stack", count, maxCount));
            }
        }

        if (item instanceof ICooldownReduction iCooldownReduction) {
            var p = iCooldownReduction.getCooldownReductionPercentage(stack);
            tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown_reduction", Math.round(p * 100)));
        }

        if (item instanceof IHasCooldown iHasCooldown) {
            var cd = CooldownUtil.getReducedCooldown(MinecraftClient.getInstance().player, stack.getItem(), iHasCooldown.getCooldown(stack)) / 20f;
            var n = TextUtil.f1(cd);

            if (iHasCooldown.canReduceCooldown()) {
                tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown.reduceable", n));
            } else {
                tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.cooldown.static", n));
            }
        }
    }
}
