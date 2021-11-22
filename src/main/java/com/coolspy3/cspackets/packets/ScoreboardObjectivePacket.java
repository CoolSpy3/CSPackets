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
public class ScoreboardObjectivePacket extends Packet
{

    public final String objectiveName;
    public final Mode mode;
    public final String value;
    public final Type type;

    public ScoreboardObjectivePacket(String objectiveName)
    {
        this(objectiveName, Mode.REMOVE, null, null);
    }

    public ScoreboardObjectivePacket(String objectiveName, Mode mode, String value, Type type)
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

    public static enum Mode
    {
        CREATE(0), REMOVE(1), UPDATE(2);

        public final byte id;

        private Mode(int id)
        {
            this((byte) id);
        }

        private Mode(byte id)
        {
            this.id = id;
        }

        public Byte getId()
        {
            return id;
        }

        public static Mode withId(byte id)
        {
            for (Mode val : values())
                if (val.id == id) return val;

            return null;
        }
    }

    public static enum Type
    {
        INTEGER("integer"), HEARTS("hearts");

        public final String id;

        private Type(String id)
        {
            this.id = id;
        }

        public String getId()
        {
            return id;
        }

        public static Type withId(String id)
        {
            for (Type val : values())
                if (val.id.equals(id)) return val;

            return null;
        }
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
            String objectiveName = Utils.readString(is);
            Mode mode = Mode.withId((byte) is.read());

            if (mode == Mode.REMOVE) return new ScoreboardObjectivePacket(objectiveName);

            return new ScoreboardObjectivePacket(objectiveName, mode, Utils.readString(is),
                    Type.withId(Utils.readString(is)));
        }

        @Override
        public void write(ScoreboardObjectivePacket packet, OutputStream os) throws IOException
        {
            Utils.writeString(packet.objectiveName, os);
            os.write(packet.mode.id);

            if (packet.mode == Mode.REMOVE) return;

            Utils.writeString(packet.value, os);
            Utils.writeString(packet.type.id, os);
        }

    }

}
