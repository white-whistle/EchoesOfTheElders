package com.bajookie.echoes_of_the_elders.mixin;


import com.bajookie.echoes_of_the_elders.world.dimension.ModDimensions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGenerator.class)
public class ChunkGeneratorMixin {
    @Inject(method = "generateFeatures",at = @At("TAIL"))
    private void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo info) {
        if (world.getDimension() == world.getRegistryManager().get(RegistryKeys.DIMENSION_TYPE).get(ModDimensions.DEFENSE_DIM_TYPE)){
            if (chunk.getPos().x == 0 && chunk.getPos().z == 0){
                if (world.getServer() != null){
                    MinecraftServer server = world.getServer();
                    server.getCommandManager().executeWithPrefix(server.getCommandSource(),"/place structure echoes_of_the_elders:hub_struct 0 90 0"); // not spawning
                    System.out.println("committed");
                }
            }
        }
    }}
