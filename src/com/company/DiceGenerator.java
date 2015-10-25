package com.company;

//import random for my rng
import java.util.Random;

//define class DiceGenerator
public abstract class DiceGenerator
{
    //Data
    protected static Random RNG = new Random();

    //roll a die
    static int roll(int sides) {
        return RNG.nextInt(sides) + 1;
    }

    static int roll() {
        return roll(Main.SIDES_PER_DIE);
    }
}