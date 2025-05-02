package net.bluebunnex.pixelgirls.entity;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface WomanMind {

    // gives information on what a woman trades and how she speaks

    public ItemStack[] getTradePair(int i);

    public int getTradePairCount();

    String sayIdle(Random random);

    public String sayLoveDiamonds(Random random);

    String sayDamage(Random random);

    String sayWrongItem(Random random, ItemStack stack);

    String sayRightItem(Random random, ItemStack stack);

    String sayEat(Random random, ItemStack stack);

    String sayNotHungry(Random random, ItemStack stack);
}
