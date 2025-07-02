package net.dairycultist.pixelgirls.model;

import net.minecraft.client.model.ModelPart;

public class CowWomanEntityModel extends ShortstackEntityModel {

    public ModelPart bell;

    public CowWomanEntityModel() {
        super();

        // make cow features an accessory? same with miku braids
        // TODO add cow ears and cow horns

        this.bell = new ModelPart(32, 11);
        this.bell.addCuboid(-2.5f, 0f, 0f, 5, 2, 5);
        this.bell.setPivot(0f, 2.5f, -6f);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.render(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        this.bell.render(scale);
    }
}
