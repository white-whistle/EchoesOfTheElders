package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.VacuumProjectileEntity;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PocketGalaxy extends Item implements IArtifact, IHasCooldown, IStackPredicate, IRaidReward {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 20, 20 * 5);

    public PocketGalaxy() {
        super(new ArtifactItemSettings());
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
                    VacuumProjectileEntity vacuumProjectileEntity = new VacuumProjectileEntity(world, user);
                    vacuumProjectileEntity.setOwner(user);
                    vacuumProjectileEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
                    world.spawnEntity(vacuumProjectileEntity);
                    user.getItemCooldownManager().set(this, this.getCooldown(user.getStackInHand(hand)));
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            }
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.consume(user.getStackInHand(hand));
    }


    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (user instanceof PlayerEntity player) {
            Box box = new Box(user.getX() - 30, user.getY() - 20, user.getZ() - 30, user.getX() + 30, user.getY() + 20, user.getZ() + 30);
            List<Entity> list = world.getOtherEntities(user, box, entity -> entity instanceof ItemEntity || entity instanceof ExperienceOrbEntity);
            if (!list.isEmpty()) {
                for (Entity itemEntity : list) {
                    itemEntity.setVelocity(user.getPos().add(0, 1, 0).subtract(itemEntity.getPos()).normalize().multiply(1));
                }
            }
        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static final Ability VACUUM_ABILITY = new Ability("vacuum", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }
    };

    public static final Ability MINIATURE_BLACK_HOLE_ABILITY = new Ability("miniature_black_hole", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.SNEAK_RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1");
        }

        @Override
        public boolean hasCooldown() {
            return true;
        }
    };

    public static final List<Ability> ABILITIES = List.of(VACUUM_ABILITY, MINIATURE_BLACK_HOLE_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
