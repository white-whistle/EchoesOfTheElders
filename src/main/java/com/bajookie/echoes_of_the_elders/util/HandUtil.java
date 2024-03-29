package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;

public class HandUtil {

    public static Hand stackToHand(LivingEntity livingEntity, ItemStack stack) {
        if (livingEntity.getMainHandStack() == stack) return Hand.MAIN_HAND;
        return Hand.OFF_HAND;
    }

    public static Arm handToArm(LivingEntity livingEntity, Hand hand) {
        var mainArm = livingEntity.getMainArm();

        if (hand == Hand.MAIN_HAND) return mainArm;
        return mainArm.getOpposite();
    }

    public static int getHandDir(LivingEntity livingEntity, Hand hand) {
        var arm = HandUtil.handToArm(livingEntity, hand);

        return arm == Arm.RIGHT ? 1 : -1;
    }

}
