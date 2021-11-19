package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = String.class, direction = PacketDirection.SERVERBOUND)
public class ClientChatSendPacket extends Packet
{

    public final String msg;

    public ClientChatSendPacket(String msg)
    {
        this.msg = msg;
    }

    public ClientChatSendPacket(Object[] args)
    {
        this((String) args[0]);
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {msg};
    }

}
