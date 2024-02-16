package com.bajookie.echoes_of_the_elders.sound;

import com.bajookie.echoes_of_the_elders.EOTE;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModSounds {

    public static final SoundEvent SPIRIT_AMBIENT = registerSoundEvent("spirit_entity_ambient");
    public static final SoundEvent SPIRIT_HURT = registerSoundEvent("spirit_entity_hurt");
    public static final SoundEvent SPIRIT_DEATH = registerSoundEvent("spirit_entity_death");
    public static final SoundEvent ELECTRIC_STRIKE = registerSoundEvent("electric_sound");
    public static final SoundEvent MINIGUN_CHARGE = registerSoundEvent("minigun_charge");
    public static final SoundEvent MINIGUN_FIRE = registerSoundEvent("minigun_fire");
    public static final SoundEvent MINIGUN_FIRE2 = registerSoundEvent("minigun_fire2");
    public static final SoundEvent MINIGUN_END = registerSoundEvent("minigun_end");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }
    public static void registerSounds(){
        EOTE.LOGGER.info("Registering sounds for ---> "+MOD_ID);
    }
}
