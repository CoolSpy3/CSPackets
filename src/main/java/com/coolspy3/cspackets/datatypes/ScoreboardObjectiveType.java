package com.coolspy3.cspackets.datatypes;

public enum ScoreboardObjectiveType
{
    INTEGER("integer"), HEARTS("hearts");

    public final String id;

    private ScoreboardObjectiveType(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public static ScoreboardObjectiveType withId(String id)
    {
        for (ScoreboardObjectiveType val : values())
            if (val.id.equals(id)) return val;

        return null;
    }
}
