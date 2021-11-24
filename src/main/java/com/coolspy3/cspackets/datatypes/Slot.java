package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;

import net.querz.nbt.io.NamedTag;

public class Slot
{

    public final boolean present;
    public final int itemId;
    public final byte itemCount;
    public final NamedTag nbt;

    public Slot(boolean present, int itemId, byte itemCount, NamedTag nbt)
    {
        this.present = present;
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.nbt = nbt;
    }

    public static class Parser implements ObjectParser<Slot>
    {

        @Override
        public Slot decode(InputStream is) throws IOException
        {
            boolean present = PacketParser.readObject(Boolean.class, is);

            if (present)
            {
                return new Slot(true, Utils.readVarInt(is), PacketParser.readObject(Byte.class, is),
                        PacketParser.readObject(NamedTag.class, is));
            }

            return new Slot(false, 0, (byte) 0, null);
        }

        @Override
        public void encode(Slot obj, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Boolean.class, obj.present, os);

            if (obj.present)
            {
                PacketParser.writeObject(Packet.VarInt.class, obj.itemId, os);
                PacketParser.writeObject(Byte.class, obj.itemCount, os);
                PacketParser.writeObject(NamedTag.class, obj.nbt, os);
            }
        }

        @Override
        public Class<Slot> getType()
        {
            return Slot.class;
        }

    }

}
