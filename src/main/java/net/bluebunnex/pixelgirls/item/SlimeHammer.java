package net.bluebunnex.pixelgirls.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import javax.annotation.Nullable;

public class SlimeHammer extends TemplateItem {

    public SlimeHammer(Identifier identifier) {
        super(identifier);

        this.setMaxCount(1);
        this.setMaxDamage(100);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        launch(stack, target, attacker, 1.5);

        return true;
    }

    @Override
    public boolean preMine(ItemStack stack, BlockState blockState, int x, int y, int z, int side, PlayerEntity player) {

        launch(stack, null, player, 1.5);

        return true;
    }

    private void launch(ItemStack stack, @Nullable LivingEntity hit, LivingEntity hitter, double force) {

        stack.damage(1, null);

        // play slime sound
        hitter.world.playSound(hitter, "mob.slime", 0.6f, 1.0f + (float) Math.random() * 0.2f);

        // make particles
        for (int i=0; i<5; i++) {

            hitter.world.addParticle("slime", hitter.x, hitter.y, hitter.z, Math.random() - 0.5, 0.5 + Math.random() * 0.1, Math.random() - 0.5);
        }

        // launch hitter backwards and hit forwards
        Vec3d look = hitter.getLookVector();

        hitter.velocityX = -look.x * force;
        hitter.velocityY = -look.y * force;
        hitter.velocityZ = -look.z * force;

        if (hit != null) {

            hit.velocityX = look.x * force;
            hit.velocityY = look.y * force;
            hit.velocityZ = look.z * force;
        }
    }
}