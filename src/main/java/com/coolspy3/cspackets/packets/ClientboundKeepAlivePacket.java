package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = Packet.VarInt.class, direction = PacketDirection.CLIENTBOUND)
public class ClientboundKeepAlivePacket extends Packet
{

    public final int keepAliveId;

    public ClientboundKeepAlivePacket(int keepAliveId)
    {
        this.keepAliveId = keepAliveId;
    }

    public ClientboundKeepAlivePacket(Object[] args)
    {
        this((int) args[0]);
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {keepAliveId};
    }

}
