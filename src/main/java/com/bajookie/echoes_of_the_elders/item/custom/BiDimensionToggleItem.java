package com.bajookie.echoes_of_the_elders.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BiDimensionToggleItem extends DimensionToggleItem {
    private final Pair<RegistryKey<World>, RegistryKey<World>> dimensions;

    public BiDimensionToggleItem(Settings settings, Pair<RegistryKey<World>, RegistryKey<World>> dimensions) {
        super(settings);

        this.dimensions = dimensions;
    }

    @Override
    public @Nullable RegistryKey<World> getNextDimension(ItemStack stack, World world, LivingEntity user) {

        var registryKey = user.getWorld().getRegistryKey();

        if (registryKey == dimensions.getLeft()) return dimensions.getRight();
        if (registryKey == dimensions.getRight()) return dimensions.getLeft();
        
        return null;
    }
}
