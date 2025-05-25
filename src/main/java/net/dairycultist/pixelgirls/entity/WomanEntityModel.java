package net.dairycultist.pixelgirls.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;

public class WomanEntityModel extends BipedEntityModel {

    public ModelPart breasts;

    public WomanEntityModel() {
        super();

        this.breasts = new ModelPart(40, 0);
        this.breasts.addCuboid(0f, 0f, -4f, 8, 4, 4); // position relative to pivot
        this.breasts.setPivot(-4f, 1f, -2f); // pivot (global space)
        this.breasts.pitch = (float) (Math.PI / 8);

        // bigger
        // this.breasts.addCuboid(0f, 0f, -5f, 10, 5, 5); // position relative to pivot
        // this.breasts.setPivot(-5f, 1f, -2f); // pivot (global space)
    }

    // override cuz we don't want to render the hat
    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

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
