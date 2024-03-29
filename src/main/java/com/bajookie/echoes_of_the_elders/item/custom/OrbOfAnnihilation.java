package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.OrbOfAnnihilationEntity;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrbOfAnnihilation extends Item implements IArtifact, IStackPredicate, IHasToggledEffect {
    public static StackedItemStat.Float EXPLOSION_POWER = new StackedItemStat.Float(6f, 20f);

    public OrbOfAnnihilation() {
        super(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            var stack = user.getStackInHand(hand);
            var orbEntity = new OrbOfAnnihilationEntity(world, user.getX(), user.getY() + 3, user.getZ(), 0.7f, user);

            world.spawnEntity(orbEntity);
            user.startRiding(orbEntity);

            orbEntity.setStack(stack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.orb_of_annihilation.effect1.info1"));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.orb_of_annihilation.effect1.info2", new TextArgs().putF("explosion_power", EXPLOSION_POWER.get(stack))));
        tooltip.add(TextUtil.join(
                TextUtil.translatable("tooltip.echoes_of_the_elders.orb_of_annihilation.effect1.info3"),
                IHasToggledEffect.getText(stack)
        ));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
