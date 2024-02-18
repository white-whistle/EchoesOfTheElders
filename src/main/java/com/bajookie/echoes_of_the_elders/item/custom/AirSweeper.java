package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.AirSweeperProjectileEntity;
import com.bajookie.echoes_of_the_elders.item.IHasCooldown;
import com.bajookie.echoes_of_the_elders.system.ItemStack.StackLevel;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.system.Text.TextUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AirSweeper extends Item implements IArtifact, IStackPredicate, IHasCooldown {
    protected final StackedItemStat.Int cooldown = new StackedItemStat.Int(20 * 40, 5 * 20);
    protected final StackedItemStat.Float maxPull = new StackedItemStat.Float(0.1f, 0.4f);
    protected final StackedItemStat.Float maxSpeed = new StackedItemStat.Float(0.5f, 1.3f);

    public AirSweeper() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public int getArtifactMaxStack() {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity user) {
            if (!user.getItemCooldownManager().isCoolingDown(this)) {
                if (!world.isClient) {
                    Box box = new Box(user.getX() - 60, user.getY() - 60, user.getZ() - 60, user.getX() + 60, user.getY() + 60, user.getZ() + 60);
                    List<Entity> list = world.getOtherEntities(user, box, entityer -> ((entityer instanceof FlyingEntity) & !(entityer instanceof BatEntity)) || entityer instanceof EnderDragonEntity || entityer instanceof WitherEntity);
                    if (!list.isEmpty()) {
                        System.out.println(this.maxSpeed.get(stack));
                        AirSweeperProjectileEntity sweeper = new AirSweeperProjectileEntity(world, user.getX(), user.getY(), user.getZ(), list.get(0).getId(), this.maxPull.get(stack), stack.getCount() == stack.getMaxCount(), this.maxSpeed.get(stack), stack.getCount());
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
        return cooldown.get(itemStack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getCount() == stack.getMaxCount()) {
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.air_sweeper.info_max"));
        } else {
            tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.air_sweeper.info"));
        }
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.air_sweeper.max_pull", new TextArgs().putF("max_pull", this.maxPull.get(stack))));
        tooltip.add(TextUtil.translatable("tooltip.echoes_of_the_elders.air_sweeper.max_speed", new TextArgs().putF("max_speed", this.maxSpeed.get(stack))));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
