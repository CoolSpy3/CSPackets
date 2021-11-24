package com.coolspy3.cspackets.packets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.ChunkUtils;
import com.coolspy3.cspackets.datatypes.Chunk;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class ChunkDataPacket extends Packet
{

    public final Chunk chunk;
    public final boolean continuous;
    public final boolean hasSkylightData;

    public ChunkDataPacket(Chunk chunk, boolean continuous, boolean hasSkylightData)
    {
        this.chunk = chunk;
        this.continuous = continuous;
        this.hasSkylightData = hasSkylightData;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<ChunkDataPacket>
    {

        @Override
        public Class<ChunkDataPacket> getType()
        {
            return ChunkDataPacket.class;
        }

        @Override
        public ChunkDataPacket read(InputStream is) throws IOException
        {
            int chunkX = PacketParser.readObject(Integer.class, is);
            int chunkZ = PacketParser.readObject(Integer.class, is);
            boolean continuous = PacketParser.readObject(Boolean.class, is);
            short mask = PacketParser.readObject(Short.class, is);
            int dataLength = PacketParser.readWrappedObject(VarInt.class, is);

            boolean hasSkylightData =
                    dataLength > Chunk.MIN_BYTES_IN_SUBCHUNK * ChunkUtils.numSetBits(mask);

            return new ChunkDataPacket(Chunk.read(chunkX, chunkZ, mask, hasSkylightData, false, is),
                    continuous, hasSkylightData);
        }

        @Override
        public void write(ChunkDataPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Integer.class, packet.chunk.chunkX, os);
            PacketParser.writeObject(Integer.class, packet.chunk.chunkZ, os);
            PacketParser.writeObject(Boolean.class, packet.continuous, os);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            short mask = packet.chunk.write(packet.hasSkylightData, false, baos);

            PacketParser.writeObject(Short.class, mask, os);

            byte[] chunkData = baos.toByteArray();
            PacketParser.writeObject(VarInt.class, chunkData.length, os);
            os.write(chunkData);
        }

    }

}
