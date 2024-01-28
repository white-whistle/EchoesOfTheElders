package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.system.Capability.IHasCapability;
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

import java.util.Random;

public class RaidDebugItem extends Item {
    public RaidDebugItem() {
        super(new FabricItemSettings().rarity(Rarity.EPIC).maxCount(1));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        var world = entity.getWorld();

        if (entity instanceof IHasCapability iHasCapability) {
            System.out.println(iHasCapability.echoesOfTheElders$hasCapabilities());
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos().add(0, 1, 0);

        if (!world.isClient) {
            var raidObjective = EntityType.PIG.create(world);

            assert raidObjective != null;
            raidObjective.refreshPositionAndAngles((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, MathHelper.wrapDegrees(world.random.nextFloat() * 360.0f), 0.0f);

            world.spawnEntity(raidObjective);

            ModCapabilities.RAID_OBJECTIVE.attach(raidObjective);
        }

        return ActionResult.SUCCESS;

    }

}
