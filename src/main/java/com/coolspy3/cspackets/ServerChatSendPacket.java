package com.coolspy3.cspackets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

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

    @Override
    public Object[] getValues()
    {
        return new Object[] {msg, position};
    }

    public static class Spec implements PacketSpec<ServerChatSendPacket>
    {

        @Override
        public ServerChatSendPacket create(Object[] args)
        {
            return new ServerChatSendPacket((String) args[0], (byte) args[1]);
        }

        @Override
        public Class<?>[] types()
        {
            return new Class[] {String.class, Byte.class};
        }

        @Override
        public Class<ServerChatSendPacket> getType()
        {
            return ServerChatSendPacket.class;
        }

        @Override
        public PacketDirection getDirection()
        {
            return PacketDirection.CLIENTBOUND;
        }

    }

}
