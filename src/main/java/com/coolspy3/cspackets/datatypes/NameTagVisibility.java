package com.coolspy3.cspackets.datatypes;

public enum NameTagVisibility
{

    ALWAYS("always"), HIDE_FOR_OTHER_TEAMS("hideForOtherTeams"), HIDE_FOR_OWN_TEAM(
            "hideForOwnTeam"), NEVER("never");

    public final String id;

    private NameTagVisibility(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public static NameTagVisibility withId(String id)
    {
        for (NameTagVisibility val : values())
            if (val.id.equals(id)) return val;

        return null;
    }

}
