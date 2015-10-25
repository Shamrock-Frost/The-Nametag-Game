package com.company;

public class Ability {

    int abilityCost;
    String abilityName;
    int abilityDamage;
    Buff abilityBuff;

    Ability(int manaCost, String name)
    {
        this.abilityCost = manaCost;
        this.abilityName = name;
    }
}
