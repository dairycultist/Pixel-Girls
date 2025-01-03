package net.bluebunnex.pixelgirls.entity;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.math.MathHelper;

public class WomanEntityModel extends BipedEntityModel {

    public ModelPart breasts;

    public WomanEntityModel() {
        super();

        this.breasts = new ModelPart(16, 21); // best uv for boobs, though skin still needs to be tailored to work well
        this.breasts.addCuboid(0f, 0f, -4f, 8, 4, 4); // position relative to pivot
        this.breasts.setPivot(-4f, 1f, -2f); // pivot (global space)

        this.breasts.pitch = (float) (Math.PI / 4);
    }

    @Override
    public void render(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {
        super.render(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);

        this.breasts.render(scale);
    }

    // TODO cute anime girl swaying idle animation
    @Override
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        // find out what these parameters do by setting them to zero
        super.setAngles(limbAngle, limbDistance, animationProgress, headYaw, headPitch, scale);
    }
}
