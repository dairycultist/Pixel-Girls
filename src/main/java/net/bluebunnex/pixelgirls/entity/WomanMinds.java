package net.bluebunnex.pixelgirls.entity;

import net.minecraft.item.ItemStack;

import java.util.Random;

public class WomanMinds {

    public static final WomanMind GENERIC_MIND = new WomanMind() {

        @Override
        public String sayLoveDiamonds(Random random) {

            return switch (random.nextInt(0, 3)) {
                case 0 -> "Nice, I have a few of those.";
                case 1 -> "I love diamonds! Can I have it?";
                default -> "Where do you keep the rest of those?";
            };
        }

        @Override
        public String sayDamage(Random random) {

            return switch (random.nextInt(0, 3)) {
                case 0 -> "Ow, stop!";
                case 1 -> "That hurts!";
                default -> "Wahh!";
                // senko says Eep!
            };
        }

        @Override
        public String sayEat(Random random, ItemStack stack) {
            return "That " + stack.getItem().getTranslatedName().toLowerCase() + " was delicious!";
        }

        @Override
        public String sayNotHungry(Random random, ItemStack stack) {
            return "No thanks, I'm full!";
        }

        @Override
        public String sayIdle(Random random) {

            return switch (random.nextInt(0, 4)) {
                case 0 -> "Isn't today nice?";
                case 1 -> "I'll follow you around if you have some diamonds!";
                case 2 -> "Do you know anywhere safe I can stay for the night?";
                default -> "I'd love to settle down in a village someday...";
            };
        }
    };
}
