package net.bluebunnex.pixelgirls.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResult;
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

        // TODO when hovering over any woman, it shows their name + "(unmarried)" and their health bar (red pixel thin bar)

        ScreenScaler scaler = new ScreenScaler(this.minecraft.options, this.minecraft.displayWidth, this.minecraft.displayHeight);
        int x = scaler.getScaledWidth() / 2 + 4;
        int y = scaler.getScaledHeight() / 2 - 4;

        HitResult hit = this.minecraft.player.raycast(3, 0.1f);

        if (hit != null)
            System.out.println(hit.entity);

        TextRenderer textRenderer = this.minecraft.textRenderer;

        textRenderer.drawWithShadow("Jenny", x, y, -1);
    }
}
