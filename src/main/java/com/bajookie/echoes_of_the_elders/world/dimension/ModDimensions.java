package com.bajookie.echoes_of_the_elders.world.dimension;

import net.minecraft.registry.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.OptionalLong;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> DEFENSE_DIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            new Identifier(MOD_ID, "defense_dim"));
    public static final RegistryKey<World> DEFENSE_DIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(MOD_ID, "defense_dim"));
    public static final RegistryKey<DimensionType> DEFENSE_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            new Identifier(MOD_ID, "defense_dim_type"));

    public static void bootstrapType(Registerable<DimensionType> context) {
        context.register(DEFENSE_DIM_TYPE, new DimensionType(
                OptionalLong.of(20000), // fixedTime
                true, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                true, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                -64, // minY
                256, // height
                256, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                7.5f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0), 0)));
    }
}
