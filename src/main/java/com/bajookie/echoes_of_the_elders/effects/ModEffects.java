package com.bajookie.echoes_of_the_elders.effects;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.entity.ModDamageTypes;
import com.bajookie.echoes_of_the_elders.particles.ModParticles;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import com.bajookie.echoes_of_the_elders.system.Capability.ModCapabilities;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

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
                entity.addStatusEffect(new StatusEffectInstance(ModEffects.DELAYED_LIGHTNING_EFFECT, secondaryDelay, level - 1));
            }
        }
        //        spawn lightning at entity
    }));

    public static final StatusEffect RAID_OBJECTIVE_START_COOLDOWN = registerStatusEffect("raid_objective_start_cooldown", DelayedEffect.create(StatusEffectCategory.NEUTRAL, (instance, entity) -> {
        ModCapabilities.RAID_OBJECTIVE.use(entity, RaidObjectiveCapability::begin);
    }));
    public static final StatusEffect SHOCK_EFFECT = registerStatusEffect("shock_effect",new ShockEffect());
    public static final StatusEffect ELECTRIC_STUN_EFFECT = registerStatusEffect("electric_stun",new ElectricStunEffect());
    public static final StatusEffect NO_GRAVITY_EFFECT = registerStatusEffect("no_gravity_effect",new NoGravityEffect());
    public static final StatusEffect ECHO_HIT = registerStatusEffect("echo_hit_effect",DelayedEffect.create(StatusEffectCategory.HARMFUL,(effectInstance, living) -> {}));
    public static final StatusEffect CONDUCTING_EFFECT = registerStatusEffect("conducting_effect",DelayedEffect.create(StatusEffectCategory.HARMFUL,(instance,entity)->{
        if(!entity.getWorld().isClient && instance.getAmplifier() >1){
            World world = entity.getWorld();
            Box box = new Box(entity.getX()-8,entity.getY()-5, entity.getZ()-8, entity.getX() +8,entity.getY()+5,entity.getZ()+8);
            List<Entity> list = world.getOtherEntities(entity,box,(target) -> {
                if (target instanceof LivingEntity living){
                    if (living instanceof PlayerEntity) return false;
                    return !living.hasStatusEffect(ModEffects.SHOCK_EFFECT);
                }
                return false;
            });
            if (!list.isEmpty()){
                LivingEntity live = (LivingEntity) list.get(0);
                live.setAttacker(entity.getAttacker());
                Vec3d entityPos = entity.getPos();
                Vec3d targetPos = live.getPos();
                Vec3d diff = entityPos.subtract(targetPos);
                Random r = new Random();
                for (int i=0;i<10;i++){

                    ((ServerWorld)world).spawnParticles(ModParticles.ELECTRIC_SHOCK, entityPos.x -(diff.x*i/10)+r.nextInt(-5,5)*0.02,entityPos.y-(diff.y*i/10)+r.nextInt(-5,5)*0.02+0.75,entityPos.z-(diff.z*i/10)+r.nextInt(-5,5)*0.02,1,0,0,0,0);
                }
                live.addStatusEffect(new StatusEffectInstance(ModEffects.CONDUCTING_EFFECT,5,instance.getAmplifier()-1));
            }
        }
    },((entity, integer) -> {
        if (!entity.getWorld().isClient){
            World world = entity.getWorld();
            world.playSound(null,entity.getBlockPos(),ModSounds.ELECTRIC_STRIKE,SoundCategory.AMBIENT,4f,4f);
            entity.damage(world.getDamageSources().create(DamageTypes.MAGIC,entity.getLastAttacker()),4);
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.SHOCK_EFFECT,5*20),entity.getLastAttacker());
        }
    })));

    private static StatusEffect registerStatusEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(MOD_ID, name), effect);
    }

    public static void registerEffects() {
        EOTE.LOGGER.info("Registering status effects");
    }
}
