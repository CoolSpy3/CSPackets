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

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class MapUpdatePacket extends Packet
{

    public final int mapId;
    public final byte scale;
    public final byte[][] icons;
    public final byte updatedColumns, updatedRows, offsetX, offsetZ;
    public final byte[] data;

    public MapUpdatePacket(int mapId, byte scale, byte[][] icons)
    {
        this(mapId, scale, icons, (byte) 0, (byte) 0, (byte) 0, (byte) 0, new byte[0]);
    }

    public MapUpdatePacket(int mapId, byte scale, byte[][] icons, byte updatedColumns,
            byte updatedRows, byte offsetX, byte offsetZ, byte[] data)
    {
        this.mapId = mapId;
        this.scale = scale;
        this.icons = icons;
        this.updatedColumns = updatedColumns;
        this.updatedRows = updatedRows;
        this.offsetX = offsetX;
        this.offsetZ = offsetZ;
        this.data = data;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<MapUpdatePacket>
    {

        @Override
        public Class<MapUpdatePacket> getType()
        {
            return MapUpdatePacket.class;
        }

        @Override
        public MapUpdatePacket read(InputStream is) throws IOException
        {
            int mapId = PacketParser.readWrappedObject(VarInt.class, is);
            byte scale = PacketParser.readObject(Byte.class, is);
            int count = PacketParser.readWrappedObject(VarInt.class, is);
            byte[][] icons = new byte[count][3];

            for (int i = 0; i < count; i++)
            {
                icons[i][0] = (byte) is.read();
                icons[i][1] = (byte) is.read();
                icons[i][2] = (byte) is.read();
            }

            byte updatedColumns = PacketParser.readObject(Byte.class, is);

            if (updatedColumns == 0) return new MapUpdatePacket(mapId, scale, icons);

            byte updatedRows = PacketParser.readObject(Byte.class, is);
            byte offsetX = PacketParser.readObject(Byte.class, is);
            byte offsetZ = PacketParser.readObject(Byte.class, is);
            count = PacketParser.readWrappedObject(VarInt.class, is);
            byte[] data = Utils.readNBytes(is, count);

            return new MapUpdatePacket(mapId, scale, icons, updatedColumns, updatedRows, offsetX,
                    offsetZ, data);
        }

        @Override
        public void write(MapUpdatePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(VarInt.class, packet.mapId, os);
            PacketParser.writeObject(Byte.class, packet.scale, os);
            PacketParser.writeObject(VarInt.class, packet.icons.length, os);

            for (int i = 0; i < packet.icons.length; i++)
            {
                os.write(packet.icons[i][0]);
                os.write(packet.icons[i][1]);
                os.write(packet.icons[i][2]);
            }

            PacketParser.writeObject(Byte.class, packet.updatedColumns, os);

            if (packet.updatedColumns == 0) return;

            PacketParser.writeObject(Byte.class, packet.updatedRows, os);
            PacketParser.writeObject(Byte.class, packet.offsetX, os);
            PacketParser.writeObject(Byte.class, packet.offsetZ, os);
            PacketParser.writeObject(VarInt.class, packet.data.length, os);

            os.write(packet.data);
        }

    }

}
