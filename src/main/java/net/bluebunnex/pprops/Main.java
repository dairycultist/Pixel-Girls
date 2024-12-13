package net.bluebunnex.pprops;

import net.bluebunnex.pprops.item.SlimeHammer;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class Main {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item slimeHammer;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        slimeHammer = new SlimeHammer(NAMESPACE.id("slime_hammer")).setTranslationKey(NAMESPACE, "slime_hammer");
    }
}
