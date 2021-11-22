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
public class EntityDestroyPacket extends Packet
{

    public final int[] entityIds;

    public EntityDestroyPacket(int[] entityIds)
    {
        this.entityIds = entityIds;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<EntityDestroyPacket>
    {

        @Override
        public Class<EntityDestroyPacket> getType()
        {
            return EntityDestroyPacket.class;
        }

        @Override
        public EntityDestroyPacket read(InputStream is) throws IOException
        {
            int count = Utils.readVarInt(is);

            int[] entityIds = new int[count];

            for (int i = 0; i < count; i++)
            {
                entityIds[i] = Utils.readVarInt(is);
            }

            return new EntityDestroyPacket(entityIds);
        }

        @Override
        public void write(EntityDestroyPacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.entityIds.length, os);

            for (int i = 0; i < packet.entityIds.length; i++)
            {
                Utils.writeVarInt(packet.entityIds[i], os);
            }
        }

    }

}
