package com.bajookie.echoes_of_the_elders.system.Capability;

import net.minecraft.nbt.NbtCompound;

public interface Capability {

    void writeToNbt(NbtCompound nbt);

    void readFromNbt(NbtCompound nbt);
}
