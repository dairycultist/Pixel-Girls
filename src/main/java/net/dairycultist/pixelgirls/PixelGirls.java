package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.entity.WomanEntity;
import net.dairycultist.pixelgirls.entity.WomanEntityModel;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.Random;

public class PixelGirls {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block PLAINS_LUCKY_BLOCK;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        PLAINS_LUCKY_BLOCK = new LuckyBlock(NAMESPACE.id("plains_lucky_block"), WomanEntity.VariantPool.PLAINS)
        .setTranslationKey(NAMESPACE, "plains_lucky_block");

        PLAINS_LUCKY_BLOCK = new LuckyBlock(NAMESPACE.id("desert_lucky_block"), WomanEntity.VariantPool.DESERT)
                .setTranslationKey(NAMESPACE, "desert_lucky_block");

        PLAINS_LUCKY_BLOCK = new LuckyBlock(NAMESPACE.id("nether_lucky_block"), WomanEntity.VariantPool.NETHER)
                .setTranslationKey(NAMESPACE, "nether_lucky_block");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(WomanEntity.class, "Woman");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {

        event.renderers.put(WomanEntity.class, new LivingEntityRenderer(new WomanEntityModel(), 0.5f));
    }
}
