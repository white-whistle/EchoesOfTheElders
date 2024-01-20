package com.bajookie.echoes_of_the_elders.system.Capability;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Capabilities extends HashMap<String, Capability<?>> {

    public static void writeCapabilities(@Nullable Capabilities capabilities, NbtCompound compound) {
        if (capabilities == null) return;

        var capabilitiesNbt = new NbtCompound();

        capabilities.forEach((k, v) -> {
            var capabilityNbt = new NbtCompound();

            v.writeToNbt(capabilityNbt);

            capabilitiesNbt.put(k, capabilityNbt);
        });

        compound.put(IHasCapability.CAPABILITIES_KEY, capabilitiesNbt);
    }

    public static Capabilities readCapabilities(NbtCompound compound, @Nullable Object capabilityHolder) {
        if (!compound.contains(IHasCapability.CAPABILITIES_KEY)) return null;

        var map = new Capabilities();

        var capabilitiesNbt = (NbtCompound) compound.get(IHasCapability.CAPABILITIES_KEY);
        if (capabilitiesNbt == null) return null;

        capabilitiesNbt.getKeys().forEach(k -> {
            var capabilityNbt = (NbtCompound) capabilitiesNbt.get(k);

            var capabilityFactory = ModCapabilities.lookup.get(k);
            var capability = capabilityFactory.apply(capabilityHolder);

            capability.readFromNbt(capabilityNbt);

            map.put(k, capability);
        });

        return map;
    }
}
