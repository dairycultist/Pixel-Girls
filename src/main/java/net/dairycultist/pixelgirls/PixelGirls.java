package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.entity.WomanEntity;
import net.dairycultist.pixelgirls.entity.WomanEntityModel;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateSandstoneBlock;
import net.modificationstation.stationapi.api.template.block.TemplateStoneBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.Random;

import static net.minecraft.block.Block.STONE_SOUND_GROUP;

public class PixelGirls {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block COMMON_LUCKY_BLOCK;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        COMMON_LUCKY_BLOCK = new TemplateBlock(NAMESPACE.id("common_lucky_block"), Material.WOOD) {

            public int getDroppedItemCount(Random random) {
                return 0;
            }

            public void onBreak(World world, int x, int y, int z) {

                Entity e = new WomanEntity(world, WomanEntity.VariantPool.COMMON);
                e.setPosition(x + 0.5, y, z + 0.5);
                world.spawnEntity(e);
            }
        }
        .setHardness(0.8F)
        .setTranslationKey(NAMESPACE, "common_lucky_block");
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
