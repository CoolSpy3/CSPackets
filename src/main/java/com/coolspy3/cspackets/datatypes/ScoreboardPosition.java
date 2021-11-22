package com.coolspy3.cspackets.datatypes;

public enum ScoreboardPosition
{

    LIST(0), SIDEBAR(1), BELOW_NAME(2);

    public final byte id;

    private ScoreboardPosition(int id)
    {
        this((byte) id);
    }

    private ScoreboardPosition(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static ScoreboardPosition withId(byte id)
    {
        for (ScoreboardPosition val : values())
            if (val.id == id) return val;

        return null;
    }

}
