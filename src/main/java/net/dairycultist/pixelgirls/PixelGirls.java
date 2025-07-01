package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class PixelGirls {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block OVERWORLD_CRATE;
    public static Block NETHER_CRATE;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        OVERWORLD_CRATE = new WomanCrate(NAMESPACE.id("overworld_crate"), WomanEntity.VariantPool.OVERWORLD)
        .setTranslationKey(NAMESPACE, "overworld_crate");

        NETHER_CRATE = new WomanCrate(NAMESPACE.id("nether_crate"), WomanEntity.VariantPool.NETHER)
                .setTranslationKey(NAMESPACE, "nether_crate");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(WomanEntity.class, "Woman");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {

        event.renderers.put(WomanEntity.class, new WomanEntityRenderer());
    }
}
