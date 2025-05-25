package net.dairycultist.pixelgirls.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    public enum VariantPool {

        COMMON(new String[] { "Senko", "Koishi", "Miku" }),
        RARE(new String[] { "Sakura Miku" });

        public final String[] names;

        VariantPool(String[] names) { this.names = names; }
    }

    private int variantID;
    public String name;

    public WomanEntity(World world) {
        this(world, VariantPool.COMMON);
    }

    public WomanEntity(World world, VariantPool variantPool) {
        super(world);

        this.maxHealth = 20;
        this.health    = 20;

        this.setVariant(
                this.random.nextInt(0, variantPool.names.length)
                + variantPool.ordinal() * 100
        );
    }

    public void setVariant(int variantID) {

        this.variantID = variantID;
        this.name = VariantPool.values()[variantID / 100].names[variantID % 100];

        this.texture =
                "/assets/pixelgirls/stationapi/textures/entity/"
                + this.name.toLowerCase().replace(' ', '_')
                + ".png";
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getTarget() != null) {

            if (this.isImmobile()) { // close to target

                this.lookAt(this.getTarget(), 45f, 999f); // pitch and yaw are backwards

            } else { // have a target (that is far away)

                if (this.getDistance(this.getTarget()) > 32f)
                    this.setTarget(null);
            }
        }
    }

    @Override
    public boolean damage(Entity damageSource, int amount) {

        this.setTarget(null);

        return super.damage(damageSource, amount);
    }

    @Override
    public boolean interact(PlayerEntity player) {
        super.onPlayerInteraction(player);

        //if (heldItem instanceof FoodItem) {
//
//    if (this.health >= this.maxHealth) {
//
//        dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayNotHungry(this.random, heldStack));
//
//    } else {
//
//        dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayEat(this.random, heldStack));
//
//        this.heal(((FoodItem) heldItem).getHealthRestored());
//
//        for (int i = 0; i < 5; i++) {
//            this.world.addParticle("heart", this.x + Math.random() * 2 - 1, this.y + Math.random() + 0.5, this.z + Math.random() * 2 - 1, 0, 0, 0);
//        }
//
//        player.inventory.removeStack(player.inventory.selectedSlot, 1);
//    }
//}

        this.setTarget(this.getTarget() == player ? null : player);

        //world.playSound(this, "pixelgirls:entity.woman.talk", 1.0F, 1.0F);
        player.swingHand();

        return true;
    }

    @Override
    public boolean isImmobile() {

        if (this.getTarget() == null)
            return false;

        // don't move when closer than 5 blocks to target player so we don't press into them
        return this.getDistance(this.getTarget()) < 4;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        nbt.putInt("VariantID", this.variantID);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.contains("VariantID"))
            this.setVariant(nbt.getInt("VariantID"));
    }

    @Override
    protected String getRandomSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        // resources/assets/pixelgirls/stationapi/sounds/sound/entity/woman
        // "pixelgirls:entity.woman.hurt" // hurt(1-3).ogg
        return "mob.chickenhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.chickenhurt";
    }
}
