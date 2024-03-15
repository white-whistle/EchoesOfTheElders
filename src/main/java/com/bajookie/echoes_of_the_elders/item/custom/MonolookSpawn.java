package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.MonolookEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class MonolookSpawn extends Item {
    public MonolookSpawn() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        NbtCompound comp = stack.getOrCreateNbt();
        if (!world.isClient){
            ServerWorld serverWorld = (ServerWorld) world;
            if (!comp.contains("entity_id")){
                MonolookEntity monolook = new MonolookEntity(world);
                monolook.setPosition(user.getPos().add(1,3,1));
                monolook.setOwner(user);
                monolook.setOwnerUuid(user.getUuid());
                world.spawnEntity(monolook);
                comp.putUuid("entity_id",monolook.getUuid());
            } else {
                UUID uui = comp.getUuid("entity_id");
                var e = serverWorld.getEntity(uui);
                if (e != null){
                    e.discard();
                }
                comp.remove("entity_id");
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().contains("entity_id");
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (!world.isClient){
            NbtCompound comp = stack.getOrCreateNbt();
            if (comp.contains("entity_id")){
                ServerWorld serverWorld = (ServerWorld) world;
                var e =serverWorld.getEntity(comp.getUuid("entity_id"));
                if (e == null){
                    comp.remove("entity_id");
                }
            }
        }
    }
}
