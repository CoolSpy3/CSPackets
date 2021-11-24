package com.coolspy3.cspackets.datatypes;

public enum EntityInteractionType
{

    INTERACT(0), ATTACK(1), INTERACT_AT(2);

    public final int id;

    private EntityInteractionType(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static EntityInteractionType withId(int id)
    {
        for (EntityInteractionType val : values())
            if (val.id == id) return val;

        return null;
    }

}
