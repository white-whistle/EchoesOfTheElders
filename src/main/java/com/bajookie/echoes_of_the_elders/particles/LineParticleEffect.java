package com.bajookie.echoes_of_the_elders.particles;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.AbstractDustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.dynamic.Codecs;
import org.joml.Vector3f;

import java.util.Locale;

public class LineParticleEffect implements ParticleEffect {

    public Vector3f p1;
    public Vector3f p2;
    public Vector3f color;

    public LineParticleEffect(Vector3f p1, Vector3f p2, Vector3f color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    @Override
    public ParticleType<LineParticleEffect> getType() {
        return ModParticles.LINE_PARTICLE_TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeVector3f(p1);
        buf.writeVector3f(p2);
        buf.writeVector3f(color);
    }

    @Override
    public String asString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %.2f %.2f %.2f %.2f %.2f",
                Registries.PARTICLE_TYPE.getId(this.getType()),

                this.p1.x(),
                this.p1.y(),
                this.p1.z(),

                this.p2.x(),
                this.p2.y(),
                this.p2.z(),

                this.color.x(),
                this.color.y(),
                this.color.z()
        );
    }

    public static final Codec<LineParticleEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codecs.VECTOR_3F.fieldOf("p1").forGetter(i -> i.p1),
            Codecs.VECTOR_3F.fieldOf("p2").forGetter(i -> i.p2),
            Codecs.VECTOR_3F.fieldOf("color").forGetter(i -> i.color)
    ).apply(instance, LineParticleEffect::new));

    public static final ParticleEffect.Factory<LineParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<>() {

        @Override
        public LineParticleEffect read(ParticleType<LineParticleEffect> type, StringReader reader) throws CommandSyntaxException {
            Vector3f p1 = AbstractDustParticleEffect.readColor(reader);
            reader.expect(' ');
            Vector3f p2 = AbstractDustParticleEffect.readColor(reader);
            reader.expect(' ');
            Vector3f color = AbstractDustParticleEffect.readColor(reader);

            return new LineParticleEffect(p1, p2, color);
        }

        @Override
        public LineParticleEffect read(ParticleType<LineParticleEffect> type, PacketByteBuf buf) {
            return new LineParticleEffect(buf.readVector3f(), buf.readVector3f(), buf.readVector3f());
        }
    };
}
