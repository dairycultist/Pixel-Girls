package net.dairycultist.pixelgirls.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

public class ShortstackEntityModel extends BipedEntityModel {

    public ModelPart breasts;

    public ShortstackEntityModel() {
        super();

        // cuboid position = position relative to pivot
        // pivot position  = global space
        // positive y = down

        this.body.addCuboid(-4f, 0f, -2f, 8, 10, 4);
        this.body.pivotY += 2f;

        this.leftArm.addCuboid(-1f, -2f, -2f, 4, 10, 4);
        this.leftArm.pivotY += 2f;

        this.rightArm.addCuboid(-3f, -2f, -2f, 4, 10, 4);
        this.rightArm.pivotY += 2f;

        this.leftLeg.roll = (float) (Math.PI / 16);
        this.rightLeg.roll = (float) (-Math.PI / 16);


        this.leftLeg.addCuboid(-2.5f, 0f, -2.5f, 5, 12, 5);
        this.leftLeg.pivotX += 1f;

        this.rightLeg.addCuboid(-2.5f, 0f, -2.5f, 5, 12, 5);
        this.rightLeg.pivotX -= 1f;

        this.breasts = new ModelPart(40, 0);
        this.breasts.addCuboid(0f, 0f, -5f, 10, 5, 5);
        this.breasts.setPivot(-5f, 3f, -2f);
        this.breasts.pitch = (float) (Math.PI / 8);
    }

    // override cuz we don't want to render the hat
    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        this.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        if (this.sneaking) {
            this.head.pivotY = 3.0F;
        } else {
            this.head.pivotY = 2.0F;
        }

        this.head.render(scale);
        this.body.render(scale);
        this.rightArm.render(scale);
        this.leftArm.render(scale);
        this.rightLeg.render(scale);
        this.leftLeg.render(scale);
        this.breasts.render(scale);
    }
}
