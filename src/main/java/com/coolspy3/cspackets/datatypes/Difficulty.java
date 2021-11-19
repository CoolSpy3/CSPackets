package com.coolspy3.cspackets.datatypes;

public enum Difficulty
{

    PEACEFUL(0), EASY(1), NORMAL(2), HARD(3);

    public final byte id;

    private Difficulty(int id)
    {
        this((byte) id);
    }

    private Difficulty(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static Difficulty withId(byte id)
    {
        for (Difficulty mode : values())
            if (mode.id == id) return mode;

        return null;
    }

}
