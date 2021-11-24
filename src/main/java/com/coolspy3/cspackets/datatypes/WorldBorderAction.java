package com.coolspy3.cspackets.datatypes;

public enum WorldBorderAction
{

    SET_SIZE(0), LERP_SIZE(1), SET_CENTER(2), INITIALIZE(3), SET_WARNING_TIME(
            4), SET_WARNING_BLOCKS(5);

    public final int id;

    private WorldBorderAction(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static WorldBorderAction withId(int id)
    {
        for (WorldBorderAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
