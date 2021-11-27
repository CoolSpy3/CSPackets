package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;

public class EntityProperty
{

    public final String key;
    public final double value;
    public final EntityModifier[] modifiers;

    public EntityProperty(String key, double value, EntityModifier[] modifiers)
    {
        this.key = key;
        this.value = value;
        this.modifiers = modifiers;
    }

    public static class Parser implements ObjectParser<EntityProperty>
    {

        @Override
        public EntityProperty decode(InputStream is) throws IOException
        {
            String key = PacketParser.readObject(String.class, is);
            double value = PacketParser.readObject(Double.class, is);
            int count = Utils.readVarInt(is);
            EntityModifier[] modifiers = new EntityModifier[count];

            for (int i = 0; i < count; i++)
                modifiers[i] = PacketParser.readObject(EntityModifier.class, is);

            return new EntityProperty(key, value, modifiers);
        }

        @Override
        public void encode(EntityProperty obj, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, obj.key, os);
            PacketParser.writeObject(Double.class, obj.value, os);
            Utils.writeVarInt(obj.modifiers.length, os);

            for (int i = 0; i < obj.modifiers.length; i++)
                PacketParser.writeObject(EntityModifier.class, obj.modifiers[i], os);
        }

        @Override
        public Class<EntityProperties> getType()
        {
            return EntityProperties.class;
        }

    }

}
