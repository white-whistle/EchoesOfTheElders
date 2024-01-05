package com.bajookie.echoes_of_the_elders.item.entites;

import com.bajookie.echoes_of_the_elders.entity.custom.ChainLightningProjectileEntity;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.custom.IArtifact;
import com.bajookie.echoes_of_the_elders.item.custom.IStackPredicate;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ChainLightningItem extends Item implements IArtifact, IStackPredicate, IHasCooldown {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 20, 20 * 5);

    public ChainLightningItem() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var itemStack = user.getStackInHand(hand);

        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            user.getItemCooldownManager().set(this, this.getCooldown(itemStack));

            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
            if (!world.isClient) {
                ChainLightningProjectileEntity chainLightningProjectileEntity = new ChainLightningProjectileEntity(world, user);
                chainLightningProjectileEntity.setItem(itemStack);
                chainLightningProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                world.spawnEntity(chainLightningProjectileEntity);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(this));

            return TypedActionResult.success(itemStack, world.isClient());
        }

        return super.use(world, user, hand);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return cooldown.get(itemStack);
    }
}
