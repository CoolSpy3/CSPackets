package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class MultiblockChangePacket extends Packet
{

    public final int chunkX, chunkZ;
    public final Record[] affectedBlocks;

    public MultiblockChangePacket(int chunkX, int chunkZ, Record[] affectedBlocks)
    {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.affectedBlocks = affectedBlocks;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Record
    {

        public final byte relativeX, relativeZ, y;
        public final int blockId, blockMeta;

        public Record(byte relativeX, byte relativeZ, byte y, int blockId, int blockMeta)
        {
            this.relativeX = relativeX;
            this.relativeZ = relativeZ;
            this.y = y;
            this.blockId = blockId;
            this.blockMeta = blockMeta;
        }

        public static class Parser implements ObjectParser<Record>
        {

            @Override
            public Record decode(InputStream is) throws IOException
            {
                int pos = is.read();
                byte y = (byte) is.read();
                int id = Utils.readVarInt(is);

                return new Record((byte) (pos >> 4), (byte) (pos & 15), y, id >> 4, id & 15);
            }

            @Override
            public void encode(Record obj, OutputStream os) throws IOException
            {
                os.write((obj.relativeX << 4) | obj.relativeZ);
                os.write(obj.y);
                Utils.writeVarInt((obj.blockId << 4) | (obj.blockMeta & 15), os);
            }

            @Override
            public Class<Record> getType()
            {
                return Record.class;
            }

        }

    }

    public static class Serializer implements PacketSerializer<MultiblockChangePacket>
    {

        @Override
        public Class<MultiblockChangePacket> getType()
        {
            return MultiblockChangePacket.class;
        }

        @Override
        public MultiblockChangePacket read(InputStream is) throws IOException
        {
            int x = PacketParser.readObject(Integer.class, is);
            int z = PacketParser.readObject(Integer.class, is);
            int count = Utils.readVarInt(is);
            Record[] records = new Record[count];

            for (int i = 0; i < count; i++)
                records[i] = PacketParser.readObject(Record.class, is);

            return new MultiblockChangePacket(x, z, records);
        }

        @Override
        public void write(MultiblockChangePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Integer.class, packet.chunkX, os);
            PacketParser.writeObject(Integer.class, packet.chunkZ, os);

            for (int i = 0; i < packet.affectedBlocks.length; i++)
                PacketParser.writeObject(Record.class, packet.affectedBlocks[i], os);
        }

    }

}
