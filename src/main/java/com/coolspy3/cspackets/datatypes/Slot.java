package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
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
            boolean present = is.read() == 0x01;

            if (present)
            {
                return new Slot(true, Utils.readVarInt(is), (byte) is.read(),
                        PacketParser.readObject(NamedTag.class, is));
            }

            return new Slot(false, 0, (byte) 0, null);
        }

        @Override
        public void encode(Slot obj, OutputStream os) throws IOException
        {
            if (obj.present)
            {
                os.write(0x01);
                Utils.writeVarInt(obj.itemId, os);
                os.write(obj.itemCount);
                PacketParser.writeObject(NamedTag.class, obj.nbt, os);

                return;
            }

            os.write(0x00);
        }

        @Override
        public Class<Slot> getType()
        {
            return Slot.class;
        }

    }

}
