package com.bajookie.echoes_of_the_elders.entity.custom;

import com.bajookie.echoes_of_the_elders.block.ModBlocks;
import com.bajookie.echoes_of_the_elders.effects.ModEffects;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RaidTotemEntity extends AnimalEntity {
    public RaidTotemEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

        ModCapabilities.RAID_OBJECTIVE.attach(this);
    }

    @Override
    protected void initGoals() {
    }

    public static DefaultAttributeContainer.Builder createFlowerDefenseAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15)
                .add(EntityAttributes.GENERIC_ARMOR, 5f);
    }

    // @Override
    // public ActionResult interactMob(PlayerEntity player, Hand hand) {
    //     // if (!player.getWorld().isClient) {
    //     //     var inv = new SimpleInventory(2);
    //     //     inv.setStack(0, new ItemStack(ModItems.GODSLAYER));
    //     //     inv.setStack(1, new ItemStack(ModItems.PANDORAS_BAG));
    //     //
    //     //     player.openHandledScreen(new NamedScreenHandlerFactory() {
    //     //         @Override
    //     //         public Text getDisplayName() {
    //     //             return Text.empty();
    //     //         }
    //     //
    //     //         @Nullable
    //     //         @Override
    //     //         public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    //     //             return new RaidContinueScreenHandler(syncId, playerInventory, inv);
    //     //         }
    //     //     });
    //     // }
    //
    //     return ActionResult.SUCCESS;
    // }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity) return false;
        if (this.hasStatusEffect(ModEffects.RAID_OBJECTIVE_CONTINUE_PHASE)) return false;

        return super.damage(source, amount);
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        //    noop
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public ItemStack eatFood(World world, ItemStack stack) {
        return Items.APPLE.getDefaultStack();
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);

        var world = this.getWorld();
        if (!world.isClient) {
            world.setBlockState(this.getBlockPos(), ModBlocks.TOTEM_SPAWN_BLOCK.getDefaultState());
        }
    }
}
