package com.bajookie.echoes_of_the_elders.world.tree;

import com.bajookie.echoes_of_the_elders.EOTE;
import com.bajookie.echoes_of_the_elders.mixin.TrunkPlacerTypeInvoker;
import com.bajookie.echoes_of_the_elders.world.tree.trunks.AncientTreeTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class ModTrunkPlacerTypes {
    public static final TrunkPlacerType<?> ANCIENT_TREE_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister(
            "ancient_tree_trunk_placer", AncientTreeTrunkPlacer.CODEC);

    public static void register(){
        EOTE.LOGGER.info("Register custom Trunks");
    }
}
