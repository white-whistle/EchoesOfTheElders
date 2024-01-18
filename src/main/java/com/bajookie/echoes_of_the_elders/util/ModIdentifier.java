package com.bajookie.echoes_of_the_elders.util;

import net.minecraft.util.Identifier;

import static com.bajookie.echoes_of_the_elders.EOTE.MOD_ID;

public class ModIdentifier extends Identifier {
    public ModIdentifier(String id) {
        super(MOD_ID, id);
    }

    public static String string(String s) {
        return new ModIdentifier(s).toString();
    }
}
