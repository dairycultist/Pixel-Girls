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
    private String activeSpeakerName;

    @Unique
    private String activeDialogue;

    @Unique
    private int messageTimeLeft;

    @Unique
    public void pixel_girls$pushDialogue(String speakerName, String dialogue) {

        this.activeSpeakerName = speakerName;
        this.activeDialogue = dialogue;
        this.messageTimeLeft = 72;
    }

    @Override
    public boolean pixel_girls$hasDialogue() {
        return activeDialogue != null;
    }

    @Unique
    @Override
    public String pixel_girls$getDialogue() {
        return activeDialogue;
    }

    @Unique
    @Override
    public String pixel_girls$getSpeakerName() {
        return activeSpeakerName;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void fadeDialogue(CallbackInfo ci) {

        if (activeDialogue != null && messageTimeLeft-- <= 0) {

            activeDialogue = null;
            activeSpeakerName = null;
        }
    }
}
