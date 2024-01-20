package com.bajookie.echoes_of_the_elders.mixin;

import com.bajookie.echoes_of_the_elders.system.Capability.Capabilities;
import com.bajookie.echoes_of_the_elders.system.Capability.IHasCapability;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityCapabilityMixin implements IHasCapability {

    @Unique
    private Capabilities capabilities;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomNbt(NbtCompound nbt, CallbackInfo ci) {
        Capabilities.writeCapabilities(capabilities, nbt);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        var entity = (LivingEntity) (Object) this;
        capabilities = Capabilities.readCapabilities(nbt, entity);
    }

    @Override
    public boolean echoesOfTheElders$hasCapabilities() {
        return capabilities != null;
    }

    @Override
    public Capabilities echoesOfTheElders$getCapabilities() {
        return capabilities;
    }

    @Override
    public void echoesOfTheElders$setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

}
