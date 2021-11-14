package com.coolspy3.cspackets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

public class ClientChatSendPacket extends Packet
{

    public final String msg;

    public ClientChatSendPacket(String msg)
    {
        this.msg = msg;
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {msg};
    }

    public static class Spec implements PacketSpec<ClientChatSendPacket>
    {

        @Override
        public ClientChatSendPacket create(Object[] args)
        {
            return new ClientChatSendPacket((String) args[0]);
        }

        @Override
        public Class<?>[] types()
        {
            return new Class[] {String.class};
        }

        @Override
        public Class<ClientChatSendPacket> getType()
        {
            return ClientChatSendPacket.class;
        }

        @Override
        public PacketDirection getDirection()
        {
            return PacketDirection.SERVERBOUND;
        }

    }

}
