package net.dairycultist.pixelgirls.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;

public class CowWomanDecorationModel extends EntityModel {

    public ModelPart bell;

    public CowWomanDecorationModel(float headPivotY) {
        super();

        // TODO add cow ears and cow horns

        this.bell = new ModelPart(0, 0);
        this.bell.addCuboid(-2.5f, 0f, 0f, 5, 2, 5);
        this.bell.setPivot(0f, headPivotY + 0.5f, -6f);
    }

    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        this.bell.render(scale);
    }
}
