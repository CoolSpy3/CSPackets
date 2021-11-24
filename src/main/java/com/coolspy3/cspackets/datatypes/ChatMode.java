package com.coolspy3.cspackets.datatypes;

public enum ChatMode
{

    ENABLED(0), COMMANDS_ONLY(1), HIDDEN(2);

    public final byte id;

    private ChatMode(int id)
    {
        this((byte) id);
    }

    private ChatMode(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static ChatMode withId(byte id)
    {
        for (ChatMode val : values())
            if (val.id == id) return val;

        return null;
    }

}
