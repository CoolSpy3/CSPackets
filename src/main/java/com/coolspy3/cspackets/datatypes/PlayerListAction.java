package com.coolspy3.cspackets.datatypes;

public enum PlayerListAction
{

    ADD_PLAYER(0), UPDATE_GAMEMODE(1), UPDATE_LATENCY(2), UPDATE_DISPLAY_NAME(3), REMOVE_PLAYER(4);

    public final int id;

    private PlayerListAction(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static PlayerListAction withId(int id)
    {
        for (PlayerListAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
