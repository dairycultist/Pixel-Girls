package net.bluebunnex.pixelgirls.mixin;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

    @Shadow
    private World world;

    @Shadow
    private Random random;

    @Inject(method = "decorate", at = @At("HEAD"))
    public void decorateMixin(ChunkSource source, int x, int z, CallbackInfo ci) {

        int blockX = x * 16 + this.random.nextInt(16);
        int blockZ = z * 16 + this.random.nextInt(16);

        Biome biome = this.world.method_1781().getBiome(blockX, blockZ);

        if (biome == Biome.DESERT) {
            world.setBlock(blockX, world.getTopSolidBlockY(blockX, blockZ), blockZ, Block.GOLD_BLOCK.id);
        } else {
            world.setBlock(blockX, world.getTopSolidBlockY(blockX, blockZ), blockZ, Block.IRON_BLOCK.id);
        }
    }
}