package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.CowWomanDecorationModel;
import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;

import java.util.ArrayList;

public class WomanVariantRegistry {

    public static final ArrayList<WomanVariantRegistry> ALL_POOL       = new ArrayList<WomanVariantRegistry>();
    public static final ArrayList<WomanVariantRegistry> OVERWORLD_POOL = new ArrayList<WomanVariantRegistry>();
    public static final ArrayList<WomanVariantRegistry> NETHER_POOL    = new ArrayList<WomanVariantRegistry>();

    // will add MikuDecorationModel later for twintails
    public static final WomanVariantRegistry MIKU = new WomanVariantRegistry("Miku", true);
//            .model(new WomanEntityModel(), new CowWomanDecorationModel(0f));
    public static final WomanVariantRegistry CHONKY_MIKU = new WomanVariantRegistry("Chonky Miku", true)
                                                           .model(new ShortstackEntityModel());
    public static final WomanVariantRegistry KOISHI = new WomanVariantRegistry("Koishi", true);
    public static final WomanVariantRegistry SAKURA_MIKU = new WomanVariantRegistry("Sakura Miku", false);
    public static final WomanVariantRegistry SENKO = new WomanVariantRegistry("Senko", false);
    public static final WomanVariantRegistry URT = new WomanVariantRegistry("Urt", true)
                                                   .model(new ShortstackEntityModel(), new CowWomanDecorationModel(2f))
                                                   .sound("mob.cow", "mob.cowhurt", "mob.cowhurt");

    private static int NEXT_ID = 0;

    public final String name;
    public final int id;

    public EntityModel model = new WomanEntityModel();
    public EntityModel decorationModel = null;

    public String randomSound = null;
    public String hurtSound = "mob.chickenhurt";
    public String deathSound = "mob.chickenhurt";

    public WomanVariantRegistry(String name, boolean trueIfOverworldElseNether) {

        this.name = name;
        this.id = NEXT_ID++;

        if (trueIfOverworldElseNether) {
            OVERWORLD_POOL.add(this);
        } else {
            NETHER_POOL.add(this);
        }

        ALL_POOL.add(this);
    }

    public WomanVariantRegistry model(EntityModel model) {

        this.model = model;

        return this;
    }

    public WomanVariantRegistry model(EntityModel model, EntityModel decorationModel) {

        this.model = model;
        this.decorationModel = decorationModel;

        return this;
    }

    public WomanVariantRegistry sound(String randomSound, String hurtSound, String deathSound) {

        this.randomSound = randomSound;
        this.hurtSound = hurtSound;
        this.deathSound = deathSound;

        return this;
    }
}
