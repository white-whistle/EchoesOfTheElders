package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.client.animation.AnimationUtil;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.util.Color;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RealityPick extends PickaxeItem implements IArtifact {
    public RealityPick() {
        super(ModItems.ARTIFACT_BASE_MATERIAL, 0, 0, new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        var progress = AnimationUtil.HUE_SHIFT_ANIMATION.getProgress(0);

        var c = Color.fromHSL(360 * progress, 0.5f, 0.7f).getRGB();

        tooltip.add(Text.translatable("tooltip.echoes_of_the_elders.reality_pick.effect", Text.translatable("tooltip.echoes_of_the_elders.reality_pick.everything").styled(s -> s.withColor(c))));
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return true;
    }
}
