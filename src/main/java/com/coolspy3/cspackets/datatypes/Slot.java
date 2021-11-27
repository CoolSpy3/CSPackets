package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.PacketParser;

import net.querz.nbt.io.NamedTag;

public class Slot
{

    public final short itemId;
    public final byte itemCount;
    public final short itemDamage;
    public final NamedTag nbt;

    public Slot(short itemId, byte itemCount, short itemDamage, NamedTag nbt)
    {
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemDamage = itemDamage;
        this.nbt = nbt;
    }

    public static class Parser implements ObjectParser<Slot>
    {

        @Override
        public Slot decode(InputStream is) throws IOException
        {
            short itemId = PacketParser.readObject(Short.class, is);

            if (itemId == -1) return new Slot(itemId, (byte) 0, (short) 0, null);

            return new Slot(itemId, PacketParser.readObject(Byte.class, is),
                    PacketParser.readObject(Short.class, is),
                    PacketParser.readObject(NamedTag.class, is));
        }

        @Override
        public void encode(Slot obj, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Short.class, obj.itemId, os);

            if (obj.itemId == -1) return;

            PacketParser.writeObject(Byte.class, obj.itemCount, os);
            PacketParser.writeObject(Short.class, obj.itemDamage, os);
            PacketParser.writeObject(NamedTag.class, obj.nbt, os);
        }

        @Override
        public Class<Slot> getType()
        {
            return Slot.class;
        }

    }

}
