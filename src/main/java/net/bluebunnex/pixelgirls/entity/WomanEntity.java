package net.bluebunnex.pixelgirls.entity;

import net.bluebunnex.pixelgirls.DialogueContainer;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    private int variant;

    public String name;
    public Item favouriteItem;

    public WomanEntity(World world) {
        super(world);

        this.maxHealth = 20;
        this.health    = 20;

        this.setVariant((int) (Math.random() * 2));
    }

    public void setVariant(int variant) {

        this.variant = variant;

        switch (this.variant) {

            default:
            case 0:
                this.name = "Rosa Maria";
                this.favouriteItem = Block.ROSE.asItem();
                break;

            case 1:
                this.name = "TVetta";
                this.favouriteItem = Block.REDSTONE_TORCH.asItem();
                break;
        }

        this.texture = "/assets/pixelgirls/stationapi/textures/entity/" + this.name.toLowerCase().replace(' ', '_') + ".png";
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack heldStack = player.inventory.getSelectedItem();
        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        DialogueContainer dialogueContainer = ((DialogueContainer) (Object) player);

        if (heldItem == favouriteItem) {

            dialogueContainer.pixel_girls$pushDialogue(this.name, "A " + favouriteItem.getTranslatedName().toLowerCase() + ", my favourite!");

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else if (heldItem instanceof FoodItem) {

            if (Math.random() > 0.5) {
                dialogueContainer.pixel_girls$pushDialogue(this.name, "Thank you for the " + heldItem.getTranslatedName().toLowerCase() + "!");
            } else {
                dialogueContainer.pixel_girls$pushDialogue(this.name, "That " + heldItem.getTranslatedName().toLowerCase() + " was delicious!");
            }

            this.heal(((FoodItem) heldItem).getHealthRestored());

            for (int i = 0; i < 5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else {

            // maybe open trading menu?
            dialogueContainer.pixel_girls$pushDialogue(this.name, "I'd love a " + favouriteItem.getTranslatedName().toLowerCase() + ".");

            this.setTarget(player);
        }

        //world.playSound(this, "pixelgirls:entity.woman.talk", 1.0F, 1.0F);
        player.swingHand();

        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isImmobile()) {

            // continue looking at target until they get out of range
            this.lookAt(this.getTarget(), 45f, 999f); // pitch and yaw are backwards

        } else if (this.getTarget() == null) {

            // if we don't have a target, look for a potential target

            PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

            if (player != null && this.canSee(player)) {

                ItemStack heldStack = player.inventory.getSelectedItem();
                Item heldItem = heldStack != null ? heldStack.getItem() : null;

                if (heldItem == favouriteItem)
                    this.setTarget(player);
            }

        } else {

            // if we have a target, only continue targeting it while they are holding our favourite item

            if (this.canSee(this.getTarget())) {

                ItemStack heldStack = ((PlayerEntity) this.getTarget()).inventory.getSelectedItem();
                Item heldItem = heldStack != null ? heldStack.getItem() : null;

                if (heldItem != favouriteItem)
                    this.setTarget(null);

            } else {

                this.setTarget(null);
            }
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
