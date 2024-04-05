package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.block.custom.IPrismActionable;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElderPrismItem extends BiDimensionToggleItem implements IArtifact, IStackPredicate, IHasCooldown {
    public static final int REFRACT_COOLDOWN = 20 * 300;

    public ElderPrismItem() {
        super(new ArtifactItemSettings(), new Pair<>(World.OVERWORLD, ModDimensions.DEFENSE_DIM_LEVEL_KEY));
    }

    public static final Ability SPIRITUAL_SHIFT_ABILITY = new Ability("spiritual_shift", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            if (world != null) {

                var worldKey = world.getRegistryKey();

                if (worldKey == ModDimensions.DEFENSE_DIM_LEVEL_KEY) {
                    section.line("to_overworld");
                } else if (worldKey == World.OVERWORLD) {
                    section.line("to_spirit_realm");
                } else {
                    section.line("other");
                }
            }
        }
    };

    public static final Ability REFRACT_ABILITY = new Ability("refract", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(SPIRITUAL_SHIFT_ABILITY, REFRACT_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        var blockPos = context.getBlockPos();
        var world = context.getWorld();
        var stack = context.getStack();
        var user = context.getPlayer();

        var blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IPrismActionable iPrismActionable && user != null && !user.getItemCooldownManager().isCoolingDown(this)) {
            var side = context.getSide();
            if (iPrismActionable.onPrism(stack, user, world, blockPos, side)) {
                user.getItemCooldownManager().set(this, REFRACT_COOLDOWN);
                if (world.isClient) {
                    MinecraftClient.getInstance().gameRenderer.showFloatingItem(stack);
                }
                world.playSound(user, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.PLAYERS, 1, 0.5f);
                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return REFRACT_COOLDOWN;
    }
}
