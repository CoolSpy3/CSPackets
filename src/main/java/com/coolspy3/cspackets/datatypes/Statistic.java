package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;

public class Statistic
{

    public final String name;
    public final int value;

    public Statistic(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public static class Parser implements ObjectParser<Statistic>
    {

        @Override
        public Statistic decode(InputStream is) throws IOException
        {
            return new Statistic(PacketParser.readObject(String.class, is),
                    PacketParser.readWrappedObject(Packet.VarInt.class, is));
        }

        @Override
        public void encode(Statistic obj, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, obj.name, os);
            PacketParser.writeObject(Packet.VarInt.class, obj.name, os);
        }

        @Override
        public Class<Statistic> getType()
        {
            return Statistic.class;
        }

    }

}
