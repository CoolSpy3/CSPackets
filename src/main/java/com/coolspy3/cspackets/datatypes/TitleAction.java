package com.coolspy3.cspackets.datatypes;

public enum TitleAction
{

    SET_TITLE(0), SET_SUBTITLE(1), SET_TIMES(2), HIDE(3), RESET(4);

    public final int id;

    private TitleAction(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static TitleAction withId(int id)
    {
        for (TitleAction val : values())
            if (val.id == id) return val;

        return null;
    }

}
