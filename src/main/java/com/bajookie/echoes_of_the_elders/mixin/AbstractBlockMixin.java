package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Redirect(method = "calcBlockBreakingDelta", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getHardness(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)F"))
    public float getHardnessProxy(BlockState instance, BlockView blockView, BlockPos blockPos, BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float f = state.getHardness(world, pos);

        if (f == -1.0f) {
            var stack = player.getMainHandStack();
            if (stack.isOf(ModItems.REALITY_PICK)) return 3f;

            return -1.0f;
        }

        return f;
    }

}
