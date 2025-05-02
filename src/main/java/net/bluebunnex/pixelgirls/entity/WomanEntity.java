package net.bluebunnex.pixelgirls.entity;

import net.bluebunnex.pixelgirls.DialogueContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Random;

public class WomanEntity extends AnimalEntity {

    private static final WomanMind GENERIC_MIND = new WomanMind() {

        @Override
        public String sayLoveDiamonds(Random random) {
            return "Woah, so shiny! I have a few of these.";
        }

        @Override
        public String sayDamage(Random random) {

            return switch (random.nextInt(0, 3)) {
                case 0 -> "Ow, stop!";
                case 1 -> "That hurts!";
                default -> "Wahh!";
            };
        }

        @Override
        public String sayEat(Random random, Item heldItem) {
            return "That " + heldItem.getTranslatedName().toLowerCase() + " was delicious!";
        }

        @Override
        public String sayIdle(Random random) {
            return "Hey!";
        }
    };

    // senko says Eep!

    private int variant;
    private WomanMind mind;

    public String name;

    public WomanEntity(World world) {
        super(world);

        this.maxHealth = 20;
        this.health    = 20;

        this.setVariant(this.random.nextInt(0, 4));
    }

    public void setVariant(int variant) {

        this.variant = variant;

        switch (this.variant) {

            default:
            case 0:
                this.name = "Senko";
                this.mind = GENERIC_MIND;
                break;
//            case 1:
//                this.name = "Koishi";
//                break;
//            case 2:
//                this.name = "Miku";
//                break;
//            case 3:
//                this.name = "Sakura Miku";
//                break;
        }

        this.texture = "/assets/pixelgirls/stationapi/textures/entity/" + this.name.toLowerCase().replace(' ', '_') + ".png";
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public boolean damage(Entity damageSource, int amount) {

        if (damageSource instanceof PlayerEntity player) {

            ((DialogueContainer) player).pixel_girls$pushDialogue(this.name, this.mind.sayDamage(this.random));
        }

        return super.damage(damageSource, amount);
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ItemStack heldStack = player.inventory.getSelectedItem();
        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        DialogueContainer dialogueContainer = ((DialogueContainer) player);

        if (heldItem == Item.DIAMOND) {

            dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayLoveDiamonds(this.random));

        } else if (heldItem instanceof FoodItem) {

            dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayEat(this.random, heldItem));

            this.heal(((FoodItem) heldItem).getHealthRestored());

            for (int i = 0; i < 5; i++) {
                this.world.addParticle("smoke", this.x, this.y + 0.5, this.z, Math.random() - 0.5, Math.random() * 0.5, Math.random() - 0.5);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else {

            dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayIdle(this.random));

            this.setTarget(player);
        }

        //world.playSound(this, "pixelgirls:entity.woman.talk", 1.0F, 1.0F);
        player.swingHand();

        return true;
    }

    @Override
    public void tick() {
        super.tick();

        // TODO this follow system sucks but idk

        if (this.isImmobile()) {

            // continue looking at target until they get out of range
            this.lookAt(this.getTarget(), 45f, 999f); // pitch and yaw are backwards

        } else if (this.getTarget() == null) {

            // if we don't have a target, look for a potential target

            PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

            if (player != null && this.canSee(player)) {

                ItemStack heldStack = player.inventory.getSelectedItem();
                Item heldItem = heldStack != null ? heldStack.getItem() : null;

                if (heldItem == Item.DIAMOND)
                    this.setTarget(player);
            }

        } else {

            // if we have a target, only continue targeting them while they are holding a diamond

            if (this.canSee(this.getTarget())) {

                ItemStack heldStack = ((PlayerEntity) this.getTarget()).inventory.getSelectedItem();
                Item heldItem = heldStack != null ? heldStack.getItem() : null;

                if (heldItem != Item.DIAMOND)
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
        return "mob.chickenhurt"; // "pixelgirls:entity.woman.hurt" // hurt(1-3).ogg
    }

    @Override
    protected String getDeathSound() {
        return "mob.chickenhurt";
    }
}
