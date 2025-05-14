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

        if (random.nextInt(128) == 0) {

            int blockX = x * 16;
            int blockZ = z * 16;

            for (int i = 0; i < 5; i++)
                attemptToPlaceHouse(blockX + this.random.nextInt(32), blockZ + this.random.nextInt(32));
        }
    }

    @Unique
    private boolean isPermittedSurfaceBlock(int blockX, int blockY, int blockZ) {

        int blockId = world.getBlockId(blockX, blockY, blockZ);

        return blockId == Block.GRASS_BLOCK.id || blockId == Block.SAND.id;
    }

    @Unique
    private void attemptToPlaceHouse(int blockX, int blockZ) {

        int blockY = world.getTopSolidBlockY(blockX, blockZ) - 1;

        if (!(
            isPermittedSurfaceBlock(blockX, blockY, blockZ) &&
            isPermittedSurfaceBlock(blockX + 4, world.getTopSolidBlockY(blockX + 4, blockZ + 4) - 1, blockZ + 4)
            ))
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
        for (int x = blockX; x < blockX + 5; x++) {
            for (int z = blockZ; z < blockZ + 5; z++) {

                world.setBlock(x, blockY, z, floorId);
                world.setBlock(x, blockY + 4, z, wallId);

                world.setBlock(x, blockY + 5, z, Block.SLAB.id);
                world.setBlockMeta(x, blockY + 5, z, roofMeta);

                // walls
                if (x == blockX || z == blockZ || x == blockX + 4 || z == blockZ + 4)
                    for (int yo = 1; yo < 4; yo++)
                        world.setBlock(x, blockY + yo, z, wallId);
                else
                    for (int yo = 1; yo < 4; yo++)
                        world.setBlock(x, blockY + yo, z, 0);

                // foundation
                for (int yo = -2; yo < 0; yo++)
                    if (world.getBlockId(x, blockY + yo, z) == 0)
                        world.setBlock(x, blockY + yo, z, floorId);
            }
        }

        // windows
        world.setBlock(blockX + 2, blockY + 2, blockZ + 4, 0);
        world.setBlock(blockX + 4, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX + 2, blockY + 2, blockZ, 0);

        // door
        switch (random.nextInt(4)) {
            case 0: world.setBlock(blockX + 2, blockY + 1, blockZ + 4, 0); break;
            case 1: world.setBlock(blockX + 4, blockY + 1, blockZ + 2, 0); break;
            case 2: world.setBlock(blockX, blockY + 1, blockZ + 2, 0); break;
            case 3: world.setBlock(blockX + 2, blockY + 1, blockZ, 0); break;
        }

        // torches
        world.setBlock(blockX + 2, blockY + 3, blockZ + 1, Block.TORCH.id);
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