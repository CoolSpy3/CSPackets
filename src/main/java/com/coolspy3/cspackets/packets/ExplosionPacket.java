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
public class ExplosionPacket extends Packet
{

    public final float x, y, z, radius;
    public final byte[][] affectedBlocks;
    public final float playerVelX, playerVelY, playerVelZ;

    public ExplosionPacket(float x, float y, float z, float radius, byte[][] affectedBlocks,
            float playerVelX, float playerVelY, float playerVelZ)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
        this.affectedBlocks = affectedBlocks;
        this.playerVelX = playerVelX;
        this.playerVelY = playerVelY;
        this.playerVelZ = playerVelZ;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ExplosionPacket>
    {

        @Override
        public Class<ExplosionPacket> getType()
        {
            return ExplosionPacket.class;
        }

        @Override
        public ExplosionPacket read(InputStream is) throws IOException
        {
            float x = PacketParser.readObject(Float.class, is);
            float y = PacketParser.readObject(Float.class, is);
            float z = PacketParser.readObject(Float.class, is);
            float radius = PacketParser.readObject(Float.class, is);
            int count = PacketParser.readObject(Integer.class, is);
            byte[][] affectedBlocks = new byte[count][3];

            for (int i = 0; i < count; i++)
                affectedBlocks[i] =
                        new byte[] {(byte) is.read(), (byte) is.read(), (byte) is.read()};

            float px = PacketParser.readObject(Float.class, is);
            float py = PacketParser.readObject(Float.class, is);
            float pz = PacketParser.readObject(Float.class, is);

            return new ExplosionPacket(x, y, z, radius, affectedBlocks, px, py, pz);
        }

        @Override
        public void write(ExplosionPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Float.class, packet.x, os);
            PacketParser.writeObject(Float.class, packet.y, os);
            PacketParser.writeObject(Float.class, packet.z, os);
            PacketParser.writeObject(Float.class, packet.radius, os);
            PacketParser.writeObject(Integer.class, packet.affectedBlocks.length, os);

            for (int i = 0; i < packet.affectedBlocks.length; i++)
            {
                os.write(packet.affectedBlocks[i][0]);
                os.write(packet.affectedBlocks[i][1]);
                os.write(packet.affectedBlocks[i][2]);
            }

            PacketParser.writeObject(Float.class, packet.playerVelX, os);
            PacketParser.writeObject(Float.class, packet.playerVelY, os);
            PacketParser.writeObject(Float.class, packet.playerVelZ, os);
        }

    }

}
