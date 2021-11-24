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
import com.coolspy3.cspackets.datatypes.WindowTrigger;

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class WindowActionPacket extends Packet
{

    public final byte windowId;
    public final short slot;
    public final WindowTrigger trigger;
    public final short actionId;
    public final Slot clickedItem;

    public WindowActionPacket(byte windowId, short slot, WindowTrigger trigger, short actionId,
            Slot clickedItem)
    {
        this.windowId = windowId;
        this.slot = slot;
        this.trigger = trigger;
        this.actionId = actionId;
        this.clickedItem = clickedItem;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }


    public static class Serializer implements PacketSerializer<WindowActionPacket>
    {

        @Override
        public Class<WindowActionPacket> getType()
        {
            return WindowActionPacket.class;
        }

        @Override
        public WindowActionPacket read(InputStream is) throws IOException
        {
            byte windowId = PacketParser.readObject(Byte.class, is);
            short slot = PacketParser.readObject(Short.class, is);
            byte button = PacketParser.readObject(Byte.class, is);
            short actionId = PacketParser.readObject(Short.class, is);
            byte mode = PacketParser.readObject(Byte.class, is);
            Slot clickedItem = PacketParser.readObject(Slot.class, is);

            return new WindowActionPacket(windowId, slot, WindowTrigger.withId(button, mode, slot),
                    actionId, clickedItem);
        }

        @Override
        public void write(WindowActionPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(Byte.class, packet.windowId, os);
            PacketParser.writeObject(Short.class, packet.trigger.hasRealSlot ? packet.slot : -999,
                    os);
            PacketParser.writeObject(Byte.class, packet.trigger.button, os);
            PacketParser.writeObject(Short.class, packet.actionId, os);
            PacketParser.writeObject(Byte.class, packet.trigger.mode, os);
            PacketParser.writeObject(Slot.class, packet.slot, os);
        }

    }

}
