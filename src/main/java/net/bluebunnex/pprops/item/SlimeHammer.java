package net.bluebunnex.pprops.item;

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

        launch(stack, target, attacker);

        return true;
    }

    @Override
    public boolean preMine(ItemStack stack, BlockState blockState, int x, int y, int z, int side, PlayerEntity player) {

        launch(stack, null, player);

        return true;
    }

    private void launch(ItemStack stack, @Nullable LivingEntity hit, LivingEntity hitter) {

        stack.damage(1, null);

        // TODO play slime sound, make particles

        // launch hitter backwards and hit forwards
        Vec3d look = hitter.getLookVector();

        hitter.velocityX = -look.x;
        hitter.velocityY = -look.y;
        hitter.velocityZ = -look.z;

        if (hit != null) {

            hit.velocityX = look.x;
            hit.velocityY = look.y;
            hit.velocityZ = look.z;
        }
    }
}