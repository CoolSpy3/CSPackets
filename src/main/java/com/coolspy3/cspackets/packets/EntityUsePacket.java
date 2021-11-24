package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.EntityInteractionType;

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class EntityUsePacket extends Packet
{

    public final int target;
    public final EntityInteractionType type;
    public final float targetX, targetY, targetZ;

    public EntityUsePacket(int target, float targetX, float targetY, float targetZ)
    {
        this(target, EntityInteractionType.INTERACT_AT, targetX, targetY, targetZ);
    }

    public EntityUsePacket(int target, EntityInteractionType type)
    {
        this(target, type, 0, 0, 0);
    }

    public EntityUsePacket(int target, EntityInteractionType type, float targetX, float targetY,
            float targetZ)
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
            int target = PacketParser.readWrappedObject(Packet.VarInt.class, is);
            EntityInteractionType type = PacketParser.readObject(EntityInteractionType.class, is);

            if (type == EntityInteractionType.INTERACT_AT)
                return new EntityUsePacket(target, PacketParser.readObject(Float.class, is),
                        PacketParser.readObject(Float.class, is),
                        PacketParser.readObject(Float.class, is));

            return new EntityUsePacket(target, type);
        }

        @Override
        public void write(EntityUsePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Packet.VarInt.class, packet.target, os);
            PacketParser.writeObject(EntityInteractionType.class, packet.type, os);

            if (packet.type != EntityInteractionType.INTERACT_AT) return;

            PacketParser.writeObject(Float.class, packet.targetX, os);
            PacketParser.writeObject(Float.class, packet.targetY, os);
            PacketParser.writeObject(Float.class, packet.targetZ, os);
        }

    }
}
