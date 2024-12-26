package net.bluebunnex.pixelgirls.entity;

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

    public WomanEntity(World world) {
        super(world);

        this.textureVariant = (int) (Math.random() * TOTAL_TEXTURE_COUNT) + 1;
        this.texture   = "/assets/pixelgirls/stationapi/textures/entity/woman" + textureVariant + ".png";

        this.name = POSSIBLE_NAMES[(int) (Math.random() * POSSIBLE_NAMES.length)];

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

                //world.playSound(this, "pixelgirls:entity.woman.hurt", 1.0F, 1.0F);
                for (int i = 0; i < 5; i++) {
                    this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5);
                }

                player.inventory.removeStack(player.inventory.selectedSlot, 1);

                response = "Thank you for the rose, I'm feeling better already!";

            } else {

                response = "I appreciate the sentiment, but I'm in tip top shape already.";
            }

        } else if (this.health < 10) {

            response = "I'm not feeling too hot, do you have a rose?";

        } else {

            int random = (int) (Math.random() * 3);

            response = random == 0 ? "What's up?"
                     : random == 1 ? "Hi sweetie."
                     : "Hello!";
        }

        player.sendMessage("[" + this.name + " " + this.health + "/20] " + response);

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

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("TextureVariant", this.textureVariant);
        nbt.putString("Name", this.name);
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
