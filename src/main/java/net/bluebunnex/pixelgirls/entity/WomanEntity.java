package net.bluebunnex.pixelgirls.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    // might change this to "VillagerEntity" and have some men (to make the women prettier by comparison) we'll see
    // then we'll also have to save their gender :O

    private static final int TOTAL_TEXTURE_COUNT = 3;
    // randomly generate a name for each woman, which renders above their head?

    private int textureVariant;

    public WomanEntity(World world) {
        super(world);

        textureVariant = (int) (Math.random() * TOTAL_TEXTURE_COUNT) + 1;
        this.texture   = "/assets/pixelgirls/stationapi/textures/entity/woman" + textureVariant + ".png";

        this.maxHealth = 20;
        this.health    = 20;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack heldStack = player.inventory.getSelectedItem();
        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        String response;

        // TODO improve dialogue state machine
        if (heldItem == Block.ROSE.asItem()) {

            if (this.health != this.maxHealth) {

                this.heal(4);

                world.playSound(this, "random.fuse", 1.0F, 1.0F);
                for (int i = 0; i < 5; i++) {
                    this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
                }

                player.inventory.removeStack(player.inventory.selectedSlot, 1);

                response = "Thank you for the rose, I'm feeling better already!";

            } else {

                response = "I appreciate the sentiment sweetie, but I'm in tip top shape already.";
            }

        } else if (this.health < 10) {

            response = "I'm not feeling too hot sweetie, do you have a rose?";

        } else {

            response = "Hi sweetie.";
        }

        player.sendMessage("[Woman " + this.health + "/20] " + response);

        player.swingHand();
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

        if (player != null && this.canSee(player)) {

            ItemStack heldStack = player.inventory.getSelectedItem();
            Item heldItem = heldStack != null ? heldStack.getItem() : null;

            // chase nearby player if they're holding a rose
            // AND if they're not too close (so we don't push them)
            if (heldItem == Block.ROSE.asItem() && this.getDistance(player) > 5) {
                this.setTarget(player);
            } else {
                this.setTarget(null);
            }
        }
    }

    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("TextureVariant", this.textureVariant);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.textureVariant = nbt.getInt("TextureVariant");

        if (!nbt.contains("TextureVariant")) {

            this.textureVariant = 1;
        }

        this.texture = "/assets/pixelgirls/stationapi/textures/entity/woman" + textureVariant + ".png";
    }
}
