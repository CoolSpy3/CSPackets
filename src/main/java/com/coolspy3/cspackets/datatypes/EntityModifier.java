package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.PacketParser;

public class EntityModifier
{

    public final UUID uuid;
    public final double amount;
    public final Operation op;

    public EntityModifier(UUID uuid, double amount, Operation op)
    {
        this.uuid = uuid;
        this.amount = amount;
        this.op = op;
    }

    public static enum Operation
    {
        ADD_SUBTRACT(0), ADD_SUBTRACT_PERCENT(1), MULTIPLY_PERCENT(2);

        public final byte id;

        private Operation(int id)
        {
            this((byte) id);
        }

        private Operation(byte id)
        {
            this.id = id;
        }

        public Byte getId()
        {
            return id;
        }

        public static Operation withId(byte id)
        {
            for (Operation val : values())
                if (val.id == id) return val;

            return null;
        }
    }

    public static class Parser implements ObjectParser<EntityModifier>
    {

        @Override
        public EntityModifier decode(InputStream is) throws IOException
        {
            return new EntityModifier(PacketParser.readObject(UUID.class, is),
                    PacketParser.readObject(Double.class, is),
                    PacketParser.readObject(Operation.class, is));
        }

        @Override
        public void encode(EntityModifier obj, OutputStream os) throws IOException
        {
            PacketParser.writeObject(UUID.class, obj.uuid, os);
            PacketParser.writeObject(Double.class, obj.amount, os);
            PacketParser.writeObject(Operation.class, obj.op, os);
        }

        @Override
        public Class<?> getType()
        {
            return EntityModifier.class;
        }

    }

}
