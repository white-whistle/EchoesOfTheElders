package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.item.Item;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Triplet;

import java.util.*;

public class AlgebraHelper {
    public <T> T getItemByWeight(@NotNull T[] items, int[] weight) {
        if (weight.length != items.length)
            throw new IllegalArgumentException(String.format("Number of items does not equal to the number of Weights! Got %d weights but %d items", weight.length, items.length));
        if (weight.length == 0) throw new NullPointerException("Arrays cant be empty!");
        int initialLength = weight.length;
        weight = Arrays.stream(weight).filter(value -> value > 0).toArray();
        if (initialLength != weight.length) throw new IllegalArgumentException("Cannot assign non positive weight!");
        int sum = Arrays.stream(weight).sum();
        Random r = new Random();
        int target = r.nextInt(sum) + 1;
        Iterator<Integer> it = Arrays.stream(weight).iterator();
        int total = 0;
        int pos = 0;
        while (it.hasNext()) {
            total = total + it.next();
            if (total <= target) return items[pos];
            pos++;
        }
        return items[0];
    }

    public static int randomUniform(int startInclude, int endInclude) {
        Random r = new Random();
        return r.nextInt(startInclude, endInclude + 1);
    }

    public static List<Pair<Item, Integer>> rollRewards(Map<Item, Triplet<Integer, Integer, Integer>> rewardMap) {
        List<Pair<Item, Integer>> list = new ArrayList<>();
        Random r = new Random();
        int sumWeight = rewardMap.values().stream().mapToInt(Triplet::getC).sum();
        rewardMap.forEach((item, triplet) -> {
            int target = r.nextInt(sumWeight)+1;
            if (triplet.getC() < target || triplet.getA()>0){
                list.add(new Pair<>(item,r.nextInt(triplet.getA(), triplet.getB()+1)));
            }
        });
        return list;
    }

    public static int getBinomial(int n, double p) {
        double log_q = Math.log(1.0 - p);
        int x = 0;
        double sum = 0;
        for (; ; ) {
            sum += Math.log(Math.random()) / (n - x);
            if (sum < log_q) {
                return x;
            }
            x++;
        }
    }

}
