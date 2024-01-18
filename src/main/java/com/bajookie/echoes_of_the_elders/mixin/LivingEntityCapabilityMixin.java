package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.Capabilities;
import com.bajookie.echoes_of_the_elders.system.Capability.Capability;
import com.bajookie.echoes_of_the_elders.system.Capability.IHasCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(LivingEntity.class)
public class LivingEntityCapabilityMixin implements IHasCapability {

    @Unique
    private HashMap<String, Capability> capabilities;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomNbt(NbtCompound nbt, CallbackInfo ci) {
        Capabilities.writeCapabilities(capabilities, nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        capabilities = Capabilities.readCapabilities(nbt);
    }

    @Override
    public boolean echoesOfTheElders$hasCapabilities() {
        return capabilities != null;
    }

    @Override
    public HashMap<String, Capability> echoesOfTheElders$getCapabilities() {
        return capabilities;
    }

    @Override
    public void echoesOfTheElders$setCapabilities(HashMap<String, Capability> capabilities) {
        this.capabilities = capabilities;
    }

}
