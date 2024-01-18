package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.MathHelper;

public class RaidDebugItem extends Item {
    public RaidDebugItem() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        var world = entity.getWorld();
        var pos = user.getBlockPos().add(0, 1, 0);

        if (!world.isClient) {
            var raidEnemy = EntityType.CHICKEN.create(world);
            // raidEnemy.setPos(pos.getX(), pos.getY(), pos.getZ());
            raidEnemy.refreshPositionAndAngles((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0f), 0.0f);

            world.spawnEntity(raidEnemy);

            ModCapabilities.RAID_ENEMY.attach(raidEnemy, c -> {
                c.setRaidTarget(entity);
            });
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos().add(0, 1, 0);

        if (!world.isClient) {
            var villager = EntityType.PIG.create(world);
            // villager.setPos(pos.getX(), pos.getY(), pos.getZ());

            villager.refreshPositionAndAngles((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0f), 0.0f);

            System.out.println("YO");
            world.spawnEntity(villager);

        }

        return ActionResult.SUCCESS;

    }

}
