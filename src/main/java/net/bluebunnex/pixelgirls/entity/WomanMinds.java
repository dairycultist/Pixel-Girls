package net.bluebunnex.pixelgirls.entity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class WomanMinds {

    public static final WomanMind GENERIC_MIND = new WomanMind() {

        private final ItemStack[][] tradePairs = {
                {
                        new ItemStack(Item.COOKED_FISH, 5), new ItemStack(Item.DIAMOND, 1)
                }
        };

        @Override
        public ItemStack[] getTradePair(int i) {
            return tradePairs[i];
        }

        @Override
        public int getTradePairCount() {
            return tradePairs.length;
        }

        @Override
        public String sayLoveDiamonds(Random random) {

            return switch (random.nextInt(0, 3)) {
                case 0 -> "Nice, I have a few of those.";
                case 1 -> "Take me where they sell those!";
                default -> "You willing to trade that?";
            };
        }

        @Override
        public String sayWrongItem(Random random, ItemStack stack) {
            return "I didn't ask for " + stack.getItem().getTranslatedName();
        }

        @Override
        public String sayRightItem(Random random, ItemStack stack) {
            return stack.count + " " + stack.getItem().getTranslatedName() + "! Just what I needed.";
        }

        @Override
        public String sayDamage(Random random) {

            return switch (random.nextInt(0, 3)) {
                case 0 -> "Ow, stop!";
                case 1 -> "That hurts!";
                default -> "Wahh!";
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

            ItemStack[] tradePair = getTradePair(0);

            return switch (random.nextInt(0, 4)) {
                case 0 -> "Isn't today nice?";
                case 1 -> "I'll follow you around if you have some diamonds!";
                case 2 -> "Do you know anywhere safe I can set up shop?";
                case 3 -> "I'd love to settle down in a village someday...";
                default ->
                        "I trade " + tradePair[0].count + " " +
                                tradePair[0].getItem().getTranslatedName() + " for " +
                                tradePair[1].count + " " +
                                tradePair[1].getItem().getTranslatedName() + " if you're interested!";
            };
        }
    };

    // senko says Eep!
}
