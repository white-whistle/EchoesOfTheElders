package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WTFHammer extends UpgradeHammer {
    public WTFHammer() {
        super(new ArtifactItemSettings());
    }

    @Override
    public ClickResult canUpgrade(PlayerEntity user, ItemStack self, ItemStack other) {
        if (!(other.getItem() instanceof IArtifact)) return fail(user, self, other);
        return success(user, self, other);
    }

    @Override
    public void onUpgrade(PlayerEntity user, ItemStack self, ItemStack other, StackReference cursor) {
        if (StackLevel.isMaxed(other)) {
            StackLevel.raise(other, 1);
            return;
        }

        if (other.getItem() instanceof IArtifact iArtifact) {
            StackLevel.set(other, iArtifact.getArtifactMaxStack());
        }

    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.wtf_hammer.info1"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.wtf_hammer.info2"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
