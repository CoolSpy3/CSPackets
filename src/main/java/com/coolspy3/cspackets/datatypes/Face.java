package com.coolspy3.cspackets.datatypes;

public enum Face
{

    Y_NEG(0), Y_POS(1), Z_NEG(2), Z_POS(3), X_NEG(4), X_POS(5);

    public final byte id;

    private Face(int id)
    {
        this((byte) id);
    }

    private Face(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static Face withId(byte id)
    {
        for (Face val : values())
            if (val.id == id) return val;

        return null;
    }

}
