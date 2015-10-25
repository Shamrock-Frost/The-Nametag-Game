package com.company;

public enum Buff
{
    PowerBuff(2, 4, "Empowered"),
    AxeBuff(2, 999, null),
    DefenseBuff(2, 4, "Harden"),
    SpeedBuff(2, 4, "Quickened"),
    SpeedDebuff(-2, 4, "Slowed"),
    DefenseDebuff(-2, 4, "Weakened"),
    CriticalHitDebuff(-1, 4, "Immobilized"),
    DamageDebuff(2, 4, "Targeted"),
    CriticalMissDebuff(1, 4, "Blinded"),
    EvilFire(1, 6, "Shadow Fire"),
    Fire(2, 4, "On Fire"),
    Poison(1, 5, "Poisoned");

    private int removalThreshold;
    private int buffAmount;
    private String buffName;

    Buff(int amount, int threshold, String name)
    {
        this.buffAmount = amount;
        this.removalThreshold = threshold;
        this.buffName = name;
    }

    public int getBuffAmount()
    {
        return buffAmount;
    }

    public boolean rollToKeep()
    {
        return DiceGenerator.roll() < removalThreshold;
    }

    public String getBuffName()
    {
        return buffName;
    }
}
