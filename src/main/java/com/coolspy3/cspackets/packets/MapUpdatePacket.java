package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
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
            int mapId = Utils.readVarInt(is);
            byte scale = (byte) is.read();
            int count = Utils.readVarInt(is);
            byte[][] icons = new byte[count][3];

            for (int i = 0; i < count; i++)
            {
                icons[i][0] = (byte) is.read();
                icons[i][1] = (byte) is.read();
                icons[i][2] = (byte) is.read();
            }

            byte updatedColumns = (byte) is.read();

            if (updatedColumns == 0) return new MapUpdatePacket(mapId, scale, icons);

            byte updatedRows = (byte) is.read();
            byte offsetX = (byte) is.read();
            byte offsetZ = (byte) is.read();
            count = Utils.readVarInt(is);
            byte[] data = new byte[count];

            return new MapUpdatePacket(mapId, scale, icons, updatedColumns, updatedRows, offsetX,
                    offsetZ, data);
        }

        @Override
        public void write(MapUpdatePacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.mapId, os);
            os.write(packet.scale);
            Utils.writeVarInt(packet.icons.length, os);

            for (int i = 0; i < packet.icons.length; i++)
            {
                os.write(packet.icons[i][0]);
                os.write(packet.icons[i][1]);
                os.write(packet.icons[i][2]);
            }

            os.write(packet.updatedColumns);

            if (packet.updatedColumns == 0) return;

            os.write(packet.updatedRows);
            os.write(packet.offsetX);
            os.write(packet.offsetZ);
            Utils.writeVarInt(packet.data.length, os);

            for (int i = 0; i < packet.data.length; i++)
                os.write(packet.data[i]);
        }

    }

}
