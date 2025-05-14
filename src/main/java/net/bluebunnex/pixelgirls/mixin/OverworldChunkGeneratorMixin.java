package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.entity.WomanEntity;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
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

//        Biome biome = this.world.method_1781().getBiome(blockX, blockZ);
//
//        if (biome == Biome.DESERT) {
//            world.setBlock(blockX, world.getTopSolidBlockY(blockX, blockZ), blockZ, Block.GOLD_BLOCK.id);
//        } else {
//            world.setBlock(blockX, world.getTopSolidBlockY(blockX, blockZ), blockZ, Block.IRON_BLOCK.id);
//        }

        if (random.nextInt(32) == 0) {

            int blockX = x * 16 + this.random.nextInt(16);
            int blockZ = z * 16 + this.random.nextInt(16);
            int blockY = world.getTopSolidBlockY(blockX, blockZ) - 1;

            placeHouse(blockX, blockY, blockZ);
        }
    }

    @Unique
    private void placeHouse(int blockX, int blockY, int blockZ) {

        // floor and ceiling
        for (int xo = 0; xo < 5; xo++) {
            for (int zo = 0; zo < 5; zo++) {

                world.setBlock(blockX + xo, blockY, blockZ + zo, Block.COBBLESTONE.id);
                world.setBlock(blockX + xo, blockY + 4, blockZ + zo, Block.PLANKS.id);

                world.setBlock(blockX + xo, blockY + 5, blockZ + zo, Block.SLAB.id);
                world.setBlockMeta(blockX + xo, blockY + 5, blockZ + zo, 3);
            }
        }

        // walls
        for (int yo = 1; yo < 4; yo++) {

            world.setBlock(blockX, blockY + yo, blockZ + 1, Block.PLANKS.id);
            world.setBlock(blockX, blockY + yo, blockZ + 2, Block.PLANKS.id);
            world.setBlock(blockX, blockY + yo, blockZ + 3, Block.PLANKS.id);

            world.setBlock(blockX + 4, blockY + yo, blockZ + 1, Block.PLANKS.id);
            world.setBlock(blockX + 4, blockY + yo, blockZ + 2, Block.PLANKS.id);
            world.setBlock(blockX + 4, blockY + yo, blockZ + 3, Block.PLANKS.id);

            world.setBlock(blockX + 1, blockY + yo, blockZ, Block.PLANKS.id);
            world.setBlock(blockX + 2, blockY + yo, blockZ, Block.PLANKS.id);
            world.setBlock(blockX + 3, blockY + yo, blockZ, Block.PLANKS.id);

            world.setBlock(blockX + 1, blockY + yo, blockZ + 4, Block.PLANKS.id);
            world.setBlock(blockX + 2, blockY + yo, blockZ + 4, Block.PLANKS.id);
            world.setBlock(blockX + 3, blockY + yo, blockZ + 4, Block.PLANKS.id);
        }

        world.setBlock(blockX + 2, blockY + 2, blockZ + 4, 0);
        world.setBlock(blockX + 4, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX + 2, blockY + 2, blockZ, 0);
        world.setBlock(blockX + 2, blockY + 1, blockZ, 0);

        world.setBlock(blockX + 2, blockY + 3, blockZ + 3, Block.TORCH.id);

        // log supports
        for (int yo = 0; yo < 5; yo++) {

            world.setBlock(blockX + 4, blockY + yo, blockZ, Block.LOG.id);
            world.setBlock(blockX + 4, blockY + yo, blockZ + 4, Block.LOG.id);
            world.setBlock(blockX, blockY + yo, blockZ, Block.LOG.id);
            world.setBlock(blockX, blockY + yo, blockZ + 4, Block.LOG.id);
        }

        WomanEntity resident = new WomanEntity(world);
        resident.setPosition(blockX + 2, blockY + 1, blockZ + 2);
        world.spawnEntity(resident);
    }
}