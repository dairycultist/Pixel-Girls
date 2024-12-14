package net.bluebunnex.pprops.entity;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class BouncyBallEntityRenderer extends EntityRenderer {

    // code stolen from ItemRenderer class
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch) {

        GL11.glPushMatrix();

        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(32826);

        GL11.glScalef(0.5F, 0.5F, 0.5F);

        this.bindTexture("/assets/pprops/stationapi/textures/item/bouncy_ball.png");

        Tessellator var15 = Tessellator.INSTANCE;
        float var16 = 0;
        float var17 = 1f;
        float var18 = 0;
        float var19 = 1f;

        float var20 = 1.0F;
        float var21 = 0.5F;
        float var22 = 0.25F;

        //GL11.glColor4f(var24 * var27, var25 * var27, var26 * var27, 1.0F);

        // render
        GL11.glPushMatrix();

        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        var15.startQuads();
        var15.normal(0.0F, 1.0F, 0.0F);
        var15.vertex((double)(0.0F - var21), (double)(0.0F - var22), 0.0, (double)var16, (double)var19);
        var15.vertex((double)(var20 - var21), (double)(0.0F - var22), 0.0, (double)var17, (double)var19);
        var15.vertex((double)(var20 - var21), (double)(1.0F - var22), 0.0, (double)var17, (double)var18);
        var15.vertex((double)(0.0F - var21), (double)(1.0F - var22), 0.0, (double)var16, (double)var18);
        var15.draw();
        GL11.glPopMatrix();

        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
