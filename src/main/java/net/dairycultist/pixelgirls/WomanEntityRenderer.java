package net.dairycultist.pixelgirls;

import net.dairycultist.pixelgirls.model.WomanEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class WomanEntityRenderer extends LivingEntityRenderer {

    public WomanEntityRenderer() {
        super(new WomanEntityModel(), 0.5f);
    }

    public void render(LivingEntity livingEntity, double d, double e, double f, float g, float h) {

        model = ((WomanEntity) livingEntity).getVariant().model;
        decorationModel = ((WomanEntity) livingEntity).getVariant().decorationModel;

        super.render(livingEntity, d, e, f, g, h);
    }

    protected boolean bindTexture(LivingEntity entity, int layer, float tickDelta) {

        if (this.decorationModel == null)
            return false;

        this.bindTexture("/assets/pixelgirls/stationapi/textures/entity/urt.png");

        return true;
    }
}
