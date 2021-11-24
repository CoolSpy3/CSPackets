package com.coolspy3.cspackets.datatypes;

import java.util.function.Function;

public class SkinParts
{

    public final boolean capeEnabled, jacketEnabled, leftSleveEnabled, rightSleveEnabled,
            leftPantsLegEnabled, rightPantsLegEnabled, hatEnabled;

    public SkinParts(boolean capeEnabled, boolean jacketEnabled, boolean leftSleveEnabled,
            boolean rightSleveEnabled, boolean leftPantsLegEnabled, boolean rightPantsLegEnabled,
            boolean hatEnabled)
    {
        this.capeEnabled = capeEnabled;
        this.jacketEnabled = jacketEnabled;
        this.leftSleveEnabled = leftSleveEnabled;
        this.rightSleveEnabled = rightSleveEnabled;
        this.leftPantsLegEnabled = leftPantsLegEnabled;
        this.rightPantsLegEnabled = rightPantsLegEnabled;
        this.hatEnabled = hatEnabled;
    }

    public static Function<Byte, SkinParts> READER = data -> new SkinParts((data & 0x01) == 0x01,
            (data & 0x02) == 0x02, (data & 0x04) == 0x04, (data & 0x08) == 0x08,
            (data & 0x10) == 0x10, (data & 0x20) == 0x20, (data & 0x40) == 0x40);

    public static Function<SkinParts, Byte> WRITER =
            flags -> (byte) ((flags.capeEnabled ? 0x01 : 0x00) | (flags.jacketEnabled ? 0x02 : 0x00)
                    | (flags.leftSleveEnabled ? 0x04 : 0x00)
                    | (flags.rightSleveEnabled ? 0x08 : 0x00)
                    | (flags.leftPantsLegEnabled ? 0x10 : 0x00)
                    | (flags.rightPantsLegEnabled ? 0x20 : 0x00)
                    | (flags.hatEnabled ? 0x40 : 0x00));

}
