package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.entity.custom.AirSweeperProjectileEntity;
import com.bajookie.echoes_of_the_elders.entity.custom.ChainLightningProjectileEntity;
import com.bajookie.echoes_of_the_elders.entity.custom.VacuumProjectileEntity;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class VacuumRelic extends Item implements IArtifact, IHasCooldown {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 20, 20 * 5);

    public VacuumRelic() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20 * 60;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking()) {
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                if (!world.isClient) {
                    VacuumProjectileEntity vacuumProjectileEntity = new VacuumProjectileEntity(world,user);
                    vacuumProjectileEntity.setOwner(user);
                    vacuumProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                    world.spawnEntity(vacuumProjectileEntity);
                    user.getItemCooldownManager().set(this, this.getCooldown(user.getStackInHand(hand)));
                }
            }
        } else {
            user.setCurrentHand(hand);
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }



    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                Box box = new Box(user.getX() - 20, user.getY() - 20, user.getZ() - 20, user.getX() + 20, user.getY() + 20, user.getZ() + 20);
                List<Entity> list = world.getOtherEntities(user, box, entity -> entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity);
                if (!list.isEmpty()) {
                    for (Entity itemEntity : list) {
                        itemEntity.setVelocity(user.getPos().add(0, 1, 0).subtract(itemEntity.getPos()).normalize().multiply(1));
                    }
                }
            }
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return cooldown.get(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.vacuum_relic.use", new TextArgs().putF("max_use", 60)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
