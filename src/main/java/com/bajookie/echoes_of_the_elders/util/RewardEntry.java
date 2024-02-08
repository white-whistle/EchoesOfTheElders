package com.bajookie.echoes_of_the_elders.util;

public class RewardEntry<T> {
    private final int weight;
    private final int min;
    private final int max;
    private final T reward;

    public RewardEntry(int weight,int min,int max,T reward){
        this.weight = weight;
        this.min = min;
        this.max = max;
        this.reward = reward;
    }
}
