package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;

public class WomanEntityRenderer extends LivingEntityRenderer {

    public WomanEntityRenderer() {
        super(new WomanEntityModel(), 0.5f);
    }

    public void render(LivingEntity livingEntity, double d, double e, double f, float g, float h) {

        model = ((WomanEntity) livingEntity).getVariant().model;

        super.render(livingEntity, d, e, f, g, h);
    }
}
