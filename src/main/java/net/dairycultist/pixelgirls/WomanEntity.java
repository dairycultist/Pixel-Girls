package net.dairycultist.pixelgirls;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class WomanEntity extends AnimalEntity {

    private WomanVariants variant;

    public WomanEntity(World world) {
        this(world, WomanVariants.values()[(int) (Math.random() * WomanVariants.values().length)]);
    }

    public WomanEntity(World world, WomanVariants variant) {
        super(world);

        this.maxHealth = 20;
        this.health    = 20;

        this.setVariant(variant);
    }

    public void setVariant(WomanVariants variant) {

        this.variant = variant;

        this.texture =
                "/assets/pixelgirls/stationapi/textures/entity/"
                + variant.name.toLowerCase().replace(' ', '_')
                + ".png";
    }

    public WomanVariants getVariant() {
        return variant;
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

        ItemStack heldStack = player.getHand();

        if (heldStack != null && heldStack.getItem() instanceof FoodItem food) {

            this.heal(food.getHealthRestored());

            if (this.health >= this.maxHealth) {
                for (int i = 0; i < 5; i++)
                    this.world.addParticle("heart", this.x + Math.random() * 2 - 1, this.y + Math.random() + 0.5, this.z + Math.random() * 2 - 1, 0, 0, 0);
            } else {
                for (int i = 0; i < 5; i++)
                    this.world.addParticle("smoke", this.x + Math.random() * 2 - 1, this.y + Math.random() + 0.5, this.z + Math.random() * 2 - 1, 0, 0, 0);
            }

            player.inventory.removeStack(player.inventory.selectedSlot, 1);

        } else {
            this.setTarget(this.getTarget() == player ? null : player);
        }

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

        nbt.putInt("VariantID", this.variant.id);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        if (nbt.contains("VariantID"))
            this.setVariant(WomanVariants.values()[nbt.getInt("VariantID")]);
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
