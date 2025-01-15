package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.DialogueContainer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.ScreenScaler;
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
    private static ItemRenderer ITEM_RENDERER = new ItemRenderer();

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {

        ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
        int cx = scaler.getScaledWidth() / 2;
        int cy = scaler.getScaledHeight() - 70;

        TextRenderer textRenderer = this.minecraft.textRenderer;

        // render dialogue
        // TODO make dialogue box size adaptive/make text wrap at word
        DialogueContainer dialogueContainer = (DialogueContainer) this.minecraft.player;

        if (dialogueContainer.pixel_girls$hasDialogue()) {

            String dialogue = dialogueContainer.pixel_girls$getDialogue();
            String speakerName = dialogueContainer.pixel_girls$getSpeakerName();

            // box behind text to make it easier to read (stolen from DrawContext)
            GL11.glPushMatrix();

            int x1 = cx - 80, x2 = cx + 80,
                y1 = cy + 8,  y2 = cy + 30;

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

            // text
            textRenderer.drawWithShadow(speakerName, cx - textRenderer.getWidth(speakerName) / 2, cy + 10, -4473925);
            textRenderer.drawWithShadow(dialogue, cx - textRenderer.getWidth(dialogue) / 2, cy + 20, -1);
        }
    }
}
