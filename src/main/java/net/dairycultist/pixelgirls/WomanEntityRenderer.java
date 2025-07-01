package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.ShortstackEntityModel;
import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public class WomanEntityRenderer extends LivingEntityRenderer {

    private static final EntityModel MODEL_GENERIC = new WomanEntityModel();
    private static final EntityModel MODEL_SHORTSTACK = new ShortstackEntityModel();

    public WomanEntityRenderer() {
        super(new WomanEntityModel(), 0.5f);
    }

    public void render(LivingEntity livingEntity, double d, double e, double f, float g, float h) {

        if (((WomanEntity) livingEntity).getVariant() == WomanVariants.MIKU) {
            model = MODEL_SHORTSTACK;
        } else {
            model = MODEL_GENERIC;
        }

        super.render(livingEntity, d, e, f, g, h);
    }
}
