package com.coolspy3.cspackets.datatypes;

public enum FriendlyFireSetting
{
    OFF(0), ON(1), SEE_FRIENDLY_INVISIBLES(3);

    public final byte id;

    private FriendlyFireSetting(int id)
    {
        this((byte) id);
    }

    private FriendlyFireSetting(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static FriendlyFireSetting withId(byte id)
    {
        for (FriendlyFireSetting val : values())
            if (val.id == id) return val;

        return null;
    }
}
