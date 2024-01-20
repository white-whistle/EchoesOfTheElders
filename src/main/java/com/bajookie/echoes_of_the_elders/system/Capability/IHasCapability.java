package com.bajookie.echoes_of_the_elders.system.Capability;

import com.bajookie.echoes_of_the_elders.util.ModIdentifier;

public interface IHasCapability {
    String CAPABILITIES_KEY = ModIdentifier.string("capabilities");

    boolean echoesOfTheElders$hasCapabilities();

    Capabilities echoesOfTheElders$getCapabilities();

    default Capabilities echoesOfTheElders$getOrCreateCapabilities() {
        if (!echoesOfTheElders$hasCapabilities()) {
            echoesOfTheElders$setCapabilities(new Capabilities());
        }

        return echoesOfTheElders$getCapabilities();
    }

    void echoesOfTheElders$setCapabilities(Capabilities capabilities);
}
