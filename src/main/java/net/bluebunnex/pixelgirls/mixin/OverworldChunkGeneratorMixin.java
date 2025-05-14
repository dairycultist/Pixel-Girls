package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.entity.WomanEntity;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
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

        if (random.nextInt(32) == 0) {

            int blockX = x * 16 + this.random.nextInt(16);
            int blockZ = z * 16 + this.random.nextInt(16);

            attemptToPlaceHouse(blockX, blockZ);
        }
    }

    @Unique
    private void attemptToPlaceHouse(int blockX, int blockZ) {

        int blockY = world.getTopSolidBlockY(blockX, blockZ) - 1;

        if (world.getBlockId(blockX, blockY, blockZ) != Block.GRASS_BLOCK.id &&
            world.getBlockId(blockX, blockY, blockZ) != Block.SAND.id)
            return;

        int floorId, wallId, columnId, roofMeta;

        switch (this.world.method_1781().getBiome(blockX, blockZ).name) {

            case "Desert":
                floorId = Block.SANDSTONE.id;
                wallId = Block.PLANKS.id;
                columnId = Block.SANDSTONE.id;
                roofMeta = 1;
                break;

            default:
                floorId = Block.COBBLESTONE.id;
                wallId = Block.PLANKS.id;
                columnId = Block.LOG.id;
                roofMeta = 3;
                break;
        }

        // floor and ceiling
        for (int xo = 0; xo < 5; xo++) {
            for (int zo = 0; zo < 5; zo++) {

                world.setBlock(blockX + xo, blockY, blockZ + zo, floorId);
                world.setBlock(blockX + xo, blockY + 4, blockZ + zo, wallId);

                world.setBlock(blockX + xo, blockY + 5, blockZ + zo, Block.SLAB.id);
                world.setBlockMeta(blockX + xo, blockY + 5, blockZ + zo, roofMeta);

                // walls
                if (xo == 0 || zo == 0 || xo == 4 || zo == 4)
                    for (int yo = 1; yo < 4; yo++)
                        world.setBlock(blockX + xo, blockY + yo, blockZ + zo, wallId);
                else
                    for (int yo = 1; yo < 4; yo++)
                        world.setBlock(blockX + xo, blockY + yo, blockZ + zo, 0);
            }
        }

        // door/windows
        world.setBlock(blockX + 2, blockY + 2, blockZ + 4, 0);
        world.setBlock(blockX + 4, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX + 2, blockY + 2, blockZ, 0);

        world.setBlock(blockX + 2, blockY + 1, blockZ, 0);

        // torch
        world.setBlock(blockX + 2, blockY + 3, blockZ + 3, Block.TORCH.id);

        // log supports
        for (int yo = 0; yo < 5; yo++) {

            world.setBlock(blockX + 4, blockY + yo, blockZ, columnId);
            world.setBlock(blockX + 4, blockY + yo, blockZ + 4, columnId);
            world.setBlock(blockX, blockY + yo, blockZ, columnId);
            world.setBlock(blockX, blockY + yo, blockZ + 4, columnId);
        }

        WomanEntity resident = new WomanEntity(world);
        resident.setPosition(blockX + 2, blockY + 1, blockZ + 2);
        world.spawnEntity(resident);
    }
}