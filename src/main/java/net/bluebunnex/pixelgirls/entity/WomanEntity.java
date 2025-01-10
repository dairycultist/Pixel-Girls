package net.bluebunnex.pixelgirls.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    public String name;
    public Item favouriteItem;
    private int variant;

    public WomanEntity(World world) {
        super(world);

        this.maxHealth = 20;
        this.health    = 20;

        this.setVariant(0);
    }

    public void setVariant(int variant) {

        this.variant = variant;

        switch (this.variant) {

            default:
            case 0:
                this.name = "Rosa Maria";
                this.favouriteItem = Block.ROSE.asItem();
                break;
        }

        this.texture = "/assets/pixelgirls/stationapi/textures/entity/" + this.name.toLowerCase().replace(' ', '_') + ".png";
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack heldStack = player.inventory.getSelectedItem();
        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        if (heldItem == favouriteItem) {

            player.sendMessage("Aww! How'd you know " + heldItem.getTranslatedName().toLowerCase() + "s are my favourite?");

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else if (heldItem instanceof FoodItem) {

            if (Math.random() > 0.5) {
                player.sendMessage("\"Thank you for the " + heldItem.getTranslatedName().toLowerCase() + "!\"");
            } else {
                player.sendMessage("\"That " + heldItem.getTranslatedName().toLowerCase() + " was delicious!\"");
            }

            this.heal(((FoodItem) heldItem).getHealthRestored());

            for (int i = 0; i < 5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else {

            player.sendMessage("\"Hi!\"");
        }

        //world.playSound(this, "pixelgirls:entity.woman.giggle", 1.0F, 1.0F);
        player.swingHand();

        return true;
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

        if (player != null && this.canSee(player)) {

            this.setTarget(player);

            if (this.isImmobile())
                this.lookAt(player, 45f, 999f); // pitch and yaw are backwards

        } else {

            this.setTarget(null);
        }
    }

    @Override
    public boolean isImmobile() {

        if (this.getTarget() == null)
            return false;

        // don't move when closer than 5 blocks to target player so we don't press into them
        return this.getDistance(this.getTarget()) < 5;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("Variant", this.variant);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.contains("Variant"))
            this.setVariant(nbt.getInt("Variant"));
    }

    @Override
    protected String getRandomSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return "pixelgirls:entity.woman.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "pixelgirls:entity.woman.hurt";
    }
}
