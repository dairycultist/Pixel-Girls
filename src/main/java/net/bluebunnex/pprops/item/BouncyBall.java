package net.bluebunnex.pprops.item;

import net.bluebunnex.pprops.entity.BouncyBallEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class BouncyBall extends TemplateItem {

    public BouncyBall(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemStack use(ItemStack itemStack, World world, PlayerEntity user) {

        user.swingHand();

        itemStack.count--;

        // spawn
        BouncyBallEntity ball = new BouncyBallEntity(world, user.getLookVector());

        ball.setPosition(user.x, user.y, user.z);

        world.spawnEntity(ball);

        return itemStack;
    }
}
