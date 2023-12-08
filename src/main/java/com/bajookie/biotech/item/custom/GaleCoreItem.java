package com.bajookie.biotech.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static com.bajookie.biotech.BioTech.MOD_ID;

public class GaleCoreItem extends Item {
    private final String usedTicksId = new Identifier(MOD_ID, "use").toString();
    private final String regenTicksId = new Identifier(MOD_ID, "regen").toString();
    private final String relicActiveId = new Identifier(MOD_ID, "active").toString();

    protected static final int EFFECT_RATE = 10;
    protected static final int REGEN_RATE = 10;
    protected static final int MAX_USAGE = 256;
    protected static final int DRAIN_COOLDOWN_TICKS = 20 * 30;

    public GaleCoreItem() {
        super(new FabricItemSettings().maxCount(1).maxDamage(MAX_USAGE));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (entity instanceof PlayerEntity user) {
            var nbt = stack.getOrCreateNbt();
            // initialize nbt
            if (!nbt.contains(relicActiveId)) {
                nbt.putInt(usedTicksId, 0);
                nbt.putInt(regenTicksId, 0);
                nbt.putBoolean(relicActiveId, false);
            }

            // update usage ticks
            if (nbt.getBoolean(relicActiveId)) {
                int usage = nbt.getInt(usedTicksId);
                if (usage > EFFECT_RATE) {
                    nbt.putInt(usedTicksId, 0);
                    stack.setDamage(stack.getDamage() + 2);

                    if (user.isFallFlying()) {
                        world.addParticle(ParticleTypes.POOF, user.getX(), user.getY(), user.getZ(), 0, 0, 0);
                        Vec3d rotationDir = user.getRotationVector();
                        Vec3d velocity = user.getVelocity();
                        user.setVelocity(velocity.add(rotationDir.x * 0.1 + (rotationDir.x * 1.5 - velocity.x) * 0.5, rotationDir.y * 0.1 + (rotationDir.y * 1.5 - velocity.y) * 0.5, rotationDir.z * 0.1 + (rotationDir.z * 1.5 - velocity.z) * 0.5));
                    }

                } else {
                    nbt.putInt(usedTicksId, usage + 1);
                }
            } else {
                int regen = nbt.getInt(regenTicksId);
                if (stack.getDamage() > 0) {
                    if (regen > REGEN_RATE) {
                        nbt.putInt(regenTicksId, 0);
                        stack.setDamage(stack.getDamage() - 1);
                    } else {
                        nbt.putInt(regenTicksId, regen + 1);
                    }
                }
            }

            // if the item has consumed all of its
            if (stack.getDamage() >= MAX_USAGE) {
                nbt.putBoolean(relicActiveId, false);
                user.getItemCooldownManager().set(this, DRAIN_COOLDOWN_TICKS);
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getItemCooldownManager().isCoolingDown(this)) return super.use(world, user, hand);

        ItemStack stack = user.getStackInHand(hand);
        var nbt = stack.getOrCreateNbt();
        var active = nbt.getBoolean(relicActiveId);

        if (!active) {
            nbt.putInt(usedTicksId, 0);
            nbt.putInt(regenTicksId, 0);
        }

        nbt.putBoolean(relicActiveId, !active);

        return TypedActionResult.success(stack);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        var nbt = stack.getNbt();

        if (nbt == null) return false;

        return nbt.getBoolean(relicActiveId);
    }
}
