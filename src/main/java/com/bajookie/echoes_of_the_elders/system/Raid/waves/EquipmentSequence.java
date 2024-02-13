package com.bajookie.echoes_of_the_elders.system.Raid.waves;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EquipmentSequence {
    private final int minLevel;
    private final EquipmentSlot slot;
    private final List<Pair<Integer, ItemStack>> items;

    private EquipmentSequence(EquipmentSlot slot, int minLevel,List<Pair<Integer, ItemStack>> items) {
        this.minLevel = minLevel;
        this.slot = slot;
        this.items = items;
    }

    public void equip(LivingEntity living, int level) {
        Iterator<Pair<Integer, ItemStack>> it = this.items.iterator();
        int pos = this.minLevel;
        while (it.hasNext()) {
            Pair<Integer, ItemStack> pair = it.next();
            pos = pair.getA();
            if (level <= pos && level>=minLevel) {
                living.equipStack(slot, pair.getB());
                return;
            }
        }
        if (level>= pos){
            living.equipStack(this.slot,this.items.get(this.items.size()-1).getB());
        }
    }

    public static class SequenceBuilder {
        private int min;
        private EquipmentSlot equipmentSlot;
        private List<Pair<Integer, ItemStack>> items = new ArrayList<>();

        public SequenceBuilder(int minLevel, EquipmentSlot slot) {
            this.min = minLevel;
            this.equipmentSlot = slot;
        }

        public SequenceBuilder addEquipment(ItemStack item, int maxLevelToUse) {
            this.items.add(new Pair<>(maxLevelToUse, item));
            return this;
        }
        public EquipmentSequence build(){
            return new EquipmentSequence(this.equipmentSlot, this.min,this.items);
        }
    }
}
