package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ICooldownReduction;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.system.Text.ModText;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.util.CooldownUtil;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(Item.class)
public class ClientItemMixin {

    @Inject(method = "appendTooltip", at = @At("TAIL"))
    public void appendGenericTooltips(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        var mc = MinecraftClient.getInstance();
        if (mc.world == null) return;

        var player = mc.player;
        if (player == null) return;

        var item = stack.getItem();

        AtomicBoolean padded = new AtomicBoolean(false);
        Runnable tryPad = () -> {
            if (!padded.get()) {
                padded.set(true);
                tooltip.add(Text.empty());
            }
        };

        if (item instanceof ICooldownReduction iCooldownReduction) {
            var p = iCooldownReduction.getCooldownReductionPercentage(stack);
            tryPad.run();
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown_reduction", new TextArgs().putI("percent", Math.round(p * 100))).styled(s -> s.withColor(Formatting.BLUE)));
        }

        if (item instanceof IArtifact) {
            var count = stack.getCount();
            var maxCount = stack.getMaxCount();
            var isSingleItem = maxCount == count && count == 1;

            if (!isSingleItem) {
                tryPad.run();
                if (count == maxCount) {
                    tooltip.add(ModText.STACK_LEVEL.apply(TextUtil.translatable("tooltip.echoes_of_the_elders.artifact_stack.max", new TextArgs().putI("count", count).putI("maxCount", maxCount, Formatting.DARK_GRAY))));
                } else {
                    tooltip.add(ModText.STACK_LEVEL.apply(TextUtil.translatable("tooltip.echoes_of_the_elders.artifact_stack", new TextArgs().putI("count", count).putI("maxCount", maxCount, Formatting.DARK_GRAY))));
                }
            }
        }

        if (item instanceof IHasCooldown iHasCooldown) {
            var cd = CooldownUtil.getReducedCooldown(player, stack.getItem(), iHasCooldown.getCooldown(stack)) / 20f;
            var cdm = player.getItemCooldownManager();

            tryPad.run();

            if (cdm.isCoolingDown(item)) {
                var remainingTime = cdm.getCooldownProgress(item, mc.getTickDelta()) * cd;

                var msg = TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown.active", new TextArgs().putF("seconds", cd, Formatting.BLUE).putF("remaining", remainingTime));
                tooltip.add(msg);
            } else {
                var msg = TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown", new TextArgs().putF("seconds", cd, Formatting.BLUE));
                tooltip.add(msg);
            }

        }
    }
}
