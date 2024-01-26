package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.entity.custom.TvArrowEntity;
import com.bajookie.echoes_of_the_elders.entity.custom.VacuumProjectileEntity;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BeeEntity;
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
            tvArrow.setOwner(user);
            world.spawnEntity(tvArrow);
            user.startRiding(tvArrow);
        }
        return super.use(world, user, hand);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        System.out.println("fake");
        if (!attacker.getWorld().isClient) {

            UUID humanPlayerUuid = attacker.getUuid();
            String humanPlayerName = String.valueOf(attacker.getName());
            GameProfile fakeProfile = new GameProfile(humanPlayerUuid, "[Block Breaker of " + humanPlayerName + "]");

            FakePlayer fake = FakePlayer.get((ServerWorld) attacker.getWorld(),fakeProfile);
            fake.setPosition(attacker.getPos());
            attacker.getWorld().spawnEntity(fake);

        }
        return super.postHit(stack, target, attacker);
    }
}
