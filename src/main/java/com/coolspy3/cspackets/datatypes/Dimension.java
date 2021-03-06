package com.coolspy3.cspackets.datatypes;

import com.coolspy3.csmodloader.network.packet.WrapperType;

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
        for (Dimension val : values())
            if (val.id == id) return val;

        return null;
    }

    public static class AsByte extends WrapperType<Dimension>
    {}

    public static class AsInt extends WrapperType<Dimension>
    {}

}
