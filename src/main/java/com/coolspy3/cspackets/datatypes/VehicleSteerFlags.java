package com.coolspy3.cspackets.datatypes;

import java.util.function.Function;

public class VehicleSteerFlags
{

    public final boolean jump, unmount;

    public VehicleSteerFlags(boolean jump, boolean unmount)
    {
        this.jump = jump;
        this.unmount = unmount;
    }

    public static Function<Byte, VehicleSteerFlags> READER =
            data -> new VehicleSteerFlags((data & 0x01) == 0x01, (data & 0x02) == 0x02);

    public static Function<VehicleSteerFlags, Byte> WRITER =
            flags -> (byte) ((flags.jump ? 0x01 : 0x00) | (flags.unmount ? 0x02 : 0x00));

}
