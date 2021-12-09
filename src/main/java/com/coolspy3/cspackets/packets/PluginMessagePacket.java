package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.BiFunction;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;

/**
 * A special packet type for handling plugin messages. Note that this class is not a packet, but
 * contains subclasses for each PacketDirection. WARNING: The plugin message does not contain a
 * length specifier, so the default serializer implementation will attempt to read the entire stream
 * while parsing (this requires that the stream provide a valid {@link InputStream#available()}
 * method). Thus, it should be fed a stream which contains pre-buffered data for a single packet
 * (such as a ByteArrayInputStream).
 */
public final class PluginMessagePacket
{

    @PacketSpec(direction = PacketDirection.CLIENTBOUND, types = {})
    public static class Clientbound extends Packet
    {

        public final String channel;
        public final byte[] data;

        public Clientbound(String channel, Byte[] data)
        {
            this(channel, Utils.unbox(data));
        }

        public Clientbound(String channel, byte[] data)
        {
            this.channel = channel;
            this.data = data;
        }

        @Override
        public Object[] getValues()
        {
            return new Object[] {channel, data};
        }

        public static final PacketSerializer<Clientbound> SERIALIZER =
                new Serializer<>(Clientbound::new, Clientbound.class);

    }

    @PacketSpec(direction = PacketDirection.SERVERBOUND, types = {})
    public static class Serverbound extends Packet
    {

        public final String channel;
        public final byte[] data;

        public Serverbound(String channel, Byte[] data)
        {
            this(channel, Utils.unbox(data));
        }

        public Serverbound(String channel, byte[] data)
        {
            this.channel = channel;
            this.data = data;
        }

        @Override
        public Object[] getValues()
        {
            return new Object[] {channel, data};
        }

        public static final PacketSerializer<Serverbound> SERIALIZER =
                new Serializer<>(Serverbound::new, Serverbound.class);

    }

    public static class Serializer<T extends Packet> implements PacketSerializer<T>
    {

        private final BiFunction<String, Byte[], T> constructor;
        private final Class<T> type;

        public Serializer(BiFunction<String, Byte[], T> constructor, Class<T> type)
        {
            this.constructor = constructor;
            this.type = type;
        }

        @Override
        public Class<T> getType()
        {
            return type;
        }

        @Override
        public T read(InputStream is) throws IOException
        {
            return constructor.apply(Utils.readString(is), Utils.box(readFully(is)));
        }

        @Override
        public void write(T packet, OutputStream os) throws IOException
        {
            Object[] values = packet.getValues();
            Utils.writeString((String) values[0], os);
            os.write((byte[]) values[1]);
        }

        public static final byte[] readFully(InputStream is) throws IOException
        {
            byte[] data = new byte[is.available()];
            int pos = 0;

            while (pos < data.length)
                pos += is.read(data, pos, data.length - pos);

            return data;
        }

    }

    private PluginMessagePacket()
    {}

}
