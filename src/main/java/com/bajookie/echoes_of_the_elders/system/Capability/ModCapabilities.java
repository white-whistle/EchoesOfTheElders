package com.bajookie.echoes_of_the_elders.system.Capability;

import com.bajookie.echoes_of_the_elders.system.Raid.RaidEnemyCapability;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModCapabilities {
    public static Map<String, Supplier<Capability>> lookup = new HashMap<>();

    public static CapabilityWrapper<RaidEnemyCapability> RAID_ENEMY = registerCapability("raid_enemy", RaidEnemyCapability::new);

    public static <T extends Capability> CapabilityWrapper<T> registerCapability(String name, Supplier<T> factory) {
        var namespaceName = ModIdentifier.string(name);

        // noinspection unchecked
        lookup.put(namespaceName, (Supplier<Capability>) factory);

        return new CapabilityWrapper<>(namespaceName);
    }

    public static class CapabilityWrapper<T extends Capability> {
        public String name;

        CapabilityWrapper(String name) {
            this.name = name;
        }

        public void use(Object o, Consumer<T> fn) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return;

                var capability = capabilities.get(this.name);
                if (capability == null) return;

                // noinspection unchecked
                fn.accept((T) capability);
            }
        }

        public void attach(Object o, @Nullable Consumer<T> init) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getOrCreateCapabilities();

                var capabilityFactory = ModCapabilities.lookup.get(this.name);
                if (capabilityFactory == null) return;

                var capability = capabilityFactory.get();

                if (init != null) {
                    // noinspection unchecked
                    init.accept((T) capability);
                }

                capabilities.put(this.name, capability);
            }
        }

        public void attach(Object o) {
            this.attach(o, null);
        }
    }
}
