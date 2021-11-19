package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = Packet.VarInt.class, direction = PacketDirection.SERVERBOUND)
public class ServerboundKeepAlivePacket extends Packet
{

    public final int keepAliveId;

    public ServerboundKeepAlivePacket(int keepAliveId)
    {
        this.keepAliveId = keepAliveId;
    }

    public ServerboundKeepAlivePacket(Object[] args)
    {
        this((int) args[0]);
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {keepAliveId};
    }

}
