package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class ScoreboardUpdatePacket extends Packet
{

    public final String scoreName;
    public final boolean remove;
    public final String objectiveName;
    public final int value;

    public ScoreboardUpdatePacket(String scoreName, boolean remove, String objectiveName, int value)
    {
        this.scoreName = scoreName;
        this.remove = remove;
        this.objectiveName = objectiveName;
        this.value = value;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ScoreboardUpdatePacket>
    {

        @Override
        public Class<ScoreboardUpdatePacket> getType()
        {
            return ScoreboardUpdatePacket.class;
        }

        @Override
        public ScoreboardUpdatePacket read(InputStream is) throws IOException
        {
            String scoreName = PacketParser.readObject(String.class, is);
            boolean remove = PacketParser.readObject(Boolean.class, is);
            String objectiveName = PacketParser.readObject(String.class, is);

            return new ScoreboardUpdatePacket(scoreName, remove, objectiveName,
                    remove ? 0 : PacketParser.readWrappedObject(VarInt.class, is));
        }

        @Override
        public void write(ScoreboardUpdatePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, packet.scoreName, os);
            PacketParser.writeObject(Boolean.class, packet.remove, os);
            PacketParser.writeObject(String.class, packet.objectiveName, os);

            if (!packet.remove) PacketParser.writeObject(VarInt.class, packet.value, os);
        }

    }

}
