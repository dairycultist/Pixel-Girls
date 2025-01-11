package net.bluebunnex.pixelgirls.mixin;

import net.bluebunnex.pixelgirls.DialogueContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements DialogueContainer {

    @Unique
    private String activeDialogue;

    @Unique
    private int messageTimeLeft;

    @Unique
    public void setDialogue(String dialogue) {

        this.activeDialogue = dialogue;
        this.messageTimeLeft = Math.min((int) Math.sqrt(dialogue.length()) * 15, 90);
    }

    @Override
    public String getDialogue() {

        return activeDialogue;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void fadeDialogue(CallbackInfo ci) {

        if (activeDialogue != null && messageTimeLeft-- <= 0)
            activeDialogue = null;
    }
}
