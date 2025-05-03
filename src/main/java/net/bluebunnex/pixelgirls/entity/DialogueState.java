package net.bluebunnex.pixelgirls.entity;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public abstract class DialogueState {

    public abstract String getDialogue(Random random, PlayerEntity player, boolean isAttack);

    // should return "this" until a state change is required
    // dialogue is gotten before this function is called
    public abstract DialogueState getNextState(Random random, PlayerEntity player, boolean isAttack);
}
