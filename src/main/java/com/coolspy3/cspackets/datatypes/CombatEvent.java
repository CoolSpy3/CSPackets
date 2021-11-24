package com.coolspy3.cspackets.datatypes;

public enum CombatEvent
{

    ENTER_COMBAT(0), END_COMBAT(1), ENTITY_DEAD(2);

    public final int id;

    private CombatEvent(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static CombatEvent withId(int id)
    {
        for (CombatEvent val : values())
            if (val.id == id) return val;

        return null;
    }

}
