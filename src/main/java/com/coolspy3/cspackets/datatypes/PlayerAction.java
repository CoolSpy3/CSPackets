package com.coolspy3.cspackets.datatypes;

public enum PlayerAction
{

    START_SNEAKING(0), STOP_SNEAKING(1), LEAVE_BED(2), START_SPRINTING(3), STOP_SPRINTING(
            4), JUMP_WITH_HORSE(5), OPEN_RIDDEN_HORSE_INVENTORY(6);

    public final int id;

    private PlayerAction(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static PlayerAction withId(int id)
    {
        for (PlayerAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
