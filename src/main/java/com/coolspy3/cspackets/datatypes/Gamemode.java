package com.coolspy3.cspackets.datatypes;

public enum Gamemode
{

    SURVIVAL(0), CREATIVE(1), ADVENTURE(2), SPECTATOR(3), SURVIVAL_HARDCORE(
            0 | 0x8), CREATIVE_HARDCORE(
                    1 | 0x8), ADVENTURE_HARDCORE(2 | 0x8), SPECTATOR_HARDCORE(3 | 0x8);

    public final byte id;

    private Gamemode(int id)
    {
        this((byte) id);
    }

    private Gamemode(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static Gamemode withId(byte id)
    {
        for (Gamemode val : values())
            if (val.id == id) return val;

        return null;
    }

}
