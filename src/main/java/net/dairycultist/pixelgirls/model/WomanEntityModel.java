package net.dairycultist.pixelgirls.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class WomanEntityModel extends BipedEntityModel {

    public ModelPart breasts;

    public WomanEntityModel() {
        super();

        this.breasts = new ModelPart(40, 0);
        this.breasts.addCuboid(0f, 0f, -4f, 8, 4, 4); // position relative to pivot
        this.breasts.setPivot(-4f, 1f, -2f); // pivot (global space)
        this.breasts.pitch = (float) (Math.PI / 8);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        // we don't call the super method cuz we don't want to render the hat

        this.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        this.head.render(scale);
        this.body.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.breasts.render(scale);
    }
}
