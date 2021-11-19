package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.Difficulty;
import com.coolspy3.cspackets.datatypes.Dimension;
import com.coolspy3.cspackets.datatypes.Gamemode;

@PacketSpec(types = {Integer.class, Gamemode.class, Dimension.class, Difficulty.class, Byte.class,
        String.class, Boolean.class}, direction = PacketDirection.CLIENTBOUND)
public class GameJoinPacket extends Packet
{

    public final int entityId;
    public final Gamemode gamemode;
    public final Dimension dimension;
    public final Difficulty difficulty;
    public final int maxPlayers;
    public final boolean reducedDebugInfo;

    public GameJoinPacket(int entityId, Gamemode gamemode, Dimension dimension,
            Difficulty difficulty, byte maxPlayers, boolean reducedDebugInfo)
    {
        this.entityId = entityId;
        this.gamemode = gamemode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = Byte.toUnsignedInt(maxPlayers);
        this.reducedDebugInfo = reducedDebugInfo;
    }

    public GameJoinPacket(Object[] args)
    {
        this((int) args[0], Gamemode.withId((byte) args[1]), Dimension.withId((byte) args[2]),
                Difficulty.withId((byte) args[3]), (byte) args[4], (boolean) args[5]);
    }


    @Override
    public Object[] getValues()
    {
        return new Object[] {entityId, gamemode, dimension, difficulty, (byte) (maxPlayers & 0xFF),
                reducedDebugInfo};
    }

}
