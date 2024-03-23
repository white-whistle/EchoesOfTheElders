package com.bajookie.echoes_of_the_elders.screen.client.particles.imps;

import com.bajookie.echoes_of_the_elders.screen.client.particles.ScreenParticle;
import com.bajookie.echoes_of_the_elders.screen.client.particles.SimpleParticle;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.util.Identifier;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class LightningParticle extends SimpleParticle {
    public static final Identifier TEXTURE = new ModIdentifier("textures/font/lightning.png");

    public LightningParticle(Vector2f pos) {
        super(
                ScreenParticle.random.nextInt(10, 20),
                pos,
                TEXTURE,
                new Vector2i(9, 8)
        );
    }

    @Override
    public void tick() {
        super.tick();

        float progress = getProgress();
        float sizeScalar = (float) Math.cos(progress * Math.PI / 2);

        this.scale = sizeScalar;

        this.velocity.mul(sizeScalar * sizeScalar * sizeScalar);

        this.rotation += this.velocity.x * 50;
    }
}
