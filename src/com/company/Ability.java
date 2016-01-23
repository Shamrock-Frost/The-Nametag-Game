package com.company;

public enum Ability {
    Firebolt(2, "Fire Bolt", 1, Buff.Fire, "Mage", 5, true, 1),
    BurningGround(2, "Burning Ground", 0, Buff.Fire, "Mage", 10, true, 0);

    private int abilityCost;
    private String abilityName;
    private int abilityDamage;
    private Buff abilityBuff;
    private String abilityClassReq;
    private int abilityLevelReq;
    private int abilityCategory;
    private boolean abilityIsExhaust;
    private boolean abilityIsExhausted = true;


    Ability(int cost, String name, int damage, Buff buff, String classReq, int levelReq, boolean isExhaust, int category) {
        if(isExhaust) abilityIsExhausted = false;
        abilityCost = cost;
        abilityName = name;
        abilityDamage = damage;
        abilityBuff = buff;
        abilityClassReq = classReq;
        abilityLevelReq = levelReq;
        abilityIsExhaust = isExhaust;
        abilityCategory = category;
    }

    public void cast(Character target) {
        switch(abilityCategory) {
            //Just Deal Damage
            case 0:
                target.dealDamage(this.abilityDamage);
                switch(this) {
                    case Firebolt:
                        if(target.hasBuff(Buff.Fire)) target.dealDamage(this.abilityDamage);
                        break;
                }
                break;
            case 1:
        }
    }

    public int getAbilityCost() {return abilityCost;}
    public String getAbilityName() {return abilityName;}
    public int getAbilityDamage() {return abilityDamage;}
    public Buff getAbilityBuff() {return abilityBuff;}
    public String getAbilityClassReq() {return abilityClassReq;}
    public int getAbilityLevelReq() {return abilityLevelReq;}
    public Boolean getIfExhaust() {return abilityIsExhaust;}

    public Boolean getIfExhausted() {return abilityIsExhausted;}
    public void exhaustAbility() {abilityIsExhausted = true;}
}