package com.coolspy3.cspackets.datatypes;

public class PlayerPositionAndLookFlags
{

    public final boolean xRelative, yRelative, zRelative, yawRelative, pitchRelative;

    public PlayerPositionAndLookFlags(boolean xRelative, boolean yRelative, boolean zRelative,
            boolean yawRelative, boolean pitchRelative)
    {
        this.xRelative = xRelative;
        this.yRelative = yRelative;
        this.zRelative = zRelative;
        this.yawRelative = yawRelative;
        this.pitchRelative = pitchRelative;
    }

}
