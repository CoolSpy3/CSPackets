package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.util.Utils;

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
            return new Statistic(Utils.readString(is), Utils.readVarInt(is));
        }

        @Override
        public void encode(Statistic obj, OutputStream os) throws IOException
        {
            Utils.writeString(obj.name, os);
            Utils.writeVarInt(obj.value, os);
        }

        @Override
        public Class<Statistic> getType()
        {
            return Statistic.class;
        }

    }

}
