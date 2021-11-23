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

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class EntityUsePacket extends Packet
{

    public final int target;
    public final Type type;
    public final float targetX, targetY, targetZ;

    public EntityUsePacket(int target, float targetX, float targetY, float targetZ)
    {
        this(target, Type.INTERACT_AT, targetX, targetY, targetZ);
    }

    public EntityUsePacket(int target, Type type)
    {
        this(target, type, 0, 0, 0);
    }

    public EntityUsePacket(int target, Type type, float targetX, float targetY, float targetZ)
    {
        this.target = target;
        this.type = type;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static enum Type
    {
        INTERACT(0), ATTACK(1), INTERACT_AT(2);

        public final int id;

        private Type(int id)
        {
            this.id = id;
        }

        public Integer getId()
        {
            return id;
        }

        public static Type withId(int id)
        {
            for (Type val : values())
                if (val.id == id) return val;

            return null;
        }
    }

    public static class Serializer implements PacketSerializer<EntityUsePacket>
    {

        @Override
        public Class<EntityUsePacket> getType()
        {
            return EntityUsePacket.class;
        }

        @Override
        public EntityUsePacket read(InputStream is) throws IOException
        {
            int target = Utils.readVarInt(is);
            Type type = Type.withId(Utils.readVarInt(is));

            if (type == Type.INTERACT_AT)
                return new EntityUsePacket(target, PacketParser.readObject(Float.class, is),
                        PacketParser.readObject(Float.class, is),
                        PacketParser.readObject(Float.class, is));

            return new EntityUsePacket(target, type);
        }

        @Override
        public void write(EntityUsePacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.target, os);
            Utils.writeVarInt(packet.type.id, os);

            if (packet.type != Type.INTERACT_AT) return;

            PacketParser.writeObject(Float.class, packet.targetX, os);
            PacketParser.writeObject(Float.class, packet.targetY, os);
            PacketParser.writeObject(Float.class, packet.targetZ, os);
        }

    }
}
