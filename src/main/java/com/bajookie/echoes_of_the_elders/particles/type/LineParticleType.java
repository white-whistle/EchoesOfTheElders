package com.bajookie.echoes_of_the_elders.particles.type;

import com.bajookie.echoes_of_the_elders.particles.LineParticleEffect;
import com.mojang.serialization.Codec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public class LineParticleType extends ParticleType<LineParticleEffect> {

    public LineParticleType(boolean alwaysShow, ParticleEffect.Factory<LineParticleEffect> parametersFactory) {
        super(alwaysShow, parametersFactory);
    }

    @Override
    public Codec<LineParticleEffect> getCodec() {
        return LineParticleEffect.CODEC;
    }
}
