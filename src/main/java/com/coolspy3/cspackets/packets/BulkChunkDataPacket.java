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
import com.coolspy3.cspackets.datatypes.Chunk;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class BulkChunkDataPacket extends Packet
{

    public final boolean hasSkylightData;
    public final Chunk[] chunks;

    public BulkChunkDataPacket(boolean hasSkylightData, Chunk[] chunks)
    {
        this.hasSkylightData = hasSkylightData;
        this.chunks = chunks;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<BulkChunkDataPacket>
    {

        @Override
        public Class<BulkChunkDataPacket> getType()
        {
            return BulkChunkDataPacket.class;
        }

        @Override
        public BulkChunkDataPacket read(InputStream is) throws IOException
        {
            boolean hasSkylightData = PacketParser.readObject(Boolean.class, is);
            int numChunks = PacketParser.readWrappedObject(VarInt.class, is);
            int[][] chunkMetadata = new int[numChunks][3];

            for (int i = 0; i < numChunks; i++)
            {
                chunkMetadata[i][0] = PacketParser.readObject(Integer.class, is);
                chunkMetadata[i][1] = PacketParser.readObject(Integer.class, is);
                chunkMetadata[i][2] = PacketParser.readObject(Integer.class, is);
            }

            Chunk[] chunks = new Chunk[numChunks];

            for (int i = 0; i < numChunks; i++)
                chunks[i] = Chunk.read(chunkMetadata[i][0], chunkMetadata[i][1],
                        (short) chunkMetadata[i][2], hasSkylightData, true, is);

            return new BulkChunkDataPacket(hasSkylightData, chunks);
        }

        @Override
        public void write(BulkChunkDataPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Boolean.class, packet.hasSkylightData, os);
            PacketParser.writeObject(VarInt.class, packet.chunks.length, os);

            byte[][] chunkData = new byte[packet.chunks.length][];

            for (int i = 0; i < packet.chunks.length; i++)
            {
                Chunk chunk = packet.chunks[i];

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                short mask = chunk.write(packet.hasSkylightData, true, os);

                PacketParser.writeObject(Integer.class, chunk.chunkX, os);
                PacketParser.writeObject(Integer.class, chunk.chunkZ, os);
                PacketParser.writeObject(Short.class, mask, os);

                chunkData[i] = baos.toByteArray();
            }

            for (byte[] data : chunkData)
                os.write(data);
        }

    }

}
