package net.bluebunnex.pixelgirls.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class WomanEntity extends AnimalEntity {

    private static final int TOTAL_TEXTURE_COUNT = 3;

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

        ItemStack held = player.inventory.getSelectedItem();

        // TODO dialogue state machine
        // for example, she can take a sword from you to use for fighting, say that she's hurt,
        // thank you for the rose but say she doesn't need healing right now, etc

        if (this.health != this.maxHealth && held != null && held.getItem() == Block.ROSE.asItem()) {

            this.heal(4);

            world.playSound(this, "random.fuse", 1.0F, 1.0F);
            for (int i=0; i<5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

            player.sendMessage("[Woman " + this.health + "/20] Thank you for the heals!");

        } else {

            player.sendMessage("[Woman " + this.health + "/20] Hi sweetie!");
        }

        player.swingHand();
        return true;
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
