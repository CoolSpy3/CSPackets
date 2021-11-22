package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.Slot;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class WindowItemsPacket extends Packet
{

    public byte windowId;
    public Slot[] slots;

    public WindowItemsPacket(byte windowId, Slot[] slots)
    {
        this.windowId = windowId;
        this.slots = slots;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<WindowItemsPacket>
    {

        @Override
        public Class<WindowItemsPacket> getType()
        {
            return WindowItemsPacket.class;
        }

        @Override
        public WindowItemsPacket read(InputStream is) throws IOException
        {
            byte windowId = (byte) is.read();
            short count = PacketParser.readObject(Short.class, is);
            Slot[] slots = new Slot[count];

            for (int i = 0; i < slots.length; i++)
                slots[i] = PacketParser.readObject(Slot.class, is);

            return new WindowItemsPacket(windowId, slots);
        }

        @Override
        public void write(WindowItemsPacket packet, OutputStream os) throws IOException
        {
            os.write(packet.windowId);
            PacketParser.writeObject(Short.class, packet.slots.length, os);

            for (short i = 0; i < packet.slots.length; i++)
                PacketParser.writeObject(Slot.class, packet.slots[i], os);
        }

    }

}
