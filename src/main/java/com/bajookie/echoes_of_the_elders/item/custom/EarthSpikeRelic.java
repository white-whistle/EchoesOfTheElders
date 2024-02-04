package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class EarthSpikeRelic extends Item implements IArtifact, IHasCooldown, IStackPredicate {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 40, 10 * 20);
    public EarthSpikeRelic() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            if (!user.getItemCooldownManager().isCoolingDown(this)){
                Box box = new Box(user.getX() - 15, user.getY() - 5, user.getZ() - 15, user.getX() + 15, user.getY() + 5, user.getZ() + 15);
                List<Entity> list = world.getOtherEntities(user, box, entity -> entity.isOnGround() && entity instanceof LivingEntity);
                Random r = new Random();
                for (Entity entity : list) {
                    ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(ModEffects.EARTH_SPIKE_EFFECT, 5 * r.nextInt(2) + 5, 1, true, false, false), user);
                }
                user.getItemCooldownManager().set(this,this.getCooldown(user.getStackInHand(hand)));
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public int getArtifactMaxStack() {
        return 16;
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return cooldown.get(itemStack);
    }

    @Override
    public Model getBaseModel() {
        return Models.HANDHELD;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.earth_spike_relic.active"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
