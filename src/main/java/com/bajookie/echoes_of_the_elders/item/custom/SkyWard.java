package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.SkyWardProjectileEntity;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.item.IHasToggledEffect;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.passive.BatEntity;
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

import static com.bajookie.echoes_of_the_elders.system.ItemStack.CustomItemNbt.EFFECT_ENABLED;

public class SkyWard extends Item implements IArtifact, IStackPredicate, IHasCooldown, IHasToggledEffect, IRaidReward {
    public static final StackedItemStat.Int COOLDOWN = new StackedItemStat.Int(20 * 10, 3 * 20);
    public static final StackedItemStat.Float MAX_PULL = new StackedItemStat.Float(0.1f, 0.4f);
    public static final StackedItemStat.Float PROJECTILE_SPEED = new StackedItemStat.Float(0.5f, 1.3f);

    public SkyWard() {
        super(new ArtifactItemSettings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!EFFECT_ENABLED.get(stack)) return;
        if (entity instanceof PlayerEntity user) {
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                if (!world.isClient) {
                    Box box = new Box(user.getX() - 60, user.getY() - 60, user.getZ() - 60, user.getX() + 60, user.getY() + 60, user.getZ() + 60);
                    List<Entity> list = world.getOtherEntities(user, box, entityer -> ((entityer instanceof FlyingEntity) & !(entityer instanceof BatEntity)) || entityer instanceof EnderDragonEntity || entityer instanceof WitherEntity);
                    if (!list.isEmpty()) {
                        SkyWardProjectileEntity sweeper = new SkyWardProjectileEntity(world, user.getX(), user.getY(), user.getZ(), list.get(0).getId(), MAX_PULL.get(stack), StackLevel.isMaxed(stack), this.PROJECTILE_SPEED.get(stack), stack.getCount());
                        world.spawnEntity(sweeper);
                        user.getItemCooldownManager().set(this, this.getCooldown(stack));
                    } else {
                        user.getItemCooldownManager().set(this, 20);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getCooldown(ItemStack itemStack) {
        return COOLDOWN.get(itemStack);
    }

    public static Ability PUNISH_FLIGHT_ABILITY = new Ability("punish_flight", Ability.AbilityType.PASSIVE, Ability.AbilityTrigger.TOGGLED) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            if (StackLevel.isMaxed(stack)) {
                section.line("info1.max");
            } else {
                section.line("info1");
            }
            section.line("info2");
            section.line("info3");
        }
    };

    public static List<Ability> ABILITIES = List.of(PUNISH_FLIGHT_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack stack) {
        return ABILITIES;
    }
}
