package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.item.IPlayerBoundItemCooldownManager;
import com.bajookie.echoes_of_the_elders.util.CooldownUtil;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(ItemCooldownManager.class)
public class ItemCooldownManagerMixin implements IPlayerBoundItemCooldownManager {

    @Unique
    private PlayerEntity playerEntity;

    @Unique
    public PlayerEntity eote$getPlayerEntity() {
        return this.playerEntity;
    }

    @Unique
    public void eote$setPlayerEntity(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
    }

    @ModifyVariable(method = "set", at = @At("HEAD"), argsOnly = true)
    public int setCooldown(int duration, Item item) {

        if (playerEntity == null) return duration;

        return CooldownUtil.getReducedCooldown(playerEntity, item, duration);
    }
}
