package com.bajookie.echoes_of_the_elders.screen.client.particles;

import com.bajookie.echoes_of_the_elders.util.TweenUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector2i;

public class SimpleParticle extends LifetimeParticle {
    public final Vector2f pos;
    public final Vector2f velocity = new Vector2f();
    public final Identifier texture;
    public final Vector2i textureSize;

    public float scale = 0;
    public float rotation = 0;

    private final Vector2f prevPos;
    private float prevScale = 0;
    private float prevRotation = 0;

    public SimpleParticle(int lifetime, Vector2f pos, Identifier texture, Vector2i textureSize) {
        super(lifetime);
        this.pos = pos;
        this.prevPos = new Vector2f(pos);
        this.texture = texture;
        this.textureSize = textureSize;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevScale = scale;
        this.prevRotation = this.rotation;
        this.pos.add(velocity);
    }

    @Override
    public void render(Screen screen, DrawContext drawContext, int mouseX, int mouseY, float tickDelta) {
        var cScale = TweenUtil.lerp(this.prevScale, this.scale, tickDelta);
        var cRot = TweenUtil.lerp(this.prevRotation, this.rotation, tickDelta);
        var cPos = this.prevPos.lerp(this.pos, tickDelta);

        var matrix = drawContext.getMatrices();
        matrix.push();
        matrix.translate(cPos.x, cPos.y, 0);
        matrix.multiply(new Quaternionf().rotationZ((float) Math.toRadians(cRot)));
        matrix.translate(-((this.textureSize.x * cScale) / 2), -(((this.textureSize.y * cScale) / 2)), 0);
        matrix.scale(cScale, cScale, 1);
        drawContext.drawTexture(texture, 0, 0, 0, 0, this.textureSize.x, this.textureSize.y, this.textureSize.x, this.textureSize.y);
        matrix.pop();
    }

    public SimpleParticle randomizeVelocity(float size) {

        this.velocity.set(VectorUtil.randomDirection().mul(size));

        return this;
    }
}