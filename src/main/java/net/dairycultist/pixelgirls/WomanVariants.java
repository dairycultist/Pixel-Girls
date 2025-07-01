package net.dairycultist.pixelgirls;

public enum WomanVariants {

    MIKU("Miku"),
    KOISHI("Koishi"),
    SAKURA_MIKU("Sakura Miku"),
    SENKO("Senko");

    public static final WomanVariants[] OVERWORLD_POOL = { MIKU, KOISHI, SAKURA_MIKU };
    public static final WomanVariants[] NETHER_POOL = { SENKO };

    public final String name;
    public final int id;

    WomanVariants(String name) {
        this.name = name;
        this.id = this.ordinal();
    }
}
