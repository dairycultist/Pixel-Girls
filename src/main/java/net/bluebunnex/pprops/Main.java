package net.bluebunnex.pprops;

import net.bluebunnex.pprops.entity.BouncyBallEntity;
import net.bluebunnex.pprops.entity.BouncyBallEntityRenderer;
import net.bluebunnex.pprops.item.BouncyBall;
import net.bluebunnex.pprops.item.SlimeHammer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class Main {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item slimeHammer;
    public static Item bouncyBall;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        slimeHammer = new SlimeHammer(NAMESPACE.id("slime_hammer")).setTranslationKey(NAMESPACE, "slime_hammer");
        bouncyBall = new BouncyBall(NAMESPACE.id("bouncy_ball")).setTranslationKey(NAMESPACE, "bouncy_ball");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {
        event.register(BouncyBallEntity.class,"bouncy_ball");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {
        event.renderers.put(BouncyBallEntity.class, new BouncyBallEntityRenderer());
    }
}
