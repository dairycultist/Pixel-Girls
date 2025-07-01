package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;

public enum WomanVariants {

    // I guess this would be easier to mod if it was a registry instead of an enum, and register them statically

    MIKU("Miku", new ShortstackEntityModel()),
    KOISHI("Koishi"),
    SAKURA_MIKU("Sakura Miku"),
    SENKO("Senko");

    public static final WomanVariants[] OVERWORLD_POOL = { MIKU, KOISHI, SAKURA_MIKU };
    public static final WomanVariants[] NETHER_POOL = { SENKO };

    public final String name;
    public final int id;
    public final EntityModel model;

    public final String randomSound;
    public final String hurtSound;
    public final String deathSound;

    WomanVariants(String name, EntityModel model, String randomSound, String hurtSound, String deathSound) {
        this.name = name;
        this.id = this.ordinal();
        this.model = model;

        this.randomSound = randomSound;
        this.hurtSound = hurtSound;
        this.deathSound = deathSound;
    }

    WomanVariants(String name, EntityModel model) {
        this(name, model, null, "mob.chickenhurt", "mob.chickenhurt");
    }

    WomanVariants(String name) {
        this(name, new WomanEntityModel());
    }
}
