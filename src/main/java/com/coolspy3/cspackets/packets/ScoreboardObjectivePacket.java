package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.ScoreboardObjectiveAction;
import com.coolspy3.cspackets.datatypes.ScoreboardObjectiveType;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class ScoreboardObjectivePacket extends Packet
{

    public final String objectiveName;
    public final ScoreboardObjectiveAction mode;
    public final String value;
    public final ScoreboardObjectiveType type;

    public ScoreboardObjectivePacket(String objectiveName)
    {
        this(objectiveName, ScoreboardObjectiveAction.REMOVE, null, null);
    }

    public ScoreboardObjectivePacket(String objectiveName, ScoreboardObjectiveAction mode,
            String value, ScoreboardObjectiveType type)
    {
        this.objectiveName = objectiveName;
        this.mode = mode;
        this.value = value;
        this.type = type;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ScoreboardObjectivePacket>
    {

        @Override
        public Class<ScoreboardObjectivePacket> getType()
        {
            return ScoreboardObjectivePacket.class;
        }

        @Override
        public ScoreboardObjectivePacket read(InputStream is) throws IOException
        {
            String objectiveName = PacketParser.readObject(String.class, is);
            ScoreboardObjectiveAction mode =
                    PacketParser.readObject(ScoreboardObjectiveAction.class, is);

            if (mode == ScoreboardObjectiveAction.REMOVE)
                return new ScoreboardObjectivePacket(objectiveName);

            return new ScoreboardObjectivePacket(objectiveName, mode,
                    PacketParser.readObject(String.class, is),
                    PacketParser.readObject(ScoreboardObjectiveType.class, is));
        }

        @Override
        public void write(ScoreboardObjectivePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, packet.objectiveName, os);
            PacketParser.writeObject(ScoreboardObjectiveAction.class, packet.mode, os);

            if (packet.mode == ScoreboardObjectiveAction.REMOVE) return;

            PacketParser.writeObject(String.class, packet.value, os);
            PacketParser.writeObject(ScoreboardObjectiveType.class, packet.type, os);
        }

    }

}
