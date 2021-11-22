package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class TabCompleteResponsePacket extends Packet
{

    public final String[] matches;

    public TabCompleteResponsePacket(String[] matches)
    {
        this.matches = matches;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<TabCompleteResponsePacket>
    {

        @Override
        public Class<TabCompleteResponsePacket> getType()
        {
            return TabCompleteResponsePacket.class;
        }

        @Override
        public TabCompleteResponsePacket read(InputStream is) throws IOException
        {
            int count = Utils.readVarInt(is);
            String[] matches = new String[count];

            for (int i = 0; i < count; i++)
                matches[i] = Utils.readString(is);

            return new TabCompleteResponsePacket(matches);
        }

        @Override
        public void write(TabCompleteResponsePacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.matches.length, os);

            for (int i = 0; i < packet.matches.length; i++)
                Utils.writeString(packet.matches[i], os);
        }

    }

}
