package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.entity.WomanEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResultType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public class InGameHudMixin {

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {

        // when hovering over any woman, it shows their name + "(unmarried)"
        // TODO and their health bar (red pixel thin bar)

        if (
            this.minecraft.crosshairTarget != null
            && this.minecraft.crosshairTarget.type == HitResultType.ENTITY
            && this.minecraft.crosshairTarget.entity instanceof WomanEntity woman
        ) {

            ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
            int x = scaler.getScaledWidth() / 2 + 10;
            int y = scaler.getScaledHeight() / 2 - 3 - 5;

            String text = woman.name + (woman.marriedTo == null ? " (unmarried)" : " (married to " + woman.marriedTo + ")");

            TextRenderer textRenderer = this.minecraft.textRenderer;

            // box behind text to make it easier to read (stolen from DrawContext)
            {
                GL11.glPushMatrix();

                int x1 = x - 2, x2 = x + 2 + textRenderer.getWidth(text),
                    y1 = y - 2, y2 = y + 20;

                Tessellator tessellator = Tessellator.INSTANCE;
                GL11.glEnable(3042);
                GL11.glDisable(3553);
                GL11.glBlendFunc(770, 771);
                GL11.glColor4f(0f, 0f, 0.03f, 0.5f);

                tessellator.startQuads();
                tessellator.vertex((double) x1, (double) y2, 0.0);
                tessellator.vertex((double) x2, (double) y2, 0.0);
                tessellator.vertex((double) x2, (double) y1, 0.0);
                tessellator.vertex((double) x1, (double) y1, 0.0);
                tessellator.draw();

                GL11.glEnable(3553);
                GL11.glDisable(3042);

                GL11.glPopMatrix();
            }

            textRenderer.drawWithShadow(text, x, y, -1);
            textRenderer.drawWithShadow(woman.health + "/20", x, y + 10, -2236963);
        }
    }
}
