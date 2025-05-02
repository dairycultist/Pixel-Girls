package net.bluebunnex.pixelgirls.entity;

import net.minecraft.item.Item;

import java.util.Random;

public interface WomanMind {

    // gives information on what a woman trades and how she speaks

    public String sayLoveDiamonds(Random random);

    String sayDamage(Random random);

    String sayEat(Random random, Item heldItem);

    String sayIdle(Random random);
}
