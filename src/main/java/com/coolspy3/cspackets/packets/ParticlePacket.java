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
public class ParticlePacket extends Packet
{

    public final int particleId;
    public final boolean longDistance;
    public final float x, y, z, offsetX, offsetY, offsetZ, particleData;
    public final int count;
    public final int[] data;

    public ParticlePacket(int particleId, boolean longDistance, float x, float y, float z,
            float offsetX, float offsetY, float offsetZ, float particleData, int count, int[] data)
    {
        this.particleId = particleId;
        this.longDistance = longDistance;
        this.x = x;
        this.y = y;
        this.z = z;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.particleData = particleData;
        this.count = count;
        this.data = data;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ParticlePacket>
    {

        @Override
        public Class<ParticlePacket> getType()
        {
            return ParticlePacket.class;
        }

        @Override
        public ParticlePacket read(InputStream is) throws IOException
        {
            int particleId = PacketParser.readObject(Integer.class, is);
            boolean longDistance = PacketParser.readObject(Boolean.class, is);
            float x = PacketParser.readObject(Float.class, is);
            float y = PacketParser.readObject(Float.class, is);
            float z = PacketParser.readObject(Float.class, is);
            float offsetX = PacketParser.readObject(Float.class, is);
            float offsetY = PacketParser.readObject(Float.class, is);
            float offsetZ = PacketParser.readObject(Float.class, is);
            float particleData = PacketParser.readObject(Float.class, is);
            int count = PacketParser.readObject(Integer.class, is);
            int[] data =
                    new int[particleId == 36 ? 2 : particleId == 37 || particleId == 38 ? 1 : 0];

            for (int i = 0; i < data.length; i++)
                data[i] = PacketParser.readWrappedObject(Packet.VarInt.class, is);

            return new ParticlePacket(particleId, longDistance, x, y, z, offsetX, offsetY, offsetZ,
                    particleData, count, data);
        }

        @Override
        public void write(ParticlePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Integer.class, packet.particleId, os);
            PacketParser.writeObject(Boolean.class, packet.longDistance, os);
            PacketParser.writeObject(Float.class, packet.x, os);
            PacketParser.writeObject(Float.class, packet.y, os);
            PacketParser.writeObject(Float.class, packet.z, os);
            PacketParser.writeObject(Float.class, packet.offsetX, os);
            PacketParser.writeObject(Float.class, packet.offsetY, os);
            PacketParser.writeObject(Float.class, packet.offsetZ, os);
            PacketParser.writeObject(Float.class, packet.particleData, os);
            PacketParser.writeObject(Integer.class, packet.count, os);

            switch (packet.particleId)
            {
                case 36:
                    PacketParser.writeObject(Packet.VarInt.class, packet.data[0], os);
                    PacketParser.writeObject(Packet.VarInt.class, packet.data[1], os);

                    break;

                case 37:
                case 38:
                    PacketParser.writeObject(Packet.VarInt.class, packet.data[0], os);

                    break;

                default:
                    break;
            }
        }

    }

}
