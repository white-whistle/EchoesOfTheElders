package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PortalRingItem extends BiDimensionToggleItem implements IArtifact, IRaidReward {

    public PortalRingItem() {
        super(new ArtifactItemSettings(), new Pair<>(World.OVERWORLD, World.NETHER));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        if (world != null) {

            var worldKey = world.getRegistryKey();

            if (worldKey == World.NETHER) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.portal_ring.effect.to_overworld"));
            } else if (worldKey == World.OVERWORLD) {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.portal_ring.effect.to_nether"));
            } else {
                tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.portal_ring.effect.other"));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
