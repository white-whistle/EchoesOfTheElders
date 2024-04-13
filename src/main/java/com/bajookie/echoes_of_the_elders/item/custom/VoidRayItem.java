package com.bajookie.echoes_of_the_elders.item.custom;

import com.bajookie.echoes_of_the_elders.datagen.ModModelProvider;
import com.bajookie.echoes_of_the_elders.entity.ModDamageSources;
import com.bajookie.echoes_of_the_elders.item.ArtifactItemSettings;
import com.bajookie.echoes_of_the_elders.item.ability.Ability;
import com.bajookie.echoes_of_the_elders.item.reward.DropCondition;
import com.bajookie.echoes_of_the_elders.item.reward.IRaidReward;
import com.bajookie.echoes_of_the_elders.particles.ZapParticleEffect;
import com.bajookie.echoes_of_the_elders.sound.ModSounds;
import com.bajookie.echoes_of_the_elders.system.StackedItem.StackedItemStat;
import com.bajookie.echoes_of_the_elders.system.Text.TextArgs;
import com.bajookie.echoes_of_the_elders.util.HandUtil;
import com.bajookie.echoes_of_the_elders.util.VectorUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.client.Model;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

@DropCondition.RaidLevelBetween(min = 10, max = 40)
public class VoidRayItem extends Item implements IArtifact, IStackPredicate, IRaidReward {
    public static StackedItemStat.Float RAY_DAMAGE = new StackedItemStat.Float(2f, 8f);

    public VoidRayItem() {
        super(new ArtifactItemSettings());
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        return super.use(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient && remainingUseTicks % 4 == 0) {

            var activeHand = user.getActiveHand();
            var dir = HandUtil.getHandDir(user, activeHand);

            Vec3d startPos = user.getEyePos();
            Vec3d _end = new Vec3d(VectorUtil.pitchYawRollToDirection(user.getPitch(), user.getYaw(), user.getRoll())).normalize().multiply(8);
            Vec3d end = startPos.add(_end);
            ServerWorld serverWorld = (ServerWorld) world;
            var up = new Vector3f(0, 1, 0);
            var forward = new Vector3f(end.toVector3f()).sub(startPos.toVector3f()).normalize();
            var right = new Vector3f(forward).cross(up).normalize();
            serverWorld.spawnParticles(new ZapParticleEffect(
                    new Vector3f((float) (startPos.x), (float) (startPos.y), (float) (startPos.z)).add(up.mul(-0.2f)).add(right.mul(0.35f * dir).add(forward.mul(0.45f))),
                    new Vector3f((float) (end.x), (float) (end.y), (float) (end.z)),
                    new Vector3f(92f / 255, 5f / 255, 179f / 255)
            ), startPos.x, startPos.y, startPos.z, 1, 0, 0, 0, 0);
            EntityHitResult hit = VectorUtil.raycast(user, 6, 3, 3);
            if (hit != null && (hit.getEntity() instanceof LivingEntity living)) {
                living.damage(ModDamageSources.echoAttack(user), RAY_DAMAGE.get(stack));
            }
        }
        if (remainingUseTicks % 4 == 0) {
            world.playSound(user.getX(), user.getY(), user.getZ(), ModSounds.ZEPHYR_SOUND, SoundCategory.PLAYERS, 4f, 1f, false);
        }
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public Model getBaseModel() {
        return ModModelProvider.GUN;
    }

    public static final Ability VOID_RAY_ABILITY = new Ability("void_ray", Ability.AbilityType.ACTIVE, Ability.AbilityTrigger.RIGHT_CLICK) {
        @Override
        public void appendTooltipInfo(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, TooltipSectionContext section) {
            section.line("info1", new TextArgs().putF("damage", RAY_DAMAGE.get(stack)));
            section.line("info2");
        }
    };

    public static final List<Ability> ABILITIES = List.of(VOID_RAY_ABILITY);

    @Override
    public List<Ability> getAbilities(ItemStack itemStack) {
        return ABILITIES;
    }
}
