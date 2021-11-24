package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.datatypes.Position;

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class TabCompletePacket extends Packet
{

    public final String text;
    public final Position lookedAtBlock;

    public TabCompletePacket(String text)
    {
        this(text, null);
    }

    public TabCompletePacket(String text, Position lookedAtBlock)
    {
        this.text = text;
        this.lookedAtBlock = lookedAtBlock;
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
            String text = Utils.readString(is);

            if (is.read() == 0x01)
                return new TabCompletePacket(text, PacketParser.readObject(Position.class, is));

            return new TabCompletePacket(text);
        }

        @Override
        public void write(TabCompletePacket packet, OutputStream os) throws IOException
        {
            Utils.writeString(packet.text, os);

            if (packet.lookedAtBlock != null)
            {
                os.write(0x01);
                PacketParser.writeObject(Position.class, packet.lookedAtBlock, os);
            }
            else
                os.write(0x00);
        }

    }

}
