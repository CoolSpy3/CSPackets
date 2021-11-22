package com.coolspy3.cspackets.datatypes;

public enum PaintingDirection
{

    NORTH(0), WEST(1), SOUTH(2), EAST(3);

    public final byte id;

    private PaintingDirection(int id)
    {
        this((byte) id);
    }

    private PaintingDirection(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static PaintingDirection withId(byte id)
    {
        for (PaintingDirection val : values())
            if (val.id == id) return val;

        return null;
    }

}
