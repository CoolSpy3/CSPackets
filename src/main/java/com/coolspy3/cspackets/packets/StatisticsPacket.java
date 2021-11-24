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
import com.coolspy3.cspackets.datatypes.Statistic;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class StatisticsPacket extends Packet
{

    public final Statistic[] statistics;

    public StatisticsPacket(Statistic[] statistics)
    {
        this.statistics = statistics;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<StatisticsPacket>
    {

        @Override
        public Class<StatisticsPacket> getType()
        {
            return StatisticsPacket.class;
        }

        @Override
        public StatisticsPacket read(InputStream is) throws IOException
        {
            int count = Utils.readVarInt(is);
            Statistic[] statistics = new Statistic[count];

            for (int i = 0; i < count; i++)
                statistics[i] = PacketParser.readObject(Statistic.class, is);

            return new StatisticsPacket(statistics);
        }

        @Override
        public void write(StatisticsPacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.statistics.length, os);

            for (int i = 0; i < packet.statistics.length; i++)
                PacketParser.writeObject(Statistic.class, packet.statistics[i], os);
        }

    }

}
