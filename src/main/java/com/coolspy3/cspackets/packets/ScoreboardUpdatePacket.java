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
            String scoreName = Utils.readString(is);
            boolean remove = is.read() == 0x01;
            String objectiveName = Utils.readString(is);

            return new ScoreboardUpdatePacket(scoreName, remove, objectiveName,
                    remove ? 0 : Utils.readVarInt(is));
        }

        @Override
        public void write(ScoreboardUpdatePacket packet, OutputStream os) throws IOException
        {
            Utils.writeString(packet.scoreName, os);
            os.write(packet.remove ? 0x01 : 0x00);
            Utils.writeString(packet.objectiveName, os);

            if (!packet.remove) Utils.writeVarInt(packet.value, os);
        }

    }

}
