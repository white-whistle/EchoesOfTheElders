package com.bajookie.echoes_of_the_elders.particles;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModParticles {

    public static final DefaultParticleType SECOND_SUN_PARTICLE = registerParticle(
            "second_sun_particle", FabricParticleTypes.simple()
    );

    private static DefaultParticleType registerParticle(String name,DefaultParticleType particleType){
        return Registry.register(Registries.PARTICLE_TYPE,new Identifier(MOD_ID,name),particleType);
    }
    public static void registerParticles(){
        EOTE.LOGGER.info("Registering mod particles for ---> "+MOD_ID);
    }
}
