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
import com.coolspy3.cspackets.datatypes.Position;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class BlockChangePacket extends Packet
{

    public final Position position;
    public final int blockId, blockMeta;

    public BlockChangePacket(Position position, int blockId, int blockMeta)
    {
        this.position = position;
        this.blockId = blockId;
        this.blockMeta = blockMeta;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<BlockChangePacket>
    {

        @Override
        public Class<BlockChangePacket> getType()
        {
            return BlockChangePacket.class;
        }

        @Override
        public BlockChangePacket read(InputStream is) throws IOException
        {
            Position position = PacketParser.readObject(Position.class, is);
            int id = Utils.readVarInt(is);

            return new BlockChangePacket(position, id >> 4, id & 15);
        }

        @Override
        public void write(BlockChangePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Position.class, packet.position, os);
            Utils.writeVarInt((packet.blockId << 4) | (packet.blockMeta & 15), os);
        }

    }

}
