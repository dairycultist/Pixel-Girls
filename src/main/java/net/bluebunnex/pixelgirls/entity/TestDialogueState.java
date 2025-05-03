package net.bluebunnex.pixelgirls.entity;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class TestDialogueState extends DialogueState {

    private int i = 0;
    private String[] dialogue = {
            "Hi, this is a test dialogue.",
            "I follow you if you hold a diamond!",
            "I love you very much <3",
            "This dialogue will now repeat :3"
    };

    @Override
    public DialogueState getNextState(Random random, PlayerEntity player) {

        if (i == dialogue.length) {
            return new TestDialogueState();
        }
        return this;
    }

    @Override
    public String getDialogue(Random random, PlayerEntity player) {

//        ItemStack heldStack = player.inventory.getSelectedItem();
//        Item heldItem = heldStack != null ? heldStack.getItem() : null;

        return dialogue[i++];
    }
}


//@Override
//public String sayLoveDiamonds(Random random) {
//
//    return switch (random.nextInt(0, 3)) {
//        case 0 -> "Nice, I have a few of those.";
//        case 1 -> "I love diamonds! Can I have it?";
//        default -> "Where do you keep the rest of those?";
//    };
//}
//
//@Override
//public String sayDamage(Random random) {
//
//    return switch (random.nextInt(0, 3)) {
//        case 0 -> "Ow, stop!";
//        case 1 -> "That hurts!";
//        default -> "Wahh!";
//        // senko says Eep!
//    };
//}
//
//@Override
//public String sayEat(Random random, ItemStack stack) {
//    return "That " + stack.getItem().getTranslatedName().toLowerCase() + " was delicious!";
//}
//
//@Override
//public String sayNotHungry(Random random, ItemStack stack) {
//    return "No thanks, I'm full!";
//}
//
//@Override
//public String sayIdle(Random random) {
//
//    return switch (random.nextInt(0, 4)) {
//        case 0 -> "Isn't today nice?";
//        case 1 -> "I'll follow you around if you have some diamonds!";
//        case 2 -> "Do you know anywhere safe I can stay for the night?";
//        default -> "I'd love to settle down in a village someday...";
//    };
//}



//if (heldItem instanceof FoodItem) {
//
//    if (this.health >= this.maxHealth) {
//
//        dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayNotHungry(this.random, heldStack));
//
//    } else {
//
//        dialogueContainer.pixel_girls$pushDialogue(this.name, this.mind.sayEat(this.random, heldStack));
//
//        this.heal(((FoodItem) heldItem).getHealthRestored());
//
//        for (int i = 0; i < 5; i++) {
//            this.world.addParticle("heart", this.x + Math.random() * 2 - 1, this.y + Math.random() + 0.5, this.z + Math.random() * 2 - 1, 0, 0, 0);
//        }
//
//        player.inventory.removeStack(player.inventory.selectedSlot, 1);
//    }
//}