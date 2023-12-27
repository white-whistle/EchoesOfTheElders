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

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(MOD_ID,name);
        return Registry.register(Registries.SOUND_EVENT,id,SoundEvent.of(id));
    }
    public static void registerSounds(){
        EOTE.LOGGER.info("Registering sounds for ---> "+MOD_ID);
    }
}
