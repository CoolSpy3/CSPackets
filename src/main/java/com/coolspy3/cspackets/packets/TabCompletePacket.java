package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.Position;

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class TabCompletePacket extends Packet
{

    public final String text;
    public final Position targetedBlock;

    public TabCompletePacket(String text)
    {
        this(text, null);
    }

    public TabCompletePacket(String text, Position targetedBlock)
    {
        this.text = text;
        this.targetedBlock = targetedBlock;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<TabCompletePacket>
    {

        @Override
        public Class<TabCompletePacket> getType()
        {
            return TabCompletePacket.class;
        }

        @Override
        public TabCompletePacket read(InputStream is) throws IOException
        {
            String text = PacketParser.readObject(String.class, is);

            if (PacketParser.readObject(Boolean.class, is))
                return new TabCompletePacket(text, PacketParser.readObject(Position.class, is));

            return new TabCompletePacket(text);
        }

        @Override
        public void write(TabCompletePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, packet.text, os);
            PacketParser.writeObject(Boolean.class, packet.targetedBlock != null, os);

            if (packet.targetedBlock != null)
                PacketParser.writeObject(Position.class, packet.targetedBlock, os);
        }

    }

}
