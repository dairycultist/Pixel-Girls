package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.entity.WomanEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

            int blockX = x * 16;
            int blockZ = z * 16;

            Biome biome = this.world.method_1781().getBiome(blockX, blockZ);

            attemptToPlaceWell(blockX + 16, blockZ + 16, biome);

            for (int i = 0; i < 6; i++)
                attemptToPlaceHouse(blockX + random.nextInt(16) * 2, blockZ + random.nextInt(16) * 2, biome, random.nextInt(4));
        }
    }

    @Unique
    private boolean isPermittedSurfaceBlock(int blockX, int blockY, int blockZ) {

        int blockId = world.getBlockId(blockX, blockY, blockZ);

        return blockId == Block.GRASS_BLOCK.id || blockId == Block.SAND.id;
    }

    @Unique
    private boolean isPermeableSurfaceBlock(int blockX, int blockY, int blockZ) {

        int blockId = world.getBlockId(blockX, blockY, blockZ);

        return blockId == 0 || blockId == Block.LEAVES.id || blockId == Block.GRASS.id || blockId == Block.BROWN_MUSHROOM.id || blockId == Block.RED_MUSHROOM.id  || blockId == Block.SNOW.id;
    }

    @Unique
    private int topY(int blockX, int blockZ) {

        int blockY = world.getTopSolidBlockY(blockX, blockZ);

        while (isPermeableSurfaceBlock(blockX, blockY, blockZ))
            blockY--;

        return blockY;
    }

    @Unique
    private void placeGravelPath(int x1, int z1, int x2, int z2) {

        for (int x = x1; x < x2; x++) {
            for (int z = z1; z < z2; z++) {

                int y = world.getTopSolidBlockY(x, z) - 1;

                if (isPermittedSurfaceBlock(x, y, z))
                    world.setBlock(x, y, z, Block.GRAVEL.id);
            }
        }
    }

    @Unique
    private void attemptToPlaceWell(int blockX, int blockZ, Biome biome) {

        int blockY = topY(blockX, blockZ) + 1;

        int block = biome == Biome.DESERT ? Block.SANDSTONE.id : Block.COBBLESTONE.id;

        for (int x = blockX - 1; x < blockX + 2; x++) {
            for (int z = blockZ - 1; z < blockZ + 2; z++) {

                if (x == blockX && z == blockZ)
                    for (int y = blockY - 10; y <= blockY; y++)
                        world.setBlock(x, y, z, Block.WATER.id);
                else
                    for (int y = blockY - 10; y <= blockY; y++)
                        world.setBlock(x, y, z, block);
            }
        }

        if (random.nextBoolean()) {

            world.setBlock(blockX, blockY - 10, blockZ, Block.CHEST.id);
            ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(blockX, blockY - 10, blockZ);

            for (int i = 0; i < random.nextInt(1, 4); i++) {

                chest.setStack(random.nextInt(chest.size()), new ItemStack(Item.DIAMOND, random.nextInt(1, 3)));
            }
        }
    }

    @Unique
    private void attemptToPlaceHouse(int blockX, int blockZ, Biome biome, int direction) {

        int blockY = topY(blockX, blockZ);

        if (!isPermittedSurfaceBlock(blockX, blockY, blockZ))
            return;

        int floorId, stairId, wallId, columnId, roofMeta;

        switch (biome.name) {

            case "Ice Desert":
            case "Tundra":
            case "Taiga":
                floorId = Block.COBBLESTONE.id;
                stairId = Block.COBBLESTONE_STAIRS.id;
                wallId = Block.BRICKS.id;
                columnId = Block.LOG.id;
                roofMeta = 0;
                break;

            case "Desert":
                floorId = Block.SANDSTONE.id;
                stairId = Block.WOODEN_STAIRS.id;
                wallId = Block.PLANKS.id;
                columnId = Block.SANDSTONE.id;
                roofMeta = 1;
                break;

            default:
                floorId = Block.COBBLESTONE.id;
                stairId = Block.COBBLESTONE_STAIRS.id;
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
                    for (int y = blockY + 1; y < blockY + 4; y++)
                        world.setBlock(x, y, z, wallId);
                else
                    for (int y = blockY + 1; y < blockY + 4; y++)
                        world.setBlock(x, y, z, 0);

                // foundation
                for (int y = blockY - 6; y < blockY; y++)
                    if (world.getBlockId(x, y, z) == 0)
                        world.setBlock(x, y, z, floorId);
            }
        }

        // windows
        world.setBlock(blockX + 2, blockY + 2, blockZ + 4, 0);
        world.setBlock(blockX + 4, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX, blockY + 2, blockZ + 2, 0);
        world.setBlock(blockX + 2, blockY + 2, blockZ, 0);

        // door + air in front of door + potential stair
        switch (direction) {
            case 0:
                world.setBlock(   blockX,     blockY + 1, blockZ + 2, 0);

                world.setBlock(blockX - 1, blockY + 1, blockZ + 2, 0);
                world.setBlock(blockX - 1, blockY + 2, blockZ + 2, 0);

                if (world.getBlockId(blockX - 1, blockY, blockZ + 2) == 0) {
                    world.setBlock(blockX - 1, blockY, blockZ + 2, stairId);
                    world.setBlockMeta(blockX - 1, blockY, blockZ + 2, direction);
                }
                break;
            case 1:
                world.setBlock(blockX + 4, blockY + 1, blockZ + 2, 0);

                world.setBlock(blockX + 5, blockY + 1, blockZ + 2, 0);
                world.setBlock(blockX + 5, blockY + 2, blockZ + 2, 0);

                if (world.getBlockId(blockX + 5, blockY, blockZ + 2) == 0) {
                    world.setBlock(blockX + 5, blockY, blockZ + 2, stairId);
                    world.setBlockMeta(blockX + 5, blockY, blockZ +2, direction);
                }
                break;
            case 2:
                world.setBlock(blockX + 2, blockY + 1,    blockZ,     0);

                world.setBlock(blockX + 2, blockY + 1, blockZ - 1, 0);
                world.setBlock(blockX + 2, blockY + 2, blockZ - 1, 0);

                if (world.getBlockId(blockX + 2, blockY, blockZ - 1) == 0) {
                    world.setBlock(blockX + 2, blockY, blockZ - 1, stairId);
                    world.setBlockMeta(blockX + 2, blockY, blockZ - 1, direction);
                }
                break;
            case 3:
                world.setBlock(blockX + 2, blockY + 1, blockZ + 4, 0);

                world.setBlock(blockX + 2, blockY + 1, blockZ + 5, 0);
                world.setBlock(blockX + 2, blockY + 2, blockZ + 5, 0);

                if (world.getBlockId(blockX + 2, blockY, blockZ + 5) == 0) {
                    world.setBlock(blockX + 2, blockY, blockZ + 5, stairId);
                    world.setBlockMeta(blockX + 2, blockY, blockZ + 5, direction);
                }
                break;
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

        // woman
        WomanEntity resident = new WomanEntity(world);
        resident.setPosition(blockX + 2, blockY + 1, blockZ + 2);
        world.spawnEntity(resident);

        // chest
        if (random.nextBoolean()) {

            world.setBlock(blockX + 1, blockY + 1, blockZ + 1, Block.CHEST.id);
            ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(blockX + 1, blockY + 1, blockZ + 1);

            for (int i = 0; i < 8; i++) {

                ItemStack stack = this.getRandomChestItem();
                chest.setStack(random.nextInt(chest.size()), stack);
            }
        }
    }

    @Unique
    private ItemStack getRandomChestItem() {

        return switch (random.nextInt(3)) {
            case 0 -> new ItemStack(Item.WHEAT, random.nextInt(3, 8));
            case 1 -> new ItemStack(Item.BREAD, 1);
            default -> random.nextInt(50) == 0 ? new ItemStack(Item.GOLDEN_APPLE, 1) : new ItemStack(Item.APPLE, 1);
        };
    }
}