package com.bajookie.echoes_of_the_elders.entity;

import com.bajookie.echoes_of_the_elders.entity.custom.*;
import com.bajookie.echoes_of_the_elders.item.ModItems;
import com.bajookie.echoes_of_the_elders.item.custom.TvArrow;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModEntities {
    public static final EntityType<FlowerDefenseEntity> FLOWER_DEFENSE_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "flower_defense_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, FlowerDefenseEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static final EntityType<SpiritEntity> SPIRIT_ENTITY_KEY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "spirit_entity"), SpiritEntity.createEntityType(List.of(Items.CAKE,Items.BREAD,ModItems.OLD_KEY)));

    public static final EntityType<EldermanEntity> ELDERMAN_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "elderman_entity"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, EldermanEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 2.9f)).trackRangeChunks(8).build());

    public static final EntityType<SecondSunProjectileEntity> SECOND_SUN_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "second_sun_entity"),
            FabricEntityTypeBuilder.<SecondSunProjectileEntity>create(SpawnGroup.CREATURE, SecondSunProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    public static final EntityType<ChainLightningProjectileEntity> CHAIN_LIGHTNING_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "chain_lightning_entity"),
            FabricEntityTypeBuilder.<ChainLightningProjectileEntity>create(SpawnGroup.CREATURE, ChainLightningProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<VacuumProjectileEntity> VACUUM_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "vacuum_projectile_entity"),
            FabricEntityTypeBuilder.<VacuumProjectileEntity>create(SpawnGroup.CREATURE, VacuumProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<TeleportEyeProjectileEntity> TELEPORT_EYE_PROJECTILE_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "teleport_eye_entity"),
            FabricEntityTypeBuilder.<TeleportEyeProjectileEntity>create(SpawnGroup.CREATURE, TeleportEyeProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<AirSweeperProjectileEntity> AIR_SWEEPER_PROJECTILE_ENTITY_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "air_sweeper_entity"),
            FabricEntityTypeBuilder.<AirSweeperProjectileEntity>create(SpawnGroup.CREATURE, AirSweeperProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<MagmaBullet> MAGMA_BULLET_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "magma_bullet_entity"),
            FabricEntityTypeBuilder.<MagmaBullet>create(SpawnGroup.CREATURE, MagmaBullet::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());
    public static final EntityType<TvArrowEntity> TV_ARROW_ENTITY_ENTITY_TYPE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "tv_arrow_entity"),
            FabricEntityTypeBuilder.<TvArrowEntity>create(SpawnGroup.CREATURE, TvArrowEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build());

    /**
     * Register Mob Attributes here:
     */
    public static void registerMobAttributes() {
        FabricDefaultAttributeRegistry.register(ModEntities.FLOWER_DEFENSE_ENTITY, FlowerDefenseEntity.createFlowerDefenseAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.SPIRIT_ENTITY_KEY, SpiritEntity.createSpiritEntityAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.ELDERMAN_ENTITY, EldermanEntity.createEndermanAttributes());
    }
}