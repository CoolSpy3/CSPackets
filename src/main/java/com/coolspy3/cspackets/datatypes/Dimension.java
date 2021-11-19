package com.coolspy3.cspackets.datatypes;

public enum Dimension
{

    NETHER(-1), OVERWORLD(0), END(1);

    public final byte id;

    private Dimension(int id)
    {
        this((byte) id);
    }

    private Dimension(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static Dimension withId(byte id)
    {
        for (Dimension mode : values())
            if (mode.id == id) return mode;

        return null;
    }

}
