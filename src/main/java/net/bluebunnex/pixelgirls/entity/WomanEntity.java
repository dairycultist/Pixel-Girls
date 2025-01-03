package net.bluebunnex.pixelgirls.entity;

import net.bluebunnex.pixelgirls.PixelGirls;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    private static final int TOTAL_TEXTURE_COUNT = 4;
    private static final String[] POSSIBLE_NAMES = {
        "Olivia", "Emma", "Ava", "Sophia", "Isabella", "Mia", "Amelia", "Harper", "Evelyn", "Abigail", "Ella", "Chloe",
        "Grace", "Lily", "Aria", "Zoe", "Nora", "Riley", "Scarlett", "Layla", "Camila", "Mila", "Avery", "Ellie",
        "Luna", "Samantha", "Hannah", "Hazel", "Victoria", "Nova", "Leah", "Addison", "Stella", "Natalie", "Zoey",
        "Brooklyn", "Bella", "Ariana", "Lillian", "Lucy", "Paisley", "Audrey", "Claire", "Skylar", "Sadie", "Alice",
        "Katherine", "Caroline", "Genesis", "Everly", "Eva", "Emery"
    };

    private int textureVariant;
    private String name;
    private String marriedTo;

    public WomanEntity(World world) {
        super(world);

        this.textureVariant = (int) (Math.random() * TOTAL_TEXTURE_COUNT) + 1;
        this.texture   = "/assets/pixelgirls/stationapi/textures/entity/woman" + textureVariant + ".png";

        this.name = POSSIBLE_NAMES[(int) (Math.random() * POSSIBLE_NAMES.length)];

        this.marriedTo = null;

        this.maxHealth = 20;
        this.health    = 20;
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack heldStack = player.inventory.getSelectedItem();
        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        if (heldItem == Block.ROSE.asItem() && this.health != this.maxHealth) {

            this.heal(4);

            //world.playSound(this, "pixelgirls:entity.woman.giggle", 1.0F, 1.0F);
            for (int i = 0; i < 5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else if (heldItem == PixelGirls.weddingRing) {

            if (this.marriedTo == null) {

                player.sendMessage("\"I do!\"");

                this.marriedTo = player.name;
                player.inventory.removeStack(player.inventory.selectedSlot, 1);

            } else if (this.marriedTo.equals(player.name)) {

                player.sendMessage("\"We're already married!\"");

            } else {

                player.sendMessage("\"I'm flattered, but I'm already taken.\"");
            }

        } else {

            // TODO temporary, replace with dialogue state machine or something
            player.sendMessage(
                    this.name + " (" + this.health
                    + (this.marriedTo == null ? "/20) Not married." : "/20) Married to " + this.marriedTo + ".")
            );
        }

        player.swingHand();
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        // TODO wife AI: follow you and look at you and if you stand too close to her she'll blush

        PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

        if (player != null && this.canSee(player)) {

            ItemStack heldStack = player.inventory.getSelectedItem();
            Item heldItem = heldStack != null ? heldStack.getItem() : null;

            // chase nearby player if they're holding a rose OR we're married to them
            // AND if they're not too close (so we don't push them)
            if (
                    (heldItem == Block.ROSE.asItem() || (this.marriedTo != null && this.marriedTo.equals(player.name)))
                    && this.getDistance(player) > 5
            ) {
                this.setTarget(player);
            } else {
                this.setTarget(null);
            }
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("TextureVariant", this.textureVariant);
        nbt.putString("Name", this.name);

        if (this.marriedTo != null)
            nbt.putString("MarriedTo", this.marriedTo);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        // load texture
        this.textureVariant = nbt.getInt("TextureVariant");

        if (!nbt.contains("TextureVariant"))
            this.textureVariant = 1;

        this.texture = "/assets/pixelgirls/stationapi/textures/entity/woman" + textureVariant + ".png";

        // load name
        this.name = nbt.getString("Name");

        if (!nbt.contains("Name"))
            this.name = "Hazalelponea";

        // load married to
        this.marriedTo = nbt.getString("MarriedTo");

        if (!nbt.contains("MarriedTo"))
            this.marriedTo = null;
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
