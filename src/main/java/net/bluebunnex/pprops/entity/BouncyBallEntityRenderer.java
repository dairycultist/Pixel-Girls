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

        // change color based on id
//        GL11.glColor4f(
//                entity.id * 2342.134f % 1f,
//                entity.id * 625.214f % 1f,
//                entity.id * 34208.134f % 1f, 1.0F);

        // render
        GL11.glPushMatrix();

        GL11.glRotatef(180.0F - this.dispatcher.yaw, 0.0F, 1.0F, 0.0F);
        var15.startQuads();
        var15.normal(0.0F, 1.0F, 0.0F);
        var15.vertex((double)(0.0F - 0.5F), (double)(0.0F - 0.25F), 0.0, 0, 1);
        var15.vertex((double)(1.0F - 0.5F), (double)(0.0F - 0.25F), 0.0, 1, 1);
        var15.vertex((double)(1.0F - 0.5F), (double)(1.0F - 0.25F), 0.0, 1, 0);
        var15.vertex((double)(0.0F - 0.5F), (double)(1.0F - 0.25F), 0.0, 0, 0);
        var15.draw();
        GL11.glPopMatrix();

        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }
}
