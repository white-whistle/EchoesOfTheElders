package com.bajookie.echoes_of_the_elders.system.Capability;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;

import java.util.HashMap;

public interface IHasCapability {
    String CAPABILITIES_KEY = ModIdentifier.string("capabilities");

    boolean echoesOfTheElders$hasCapabilities();

    HashMap<String, Capability> echoesOfTheElders$getCapabilities();

    default HashMap<String, Capability> echoesOfTheElders$getOrCreateCapabilities() {
        if (!echoesOfTheElders$hasCapabilities()) {
            echoesOfTheElders$setCapabilities(new HashMap<>());
        }

        return echoesOfTheElders$getCapabilities();
    }

    void echoesOfTheElders$setCapabilities(HashMap<String, Capability> capabilities);
}
