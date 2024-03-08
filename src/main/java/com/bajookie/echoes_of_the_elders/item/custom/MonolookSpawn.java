package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MonolookSpawn extends Item {
    public MonolookSpawn() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient){
            MonolookEntity monolook = new MonolookEntity(world);
            monolook.setPosition(user.getPos().add(1,3,1));
            world.spawnEntity(monolook);
            monolook.setOwner(user);
            monolook.setOwnerUuid(user.getUuid());
        }
        return super.use(world, user, hand);
    }
}
