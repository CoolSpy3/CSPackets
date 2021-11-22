package com.coolspy3.cspackets.datatypes;

public enum BlockUpdateAction
{

    SPAWN_POTENTIALS(1), COMMAND_BLOCK_TEXT(2), BEACON_POWER(3), HEAD(4), FLOWER_TYPE(
            5), BANNER_PATTERNS(6);

    public final byte id;

    private BlockUpdateAction(int id)
    {
        this((byte) id);
    }

    private BlockUpdateAction(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static BlockUpdateAction withId(byte id)
    {
        for (BlockUpdateAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
