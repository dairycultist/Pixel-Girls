package net.bluebunnex.pprops.entity;

import net.bluebunnex.pprops.Main;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.math.MathHelper;

public class BouncyBallEntity extends Entity {

    public BouncyBallEntity(World world, Vec3d forward) {
        super(world);

        this.setBoundingBoxSpacing(0.2F, 0.2F);

        this.addVelocity(forward.x, forward.y, forward.z);
    }

    @Override
    protected void initDataTracker() {}

    @Override
    public void tick() {
        super.tick();

        // gravity
        this.velocityY -= 0.1;

        // move
        double velX = this.velocityX;
        double velY = this.velocityY;
        double velZ = this.velocityZ;

        this.move(velX, velY, velZ);

        // raycast for bounce
        Vec3d origin = Vec3d.create(this.x, this.y, this.z);

        this.velocityX = getBounceOnAxis(origin, 0.3, 0, 0, velX);
        this.velocityY = getBounceOnAxis(origin, 0, 0.3, 0, velY);
        this.velocityZ = getBounceOnAxis(origin, 0, 0, 0.3, velZ);

        if (this.age > 300 && !this.dead) {

            this.dropItem(new ItemStack(Main.bouncyBall, 1), 0f);
            this.markDead();
        }
    }

    private double getBounceOnAxis(Vec3d origin, double dx, double dy, double dz, double currVelocity) {

        if (this.world.raycast(origin, origin.add(-dx, -dy, -dz)) != null) {

            return Math.abs(currVelocity);

        } else if (this.world.raycast(origin, origin.add(dx, dy, dz)) != null) {

            return -Math.abs(currVelocity);
        }

        return currVelocity;
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