package com.bajookie.echoes_of_the_elders.system.Capability;

import com.bajookie.echoes_of_the_elders.system.Raid.RaidEnemyCapability;
import com.bajookie.echoes_of_the_elders.system.Raid.RaidObjectiveCapability;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ModCapabilities {
    public static Map<String, Function<Object, Capability<?>>> lookup = new HashMap<>();

    public static CapabilityWrapper<LivingEntity, RaidEnemyCapability> RAID_ENEMY = registerCapability("raid_enemy", RaidEnemyCapability::new);
    public static CapabilityWrapper<LivingEntity, RaidObjectiveCapability> RAID_OBJECTIVE = registerCapability("raid_objective", RaidObjectiveCapability::new);


    public static <H, T extends Capability<H>> CapabilityWrapper<H, T> registerCapability(String name, Function<H, T> factory) {
        var namespaceName = ModIdentifier.string(name);

        // noinspection unchecked
        lookup.put(namespaceName, (Function<Object, Capability<?>>) factory);

        return new CapabilityWrapper<>(namespaceName);
    }

    public static class CapabilityWrapper<H, T extends Capability<H>> {
        public String name;

        CapabilityWrapper(String name) {
            this.name = name;
        }

        public void use(H o, Consumer<T> fn) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getCapabilities();
                if (capabilities == null) return;

                var capability = capabilities.get(this.name);
                if (capability == null) return;

                // noinspection unchecked
                fn.accept((T) capability);
            }
        }

        public void attach(H o, @Nullable Consumer<T> init) {
            if (o instanceof IHasCapability iHasCapability) {
                var capabilities = iHasCapability.echoesOfTheElders$getOrCreateCapabilities();

                var capabilityFactory = ModCapabilities.lookup.get(this.name);
                if (capabilityFactory == null) return;

                var capability = capabilityFactory.apply(o);

                if (init != null) {
                    // noinspection unchecked
                    init.accept((T) capability);
                }

                capabilities.put(this.name, capability);
            }
        }

        public void attach(H o) {
            this.attach(o, null);
        }
    }
}
