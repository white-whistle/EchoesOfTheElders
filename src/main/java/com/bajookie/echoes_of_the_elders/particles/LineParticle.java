package com.bajookie.echoes_of_the_elders.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class LineParticle extends SpriteBillboardParticle {
    public Vector3f p1;
    public Vector3f p2;
    public Vector3f op1;
    public Vector3f op2;
    public Vector3f color;
    public Vector3f ocolor;
    public float minSize = 0.3f;
    public float maxSize = 1.5f;
    public float size = minSize;

    protected LineParticle(SpriteProvider spriteProvider, ClientWorld world, Vector3f p1, Vector3f p2, Vector3f color) {
        super(world, p1.x, p1.y, p1.z);

        this.p1 = p1;
        this.p2 = p2;
        this.op1 = new Vector3f(p1);
        this.op2 = new Vector3f(p2);
        this.color = color;
        this.ocolor = new Vector3f(color);
        this.maxAge = 30;
        this.alpha = 1;

        this.setSpriteForAge(spriteProvider);
    }

    public Vector3f[] createBillboardLine(Vector3f p1, Vector3f p2, Vector3f lookDir, float size) {
        var lineDir = new Vector3f(p2).sub(p1).normalize();
        var billboardN = new Vector3f(lookDir).sub(p1).normalize();
        var orthogonalDir = new Vector3f(billboardN).cross(lineDir).normalize().mul(-1);

        var halfSize = size / 2;
        var offset = new Vector3f(orthogonalDir).mul(halfSize);

        return new Vector3f[]{
                new Vector3f(p1).add(offset),
                new Vector3f(p2).add(offset),
                new Vector3f(p2).sub(offset),
                new Vector3f(p1).sub(offset)
        };
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (this.age / (float) this.maxAge);
        float rProgress = 1 - progress;

        this.p1 = new Vector3f(op1).add(new Vector3f(this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f).normalize().mul(this.random.nextFloat() * 0.2f));
        // this.p2 = new Vector3f(op2).add(new Vector3f(this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f, this.random.nextFloat() - 0.5f).normalize().mul(this.random.nextFloat() * 0.2f));
        this.alpha = rProgress;
        this.color = new Vector3f(this.ocolor).mul(rProgress * rProgress);
        this.size = ((maxSize - minSize) * rProgress) + minSize;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        var vector3fs = createBillboardLine(this.p1, this.p2, camera.getPos().toVector3f(), this.size);

        Vec3d vec3d = camera.getPos();

        float k = this.getMinU();
        float l = this.getMaxU();
        float m = this.getMinV();
        float n = this.getMaxV();
        int o = this.getBrightness(tickDelta);

        float f = -(float) vec3d.getX();
        float g = -(float) vec3d.getY();
        float h = -(float) vec3d.getZ();

        for (int j = 0; j < 4; ++j) {
            Vector3f vector3f = vector3fs[j];
            vector3f.add(f, g, h);
        }

        vertexConsumer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(l, n).color(this.color.x, this.color.y, this.color.z, 0).light(o).next();
        vertexConsumer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(l, m).color(this.color.x, this.color.y, this.color.z, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(k, m).color(this.color.x, this.color.y, this.color.z, this.alpha).light(o).next();
        vertexConsumer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(k, n).color(this.color.x, this.color.y, this.color.z, 0).light(o).next();
    }

    @Override
    protected int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<LineParticleEffect> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(LineParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LineParticle(spriteProvider, world, parameters.p1, parameters.p2, parameters.color);
        }
    }
}
