package net.dairycultist.pixelgirls.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class CowWomanDecorationModel extends EntityModel {

    public ModelPart leftEar; // +x
    public ModelPart rightEar; // -x
    public ModelPart bell;

    public CowWomanDecorationModel(float headPivotY) {
        super();

        // TODO add cow horns

        this.leftEar = new ModelPart(42, 4);
        this.leftEar.mirror = true;
        this.leftEar.addCuboid(3f, -4f, -0.5f, 4, 3, 1);
        this.leftEar.setPivot(0f, headPivotY, 0f);
        this.leftEar.roll = (float) (Math.PI / 16);

        this.rightEar = new ModelPart(42, 4);
        this.rightEar.addCuboid(-7f, -4f, -0.5f, 4, 3, 1);
        this.rightEar.setPivot(0f, headPivotY, 0f);
        this.rightEar.roll = (float) (-Math.PI / 16);

        this.bell = new ModelPart(0, 0);
        this.bell.addCuboid(-2.5f, 0f, 0f, 5, 2, 5);
        this.bell.setPivot(0f, headPivotY + 0.5f, -6f);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        this.leftEar.yaw = headYaw / 57.295776f;
        this.leftEar.pitch = headPitch / 57.295776f;

        this.rightEar.yaw = headYaw / 57.295776f;
        this.rightEar.pitch = headPitch / 57.295776f;

        this.leftEar.render(scale);
        this.rightEar.render(scale);
        this.bell.render(scale);
    }
}
