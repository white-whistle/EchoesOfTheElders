package com.bajookie.echoes_of_the_elders.system.Capability;

import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerAccessor;
import com.bajookie.echoes_of_the_elders.mixin.ItemCooldownManagerEntryAccessor;
import com.bajookie.echoes_of_the_elders.util.ModIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class PersistentCooldownCapability extends Capability<PlayerEntity> {
    private static final String COOLDOWN_STATE_KEY = ModIdentifier.string("cooldown_state");

    public PersistentCooldownCapability(PlayerEntity self) {
        super(self);
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        if (this.self == null) return;
        var cdm = self.getItemCooldownManager();
        if (cdm instanceof ItemCooldownManagerAccessor a) {
            var nbtState = new NbtCompound();
            var nbtEntries = new NbtCompound();

            nbtState.putInt("tick", a.getTick());

            a.getEntries().forEach((k, v) -> {
                if (v instanceof ItemCooldownManagerEntryAccessor e) {
                    var nbtEntry = new NbtCompound();
                    nbtEntry.putInt("startTick", e.getStartTick());
                    nbtEntry.putInt("endTick", e.getEndTick());
                    nbtEntries.put(Registries.ITEM.getId(k).toString(), nbtEntry);
                }
            });

            nbtState.put("entries", nbtEntries);

            nbt.put(COOLDOWN_STATE_KEY, nbtState);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        if (this.self == null) return;

        var cdm = self.getItemCooldownManager();
        if (cdm instanceof ItemCooldownManagerAccessor a) {
            var entries = a.getEntries();

            var nbtState = (NbtCompound) nbt.get(COOLDOWN_STATE_KEY);
            if (nbtState == null) return;

            var nbtEntries = (NbtCompound) nbtState.get("entries");
            if (nbtEntries == null) return;

            a.setTick(nbtState.getInt("tick"));

            nbtEntries.getKeys().forEach(k -> {
                var nbtEntry = (NbtCompound) nbtEntries.get(k);
                if (nbtEntry == null) return;

                var startTick = nbtEntry.getInt("startTick");
                var endTick = nbtEntry.getInt("endTick");

                var entry = ItemCooldownManagerEntryAccessor.createEntry(startTick, endTick);
                var item = Registries.ITEM.get(new Identifier(k));

                entries.put(item, entry);
            });
        }

    }
}
