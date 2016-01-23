package com.company;

import java.util.HashMap;
import java.util.Map;

//class that defines a character
public class Character {

    //Declare all the stats
    private int health;
    private int attackStat;
    private int defenseStat;
    private int speedStat;
    private String characterName;
    private String characterClass;
    int characterLvl;
    int characterGold;
    int characterXP;
    int mana;
    Map<Buff, Boolean> currentBuffs;

    //set the stats
    public Character(int ATK, int DEF, int SPD, String classParameter, String name) {
        this.attackStat = ATK;
        this.defenseStat = DEF;
        this.speedStat = SPD;
        this.characterName = name;
        this.characterClass = classParameter;
        this.characterLvl = 1 + (getCharacterXP() / 10);
        this.health = getBaseHealth();
        this.characterXP = 0;
        this.mana = 4;
        this.currentBuffs = new HashMap<>();
        this.removeAllBuffs();

        //Make classes give their respective class bonuses.
        switch (characterClass) {
            case "Warrior":
            case "Mage":
                attackStat++;
                break;
            case "Cleric":
            case "Druid":
                defenseStat++;
                break;
            case "Hunter":
                speedStat++;
                break;
            case "berry":
                this.addBuff(Buff.CriticalHitDebuff);
                break;
            case "blue berry":
                this.addBuff(null);
                break;
        }
    }

    //All of my methods. They're simple enough that I won't walk through them.
    public void dealDamage(int damage) {
        health -= damage;
    }

    public boolean isDead() {
        return (health <= 0);
    }

    public boolean hasBuff(Buff b) {
        return currentBuffs.get(b);
    }

    public void addBuff(Buff b) {
        currentBuffs.put(b, true);
    }

    public void removeBuff(Buff b) {
        currentBuffs.put(b, false);
    }

    public int currentBuffAmount(Buff b) {
        if (!hasBuff(b)) {
            return 0;
        }
        return b.getBuffAmount();
    }

    public void removeAllBuffs() {
        for (Buff b : Buff.values()) {
            currentBuffs.put(b, false);
        }
    }

    public void rollForBuffRemoval() {
        //Berries are a test class
        if (this.getCharacterClass().equals("berry")) return;
        for (Buff b : Buff.values()) {
            currentBuffs.put(b, currentBuffs.get(b) && b.rollToKeep());
        }
    }

    public int getCurrentAttack() {
        return attackStat + currentBuffAmount(Buff.PowerBuff);
    }

    public int getCurrentDefense() {
        return defenseStat + currentBuffAmount(Buff.DefenseBuff) + currentBuffAmount(Buff.DefenseDebuff);
    }

    public int getCurrentSpeed() {
        return speedStat + currentBuffAmount(Buff.SpeedBuff) + currentBuffAmount(Buff.SpeedDebuff);
    }

    public int getBaseHealth() {
        return (10 + characterLvl / 2);
    }

    public int getAttackStat() {
        return attackStat;
    }

    public int getDefenseStat() {
        return defenseStat;
    }

    public int getSpeedStat() {
        return speedStat;
    }

    public void setAttackStat(int newATK) {
        attackStat = newATK;
    }

    public void setDefenseStat(int newDEF) {
        defenseStat = newDEF;
    }

    public void setSpeedStat(int newSPD) {
        speedStat = newSPD;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String newName) {
        characterName = newName;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String newCharacterClass) {
        characterClass = newCharacterClass;
    }

    public int getCharacterLevel() {
        return characterLvl;
    }

    public void setCharacterLevel(int newCharacterLvl) {
        characterLvl = newCharacterLvl;
    }

    public int getCharacterXP() {
        return characterXP;
    }

    public void setCharacterXP(int newCharacterXP) {
        characterXP = newCharacterXP;
    }

    public void addCharacterXP(int amountXP) {
        characterXP += amountXP;
    }

    public int getCharacterGold() {
        return characterGold;
    }

    public void setCharacterGold(int newCharacterGold) {
        characterGold = newCharacterGold;
    }

    public void addCharacterGold(int amountGold) {
        characterXP += amountGold;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int newMana) {
        mana = newMana;
    }

    public void changeMana(int increment) {
        setMana(mana + increment);
    }

    public int rollAttack() {
        int roll = DiceGenerator.roll();

        if (roll >= (Main.CRITICAL_HIT_THRESHOLD - currentBuffAmount(Buff.CriticalHitDebuff))) {
            return Main.CRITICAL_HIT_FLAG;
        } else if (roll <= (Main.CRITICAL_MISS_THRESHOLD + currentBuffAmount(Buff.CriticalMissDebuff))) {
            return Main.CRITICAL_MISS_FLAG;
        } else {
            return roll + getCurrentAttack();
        }
    }

    public int rollDefense() {
        int roll = DiceGenerator.roll(Main.SIDES_PER_DIE);
        return roll + getCurrentDefense();
    }

    public int rollSpeed() {
        int roll = DiceGenerator.roll(Main.SIDES_PER_DIE);
        return roll + getCurrentSpeed();
    }

    public void applyDamagingDebuffs() {
        if (hasBuff(Buff.Fire)) {
            health -= currentBuffAmount(Buff.Fire);
            System.out.println("Character " + getCharacterName() + " took " + currentBuffAmount(Buff.Fire) + " Fire damage.");
        }
        if (hasBuff(Buff.EvilFire)) {
            health -= currentBuffAmount(Buff.EvilFire);
            System.out.println("Character " + getCharacterName() + " took " + currentBuffAmount(Buff.EvilFire) + " Shadow Fire damage.");
        }
        if (hasBuff(Buff.Poison)) {
            health -= currentBuffAmount(Buff.Poison);
            System.out.println("Character " + getCharacterName() + " took " + currentBuffAmount(Buff.Poison) + " Poison damage");
        }
    }

    public void cast(Ability a, Character target) {
        a.cast(target);
        changeMana(-1 * a.getAbilityCost());
        if(a.getIfExhaust()) a.exhaustAbility();
    }
}