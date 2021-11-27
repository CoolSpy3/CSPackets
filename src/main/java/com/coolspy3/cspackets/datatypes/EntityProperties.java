package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;

public class EntityProperties extends ArrayList<EntityProperty>
{

    public static class Parser implements ObjectParser<EntityProperties>
    {

        @Override
        public EntityProperties decode(InputStream is) throws IOException
        {
            EntityProperties properties = new EntityProperties();
            int count = Utils.readVarInt(is);

            for (int i = 0; i < count; i++)
                properties.add(PacketParser.readObject(EntityProperty.class, is));

            return properties;
        }

        @Override
        public void encode(EntityProperties obj, OutputStream os) throws IOException
        {
            Utils.writeVarInt(obj.size(), os);

            for (EntityProperty property : obj)
                PacketParser.writeObject(EntityProperty.class, property, os);
        }

        @Override
        public Class<EntityProperties> getType()
        {
            return EntityProperties.class;
        }

    }

}
