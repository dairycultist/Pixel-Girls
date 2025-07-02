package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.CowWomanDecorationModel;
import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;

public enum WomanVariants {

    // I guess this would be easier to mod if it was a registry instead of an enum, and register them statically

    MIKU("Miku"),
    CHONKY_MIKU("Chonky Miku", new ShortstackEntityModel(), new CowWomanDecorationModel()),
    KOISHI("Koishi"),
    SAKURA_MIKU("Sakura Miku"),
    SENKO("Senko"),
    URT("Urt", new ShortstackEntityModel(), new CowWomanDecorationModel(), "mob.cow", "mob.cowhurt", "mob.cowhurt");

    public static final WomanVariants[] OVERWORLD_POOL = { MIKU, KOISHI, SAKURA_MIKU };
    public static final WomanVariants[] NETHER_POOL = { SENKO };

    public final String name;
    public final int id;
    public final EntityModel model;
    public final EntityModel decorationModel;

    public final String randomSound;
    public final String hurtSound;
    public final String deathSound;

    WomanVariants(String name, EntityModel model, EntityModel decorationModel,
                  String randomSound, String hurtSound, String deathSound) {
        this.name = name;
        this.id = this.ordinal();
        this.model = model;
        this.decorationModel = decorationModel;

        this.randomSound = randomSound;
        this.hurtSound = hurtSound;
        this.deathSound = deathSound;
    }

    WomanVariants(String name, EntityModel model, EntityModel decorationModel) {
        this(name, model, decorationModel, null, "mob.chickenhurt", "mob.chickenhurt");
    }

    WomanVariants(String name) {
        this(name, new WomanEntityModel(), null);
    }
}
