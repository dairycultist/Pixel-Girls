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

    @Override
    public void setAngles(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale) {

        this.head.yaw = headYaw / 57.295776F;
        this.head.pitch = headPitch / 57.295776F;
        this.hat.yaw = this.head.yaw;
        this.hat.pitch = this.head.pitch;
        this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 2.0F * limbDistance * 0.5F;
        this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 2.0F * limbDistance * 0.5F;
        this.rightArm.roll = 0.0F;
        this.leftArm.roll = 0.0F;
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightLeg.yaw = 0.0F;
        this.leftLeg.yaw = 0.0F;
        ModelPart var10000;
        if (this.riding) {
            var10000 = this.rightArm;
            var10000.pitch += -0.62831855F;
            var10000 = this.leftArm;
            var10000.pitch += -0.62831855F;
            this.rightLeg.pitch = -1.2566371F;
            this.leftLeg.pitch = -1.2566371F;
            this.rightLeg.yaw = 0.31415927F;
            this.leftLeg.yaw = -0.31415927F;
        }

        if (this.leftArmPose) {
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.31415927F;
        }

        if (this.rightArmPose) {
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.31415927F;
        }

        this.rightArm.yaw = 0.0F;
        this.leftArm.yaw = 0.0F;
        if (this.handSwingProgress > -9990.0F) {
            float var7 = this.handSwingProgress;
            this.body.yaw = MathHelper.sin(MathHelper.sqrt(var7) * 3.1415927F * 2.0F) * 0.2F;
            this.rightArm.pivotZ = MathHelper.sin(this.body.yaw) * 5.0F;
            this.rightArm.pivotX = -MathHelper.cos(this.body.yaw) * 5.0F;
            this.leftArm.pivotZ = -MathHelper.sin(this.body.yaw) * 5.0F;
            this.leftArm.pivotX = MathHelper.cos(this.body.yaw) * 5.0F;
            var10000 = this.rightArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.yaw += this.body.yaw;
            var10000 = this.leftArm;
            var10000.pitch += this.body.yaw;
            var7 = 1.0F - this.handSwingProgress;
            var7 *= var7;
            var7 *= var7;
            var7 = 1.0F - var7;
            float var8 = MathHelper.sin(var7 * 3.1415927F);
            float var9 = MathHelper.sin(this.handSwingProgress * 3.1415927F) * -(this.head.pitch - 0.7F) * 0.75F;
            var10000 = this.rightArm;
            var10000.pitch = (float)((double)var10000.pitch - ((double)var8 * 1.2 + (double)var9));
            var10000 = this.rightArm;
            var10000.yaw += this.body.yaw * 2.0F;
            this.rightArm.roll = MathHelper.sin(this.handSwingProgress * 3.1415927F) * -0.4F;
        }

        // cute anime girl swaying idle animation component
        this.rightArm.roll += (MathHelper.cos(animationProgress * 0.3F) * 0.15F + 0.15F) / (limbDistance + 1);
        this.leftArm.roll -= (MathHelper.cos(animationProgress * 0.3F) * 0.15F + 0.15F) / (limbDistance + 1);
        this.head.roll = (MathHelper.sin(animationProgress * 0.15F) * 0.1F) / (limbDistance * 4 + 1);
    }
}
