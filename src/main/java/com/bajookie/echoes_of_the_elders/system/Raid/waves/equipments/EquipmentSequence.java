package com.bajookie.echoes_of_the_elders.system.Raid.waves.equipments;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EquipmentSequence {
    private final int minLevel;
    private final EquipmentSlot slot;
    private final List<Pair<Integer, Item>> items;

    private EquipmentSequence(EquipmentSlot slot, int minLevel,List<Pair<Integer, Item>> items) {
        this.minLevel = minLevel;
        this.slot = slot;
        this.items = items;
    }

    public void equip(LivingEntity living, int level) {
        Iterator<Pair<Integer, Item>> it = this.items.iterator();
        int pos = this.minLevel;
        while (it.hasNext()) {
            Pair<Integer, Item> pair = it.next();
            pos += pair.getA();
            if (level <= pos) {
                living.equipStack(slot, pair.getB().getDefaultStack());
            }
        }
    }

    public static class SequenceBuilder {
        private int min;
        private EquipmentSlot equipmentSlot;
        private List<Pair<Integer, Item>> items = new ArrayList<>();

        public SequenceBuilder(int minLevel, EquipmentSlot slot) {
            this.min = minLevel;
            this.equipmentSlot = slot;
        }

        public SequenceBuilder addEquipment(Item item, int maxLevelToUse) {
            this.items.add(new Pair<>(maxLevelToUse, item));
            return this;
        }
        public EquipmentSequence build(){
            return new EquipmentSequence(this.equipmentSlot, this.min,this.items);
        }
    }
}
