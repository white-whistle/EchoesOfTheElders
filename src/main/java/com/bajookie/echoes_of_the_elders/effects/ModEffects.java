package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.item.custom.SparkingMitts;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;


public class ModEffects {

    public static final StatusEffect DELAYED_LIGHTNING_EFFECT = registerStatusEffect("delayed_lightning_effect", DelayedEffect.create(StatusEffectCategory.HARMFUL, (instance, entity) -> {
        final int secondaryDelay = 20 * 5;
        var world = entity.getWorld();
        var level = instance.getAmplifier();
        if (entity instanceof PlayerEntity) return;
        if (!world.isClient()) {
            ServerWorld worldServer = (ServerWorld) world;
            EntityType.LIGHTNING_BOLT.spawn(worldServer, entity.getBlockPos(), SpawnReason.TRIGGERED);

            if (level > 1) {
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.DELAYED_LIGHTNING_EFFECT, secondaryDelay, level - 1, true, false));
            }
        }
        //        spawn lightning at entity
    }));
    public static final StatusEffect STARFALL_EFFECT = registerStatusEffect("starfall_effect", new StarfallEffect(StatusEffectCategory.HARMFUL, 0x000000));
    public static final StatusEffect EARTH_SPIKE_EFFECT = registerStatusEffect("earth_spike_effect", DelayedEffect.create(StatusEffectCategory.HARMFUL, (effectInstance, living) -> {
        Vec3d pos = living.getPos();
        if (pos != null) {
            living.damage(living.getWorld().getDamageSources().create(DamageTypes.MAGIC, living.getAttacker()), 10);
            ((ServerWorld) living.getWorld()).spawnParticles(ModParticles.EARTH_SPIKE_PARTICLE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }
    }));

    public static final StatusEffect RAID_OBJECTIVE_START_COOLDOWN = registerStatusEffect("raid_objective_start_cooldown", DelayedEffect.create(StatusEffectCategory.NEUTRAL, (instance, entity) -> {
        ModCapabilities.RAID_OBJECTIVE.use(entity, RaidObjectiveCapability::begin);
    }));

    public static final StatusEffect RAID_OBJECTIVE_VICTORY_PHASE = registerStatusEffect("raid_objective_victory_phase", DelayedEffect.create(StatusEffectCategory.NEUTRAL, (instance, entity) -> {
        ModCapabilities.RAID_OBJECTIVE.use(entity, RaidObjectiveCapability::onVictory);
    }));

    public static final StatusEffect RAID_OBJECTIVE_CONTINUE_PHASE = registerStatusEffect("raid_objective_continue_phase", DelayedEffect.create(StatusEffectCategory.NEUTRAL, (instance, entity) -> {
        ModCapabilities.RAID_OBJECTIVE.use(entity, RaidObjectiveCapability::advance);
    }));
    public static final StatusEffect SHOCK_EFFECT = registerStatusEffect("shock_effect", new ShockEffect());
    public static final StatusEffect ELECTRIC_STUN_EFFECT = registerStatusEffect("electric_stun", new ElectricStunEffect());
    public static final StatusEffect NO_GRAVITY_EFFECT = registerStatusEffect("no_gravity_effect", new NoGravityEffect());
    public static final StatusEffect ECHO_HIT = registerStatusEffect("echo_hit_effect", DelayedEffect.create(StatusEffectCategory.HARMFUL, (effectInstance, living) -> {
    }));
    public static final StatusEffect CONDUCTING_EFFECT = registerStatusEffect("conducting_effect", DelayedEffect.create(StatusEffectCategory.HARMFUL, (instance, entity) -> {
        if (!entity.getWorld().isClient && instance.getAmplifier() > 1) {
            World world = entity.getWorld();
            Box box = new Box(entity.getX() - 8, entity.getY() - 5, entity.getZ() - 8, entity.getX() + 8, entity.getY() + 5, entity.getZ() + 8);
            List<Entity> list = world.getOtherEntities(entity, box, (target) -> {
                if (target instanceof LivingEntity living) {
                    if (living instanceof PlayerEntity) return false;
                    return !living.hasStatusEffect(ModEffects.SHOCK_EFFECT);
                }
                return false;
            });
            if (!list.isEmpty()) {
                LivingEntity live = (LivingEntity) list.get(0);
                live.setAttacker(entity.getAttacker());
                Vec3d entityPos = entity.getPos();
                Vec3d targetPos = live.getPos();
                Vec3d diff = entityPos.subtract(targetPos);
                Random r = new Random();
                for (int i = 0; i < 10; i++) {

                    ((ServerWorld) world).spawnParticles(ModParticles.ELECTRIC_SHOCK, entityPos.x - (diff.x * i / 10) + r.nextInt(-5, 5) * 0.02, entityPos.y - (diff.y * i / 10) + r.nextInt(-5, 5) * 0.02 + 0.75, entityPos.z - (diff.z * i / 10) + r.nextInt(-5, 5) * 0.02, 1, 0, 0, 0, 0);
                }
                live.addStatusEffect(new StatusEffectInstance(ModEffects.CONDUCTING_EFFECT, 5, instance.getAmplifier() - 1, true, false));
            }
        }
    }, ((entity, integer) -> {
        if (!entity.getWorld().isClient) {
            World world = entity.getWorld();
            world.playSound(null, entity.getBlockPos(), ModSounds.ELECTRIC_STRIKE, SoundCategory.AMBIENT, 4f, 4f);
            entity.damage(world.getDamageSources().create(DamageTypes.MAGIC, entity.getLastAttacker()), SparkingMitts.SPARK_DAMAGE);
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.SHOCK_EFFECT, 5 * 20, 0, true, false), entity.getLastAttacker());
        }
    })));

    public static final StatusEffect HOP = registerStatusEffect("hop", new StatusEffect(StatusEffectCategory.BENEFICIAL, 0x806866) {
    });


    // ============= impl ==================

    private static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, name), effect);
    }

    public static StatusEffect registerGracefulStatusEffect(String name, StatusEffect effect, int graceTicks) {
        var ret = registerStatusEffect(name, effect);
        return registerStatusEffect(name + "_grace", new DelayedEffect(StatusEffectCategory.NEUTRAL) {
            @Override
            public void onRemoved(StatusEffectInstance effectInstance, LivingEntity entity) {
                entity.addStatusEffect(new StatusEffectInstance(ret, graceTicks));
            }
        });
    }

    public static void registerEffects() {
        EOTE.LOGGER.info("Registering status effects");
    }
}
