package com.bajookie.echoes_of_the_elders.item;

import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerAccessor;
import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerEntryAccessor;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import com.bajookie.echoes_of_the_elders.util.CooldownUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public interface IHasCooldown {
    int getCooldown(ItemStack itemStack);

    default int getMinCooldown() {
        return 1;
    }

    default boolean canReduceCooldown() {
        return true;
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    static MutableText getCooldownMessage(ItemStack stack) {
        var item = stack.getItem();
        if (!(item instanceof IHasCooldown iHasCooldown)) return null;

        var mc = MinecraftClient.getInstance();
        if (mc.world == null) return null;

        var player = mc.player;
        if (player == null) return null;


        var cd = CooldownUtil.getReducedCooldown(player, stack.getItem(), iHasCooldown.getCooldown(stack));
        var cdm = player.getItemCooldownManager();

        if (cdm.isCoolingDown(item) && cdm instanceof ItemCooldownManagerAccessor a) {
            var cdEntry = a.getEntries().get(item);
            if (cdEntry instanceof ItemCooldownManagerEntryAccessor e) {
                var totalTicks = e.getEndTick() - e.getStartTick();
                var remainingTime = (int) (cdm.getCooldownProgress(item, mc.getTickDelta()) * totalTicks);

                return TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown.active", new TextArgs().put("cooldown", TextUtil.formatTime(cd).styled(s -> s.withColor(Formatting.BLUE))).put("remaining", TextUtil.formatTime(remainingTime)));

            }
        } else {
            return TextUtil.translatable("tooltip.echoes_of_the_elders.cooldown", new TextArgs().put("cooldown", TextUtil.formatTime(cd).styled(s -> s.withColor(Formatting.BLUE))));
        }

        return null;
    }
}
