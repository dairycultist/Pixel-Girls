package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.entity.WomanEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResultType;
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
            int x = scaler.getScaledWidth() / 2 + 6;
            int y = scaler.getScaledHeight() / 2 - 3;

            TextRenderer textRenderer = this.minecraft.textRenderer;

            textRenderer.drawWithShadow(woman.name + (woman.marriedTo == null ? " (unmarried)" : " married to " + woman.marriedTo), x, y, -1);
            textRenderer.drawWithShadow(woman.health + "/20", x, y + 10, -2236963);
        }
    }
}
