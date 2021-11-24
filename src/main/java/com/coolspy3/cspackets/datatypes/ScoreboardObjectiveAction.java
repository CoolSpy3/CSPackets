package com.coolspy3.cspackets.datatypes;

public enum ScoreboardObjectiveAction
{
    CREATE(0), REMOVE(1), UPDATE(2);

    public final byte id;

    private ScoreboardObjectiveAction(int id)
    {
        this((byte) id);
    }

    private ScoreboardObjectiveAction(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static ScoreboardObjectiveAction withId(byte id)
    {
        for (ScoreboardObjectiveAction val : values())
            if (val.id == id) return val;

        return null;
    }
}
