package net.dairycultist.pixelgirls;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.ArrayList;
import java.util.Random;

public class WomanCrate extends TemplateBlock {

    private final ArrayList<WomanVariantRegistry> variantPool;

    public WomanCrate(Identifier identifier, ArrayList<WomanVariantRegistry> variantPool) {
        super(identifier, Material.WOOD);

        this.setHardness(0.8F);
        this.variantPool = variantPool;
    }

    public int getDroppedItemCount(Random random) {
        return 0;
    }

    public void onBreak(World world, int x, int y, int z) {

        Entity e = new WomanEntity(world, variantPool.get((int) (Math.random() * variantPool.size())));
        e.setPosition(x + 0.5, y, z + 0.5);
        world.spawnEntity(e);
    }
}
