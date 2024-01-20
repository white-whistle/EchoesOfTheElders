package com.bajookie.echoes_of_the_elders.system.Raid;

import net.minecraft.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaidWaves {

    public static List<RaidWave> registeredWaves = new ArrayList<>();

    public static RaidWave ZOMBIES = register(new RaidWave(0, 10, RaidSpawner.of(EntityType.ZOMBIE), RaidPositioner.random(10, 20)));
    public static RaidWave SKELETONS = register(new RaidWave(0, 5, RaidSpawner.of(EntityType.SKELETON), RaidPositioner.random(20, 30)));

    public static RaidWave getWave(int level) {
        Random r = new Random();
        return registeredWaves.get(r.nextInt(registeredWaves.size()));
    }

    public static RaidWave register(RaidWave raidWave) {
        registeredWaves.add(raidWave);
        return raidWave;
    }

}
