package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;

public enum WomanVariants {

    MIKU("Miku", new ShortstackEntityModel()),
    KOISHI("Koishi", new WomanEntityModel()),
    SAKURA_MIKU("Sakura Miku", new WomanEntityModel()),
    SENKO("Senko", new WomanEntityModel());

    public static final WomanVariants[] OVERWORLD_POOL = { MIKU, KOISHI, SAKURA_MIKU };
    public static final WomanVariants[] NETHER_POOL = { SENKO };

    public final String name;
    public final int id;
    public final EntityModel model;

    WomanVariants(String name, EntityModel model) {
        this.name = name;
        this.id = this.ordinal();
        this.model = model;
    }
}
