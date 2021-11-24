package com.coolspy3.cspackets.datatypes;

public enum ResourcePackStatus
{

    SUCCESSFULLY_LOADED(0), DECLINED(1), FAILED_DOWNLOAD(2), ACCEPTED(3);

    public final int id;

    private ResourcePackStatus(int id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public static ResourcePackStatus withId(int id)
    {
        for (ResourcePackStatus val : values())
            if (val.id == id) return val;

        return null;
    }

}
