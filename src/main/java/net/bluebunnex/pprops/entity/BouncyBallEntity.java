package net.bluebunnex.pprops.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class BouncyBallEntity extends Entity {

    public BouncyBallEntity(World world) {
        super(world);

        this.setBoundingBoxSpacing(0.2F, 0.2F);
    }

    @Override
    protected void initDataTracker() {}

    @Override
    public void tick() {
        super.tick();

        //this.markDead();
    }

    @Override
    public Box getBoundingBox() {
        return Box.create(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void readNbt(NbtCompound nbt) {}

    @Override
    protected void writeNbt(NbtCompound nbt) {}
}