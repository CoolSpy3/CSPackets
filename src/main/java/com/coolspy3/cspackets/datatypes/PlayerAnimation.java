package com.coolspy3.cspackets.datatypes;

public enum PlayerAnimation
{

    SWING_ARM(0), TAKE_DAMAGE(1), LEAVE_BED(2), EAT_FOOD(3), CRITICAL_EFFECT(
            4), MAGIC_CRITICAL_EFFECT(5);

    public final byte id;

    private PlayerAnimation(int id)
    {
        this((byte) id);
    }

    private PlayerAnimation(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static PlayerAnimation withId(byte id)
    {
        for (PlayerAnimation val : values())
            if (val.id == id) return val;

        return null;
    }

}
