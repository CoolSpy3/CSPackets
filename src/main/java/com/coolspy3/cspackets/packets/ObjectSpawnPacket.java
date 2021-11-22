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
public class ObjectSpawnPacket extends Packet
{

    public final int entityId;
    public final byte type;
    public final int x, y, z;
    public final byte pitch, yaw;
    public final int data;
    public final short xVel, yVel, zVel;


    public ObjectSpawnPacket(int entityId, byte type, int x, int y, int z, byte pitch, byte yaw)
    {
        this(entityId, type, x, y, z, pitch, yaw, 0, (short) 0, (short) 0, (short) 0);
    }

    public ObjectSpawnPacket(int entityId, byte type, int x, int y, int z, byte pitch, byte yaw,
            int data, short xVel, short yVel, short zVel)
    {
        this.entityId = entityId;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
        this.data = data;
        this.xVel = xVel;
        this.yVel = yVel;
        this.zVel = zVel;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ObjectSpawnPacket>
    {

        @Override
        public Class<ObjectSpawnPacket> getType()
        {
            return ObjectSpawnPacket.class;
        }

        @Override
        public ObjectSpawnPacket read(InputStream is) throws IOException
        {
            int entityId = PacketParser.readWrappedObject(Packet.VarInt.class, is);
            byte type = PacketParser.readObject(Byte.class, is);
            int x = PacketParser.readObject(Integer.class, is);
            int y = PacketParser.readObject(Integer.class, is);
            int z = PacketParser.readObject(Integer.class, is);
            byte pitch = PacketParser.readObject(Byte.class, is);
            byte yaw = PacketParser.readObject(Byte.class, is);
            int data = PacketParser.readObject(Integer.class, is);

            if (data == 0) return new ObjectSpawnPacket(entityId, type, x, y, z, pitch, yaw);

            short xVel = PacketParser.readObject(Short.class, is);
            short yVel = PacketParser.readObject(Short.class, is);
            short zVel = PacketParser.readObject(Short.class, is);

            return new ObjectSpawnPacket(entityId, type, x, y, z, pitch, yaw, data, xVel, yVel,
                    zVel);
        }

        @Override
        public void write(ObjectSpawnPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Packet.VarInt.class, packet.entityId, os);
            PacketParser.writeObject(Byte.class, packet.type, os);
            PacketParser.writeObject(Integer.class, packet.x, os);
            PacketParser.writeObject(Integer.class, packet.y, os);
            PacketParser.writeObject(Integer.class, packet.z, os);
            PacketParser.writeObject(Byte.class, packet.pitch, os);
            PacketParser.writeObject(Byte.class, packet.yaw, os);
            PacketParser.writeObject(Integer.class, packet.data, os);

            if (packet.data == 0) return;

            PacketParser.writeObject(Short.class, packet.xVel, os);
            PacketParser.writeObject(Short.class, packet.yVel, os);
            PacketParser.writeObject(Short.class, packet.zVel, os);
        }

    }

}
