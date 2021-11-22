package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class WindowOpenPacket extends Packet
{

    public final byte windowId;
    public final String windowType, windowTitle;
    public final byte numSlots;
    public final int entityId;

    public WindowOpenPacket(byte windowId, String windowTitle, byte numSlots, int entityId)
    {
        this.windowId = windowId;
        this.windowType = "EntityHorse";
        this.windowTitle = windowTitle;
        this.numSlots = numSlots;
        this.entityId = 0;
    }

    public WindowOpenPacket(byte windowId, String windowType, String windowTitle, byte numSlots)
    {
        this.windowId = windowId;
        this.windowType = windowType;
        this.windowTitle = windowTitle;
        this.numSlots = numSlots;
        this.entityId = 0;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<WindowOpenPacket>
    {

        @Override
        public Class<WindowOpenPacket> getType()
        {
            return WindowOpenPacket.class;
        }

        @Override
        public WindowOpenPacket read(InputStream is) throws IOException
        {
            byte windowId = (byte) is.read();
            String windowType = Utils.readString(is);
            String windowTitle = Utils.readString(is);
            byte numSlots = (byte) is.read();

            if (windowType.equals("EntityHorse")) return new WindowOpenPacket(windowId, windowTitle,
                    numSlots, PacketParser.readObject(Integer.class, is));

            return new WindowOpenPacket(windowId, windowType, windowTitle, numSlots);
        }

        @Override
        public void write(WindowOpenPacket packet, OutputStream os) throws IOException
        {
            os.write(packet.windowId);
            Utils.writeString(packet.windowType, os);
            Utils.writeString(packet.windowTitle, os);
            os.write(packet.numSlots);

            if (packet.windowType.equals("EntityHorse"))
                PacketParser.writeObject(Integer.class, packet.entityId, os);
        }

    }

}
