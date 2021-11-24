package com.coolspy3.cspackets.datatypes;

public enum TeamAction
{
    CREATE(0), REMOVE(1), UPDATE(2), ADD_PLAYERS(3), REMOVE_PLAYERS(4);

    public final byte id;

    private TeamAction(int id)
    {
        this((byte) id);
    }

    private TeamAction(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static TeamAction withId(byte id)
    {
        for (TeamAction val : values())
            if (val.id == id) return val;

        return null;
    }
}
