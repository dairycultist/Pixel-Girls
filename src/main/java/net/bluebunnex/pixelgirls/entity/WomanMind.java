package net.bluebunnex.pixelgirls.entity;

import net.minecraft.item.ItemStack;

import java.util.Random;

public interface WomanMind {

    // TODO "Missions" save NBT for mission index (-1 for idle) and mission dialogue progress
    // "Hey! I have a favour."
    // "Can you get me a diamond?"

    String sayIdle(Random random);

    String sayLoveDiamonds(Random random);

    String sayDamage(Random random);

    String sayEat(Random random, ItemStack stack);

    String sayNotHungry(Random random, ItemStack stack);
}
