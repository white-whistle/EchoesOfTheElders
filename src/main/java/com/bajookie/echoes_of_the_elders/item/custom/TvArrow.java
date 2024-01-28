package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.TvArrowEntity;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.UUID;

public class TvArrow extends Item {
    public TvArrow() {
        super(new FabricItemSettings().maxCount(1));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient) {
            TvArrowEntity tvArrow = new TvArrowEntity(world, user.getX(), user.getY() + 3, user.getZ(), 0, 0.7f, user.getPos());
            world.spawnEntity(tvArrow);
            tvArrow.setOwner(user);
            user.startRiding(tvArrow);
        }
        return super.use(world, user, hand);
    }

    /*
                UUID humanPlayerUuid = user.getUuid();
            String humanPlayerName = String.valueOf(user.getName());
            GameProfile fakeProfile = new GameProfile(humanPlayerUuid, "[Block Breaker of " + humanPlayerName + "]");
            FakePlayer fake = FakePlayer.get((ServerWorld) world,fakeProfile);
            fake.setPosition(user.getPos());
     */
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return super.postHit(stack, target, attacker);
    }
}
