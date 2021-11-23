package com.coolspy3.cspackets.datatypes;

public enum PlayerDiggingType
{

    STARTED_DIGGING(0), CANCELED_DIGGING(1), FINISHED_DIGGING(2), DROP_ITEM_STACK(3), DTOP_ITEM(
            4), SHOOT_ARROW_FINISH_EATING(5);

    public final byte id;

    private PlayerDiggingType(int id)
    {
        this((byte) id);
    }

    private PlayerDiggingType(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static PlayerDiggingType withId(byte id)
    {
        for (PlayerDiggingType val : values())
            if (val.id == id) return val;

        return null;
    }

}
