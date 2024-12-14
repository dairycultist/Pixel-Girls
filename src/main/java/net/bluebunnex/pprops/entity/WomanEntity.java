package net.bluebunnex.pprops.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    public WomanEntity(World world) {
        super(world);

        this.texture = "/assets/pprops/stationapi/textures/entity/woman1.png";

        this.maxHealth = 20;
        this.health = 20;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack held = player.inventory.getSelectedItem();

        if (this.health != this.maxHealth && held != null && held.getItem() == Block.ROSE.asItem()) {

            this.heal(4);

            for (int i=0; i<5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

            return true;

        } else {

            return false;
        }
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
