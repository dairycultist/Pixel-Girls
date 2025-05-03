package net.bluebunnex.pixelgirls.entity;

import net.bluebunnex.pixelgirls.DialogueContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    private int variant;
    private DialogueState dialogueState;

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
                this.dialogueState = new TestDialogueState();
                break;
            case 1:
                this.name = "Koishi"; // TODO update boob texture
                this.dialogueState = new TestDialogueState();
                break;
            case 2:
                this.name = "Miku";
                this.dialogueState = new TestDialogueState();
                break;
            case 3:
                this.name = "Sakura Miku"; // TODO update boob texture
                this.dialogueState = new TestDialogueState();
                break;
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

            ((DialogueContainer) player).pixel_girls$pushDialogue(this.name, dialogueState.getDialogue(this.random, player, true));
            dialogueState = dialogueState.getNextState(this.random, player, true);
        }

        return super.damage(damageSource, amount);
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        ((DialogueContainer) player).pixel_girls$pushDialogue(this.name, dialogueState.getDialogue(this.random, player, false));
        dialogueState = dialogueState.getNextState(this.random, player, false);

        this.setTarget(player);

        //world.playSound(this, "pixelgirls:entity.woman.talk", 1.0F, 1.0F);
        player.swingHand();

        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isImmobile()) { // too close to target to un-target

            this.lookAt(this.getTarget(), 45f, 999f); // pitch and yaw are backwards

        } else if (this.getTarget() == null) { // no target, look for a potential target

            PlayerEntity player = this.world.getClosestPlayer(this, 16.0);

            if (player != null && this.canSee(player)) {

                ItemStack heldStack = player.inventory.getSelectedItem();
                Item heldItem = heldStack != null ? heldStack.getItem() : null;

                if (heldItem == Item.DIAMOND)
                    this.setTarget(player);
            }

        } else { // have a target (that is far away), only continue targeting if we see them holding a diamond

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
