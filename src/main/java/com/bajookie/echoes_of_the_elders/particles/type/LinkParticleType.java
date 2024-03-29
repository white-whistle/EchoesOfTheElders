package com.bajookie.echoes_of_the_elders.particles.type;

import com.bajookie.echoes_of_the_elders.particles.LinkParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.client.option.GameOptions;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class LinkParticleType extends ParticleType<LinkParticleEffect> {
    public LinkParticleType(boolean alwaysShow, ParticleEffect.Factory<LinkParticleEffect> parametersFactory) {
        super(alwaysShow, parametersFactory);
    }

    @Override
    public Codec<LinkParticleEffect> getCodec() {
        return LinkParticleEffect.CODEC;
    }
}
