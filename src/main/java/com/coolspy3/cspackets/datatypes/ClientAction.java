package com.coolspy3.cspackets.datatypes;

public enum ClientAction
{

    PERFORM_RESPAWN(0), REQUEST_STATS(1), TAKING_INVENTORY_ACHIEVEMENT(2);

    public final int id;

    private ClientAction(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static ClientAction withId(int id)
    {
        for (ClientAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
