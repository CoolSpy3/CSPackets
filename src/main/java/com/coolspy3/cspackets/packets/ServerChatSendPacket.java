package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = {String.class, Byte.class}, direction = PacketDirection.CLIENTBOUND)
public class ServerChatSendPacket extends Packet
{

    public final String msg;
    public final byte position;

    public ServerChatSendPacket(String msg)
    {
        this(msg, (byte) 0x00);
    }

    public ServerChatSendPacket(String msg, byte position)
    {
        this.msg = msg;
        this.position = position;
    }

    public ServerChatSendPacket(Object[] args)
    {
        this((String) args[0], (byte) args[1]);
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {msg, position};
    }

}
