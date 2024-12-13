package net.bluebunnex.pprops.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class SlimeHammer extends TemplateItem {

    public SlimeHammer(Identifier identifier) {
        super(identifier);

        this.setMaxCount(1);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        // TODO play slime sound, make particles, launch in opposite direction attacker is facing

        attacker.velocityY = 1;
        return false;
    }

    @Override
    public boolean preMine(ItemStack stack, BlockState blockState, int x, int y, int z, int side, PlayerEntity player) {

        player.velocityY = 1;
        return false;
    }
}