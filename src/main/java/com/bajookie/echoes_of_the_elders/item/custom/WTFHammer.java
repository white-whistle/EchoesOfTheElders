package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WTFHammer extends UpgradeHammer implements IArtifact {
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

    public static final Ability PERFECT_TOUCH_ABILITY = new Ability("perfect_touch", Ability.AbilityType.SPECIAL, Ability.AbilityTrigger.CLICK_STACK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final Ability INFINITE_POTENTIAL_ABILITY = new Ability("infinite_potential", Ability.AbilityType.SPECIAL, Ability.AbilityTrigger.CLICK_STACK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final List<Ability> ABILITIES = List.of(PERFECT_TOUCH_ABILITY, INFINITE_POTENTIAL_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
