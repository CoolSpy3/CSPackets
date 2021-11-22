package com.coolspy3.cspackets.datatypes;

import java.util.function.Function;

public class PlayerAbilities
{

    public final boolean invulnerable, flying, allowFlying, creativeMode;

    public PlayerAbilities(boolean invulnerable, boolean flying, boolean allowFlying,
            boolean creativeMode)
    {
        this.invulnerable = invulnerable;
        this.flying = flying;
        this.allowFlying = allowFlying;
        this.creativeMode = creativeMode;
    }

    public static Function<Byte, PlayerAbilities> READER =
            data -> new PlayerAbilities((data & 0x01) == 0x01, (data & 0x02) == 0x02,
                    (data & 0x04) == 0x04, (data & 0x08) == 0x08);

    public static Function<PlayerAbilities, Byte> WRITER =
            abilities -> (byte) ((abilities.invulnerable ? 0x01 : 0x00)
                    | (abilities.flying ? 0x02 : 0x00) | (abilities.allowFlying ? 0x04 : 0x00)
                    | (abilities.creativeMode ? 0x08 : 0x00));

}
