package net.bluebunnex.pprops.entity;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    public WomanEntity(World world) {
        super(world);

        this.texture = "/assets/pprops/stationapi/textures/entity/woman1.png";
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

        // chase nearby player until within XYZ blocks
        if (player != null && this.canSee(player) && this.getDistance(player) > 5) {
            this.setTarget(player);
        } else {
            this.setTarget(null);
        }
    }


}
