package com.coolspy3.cspackets.datatypes;

public enum GameState
{

    INVALID_BED(0), END_RAINING(1), BEGIN_RAINING(2), CHANGE_GAME_MODE(3), ENTER_CREDITS(
            4), DEMO_MESSAGE(5), ARROW_HITTING_PLAYER(
                    6), FADE_VALUE(7), FADE_TIME(8), PLAY_MOB_APPEARANCE(10);

    public final byte id;

    private GameState(int id)
    {
        this((byte) id);
    }

    private GameState(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static GameState withId(byte id)
    {
        for (GameState val : values())
            if (val.id == id) return val;

        return null;
    }

}
