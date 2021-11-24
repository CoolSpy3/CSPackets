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

@PacketSpec(types = {}, direction = PacketDirection.SERVERBOUND)
public class WindowActionPacket extends Packet
{

    public final byte windowId;
    public final short slot;
    public final Trigger trigger;
    public final short actionId;
    public final Slot clickedItem;

    public WindowActionPacket(byte windowId, short slot, Trigger trigger, short actionId,
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

    public static enum Trigger
    {
        LEFT_CLICK(0, 0), RIGHT_CLICK(0, 1), SHIFT_LEFT_CLICK(1, 0), SHIFT_RIGHT_CLICK(1, 1), NUM_0(
                2, 0), NUM_1(2, 0), NUM_2(2, 1), NUM_3(2, 2), NUM_4(2, 3), NUM_5(2, 4), NUM_6(2,
                        5), NUM_7(2, 6), NUM_8(2, 7), NUM_9(2, 8), MIDDLE_CLICK(3, 2), DROP(4,
                                0), DROP_STACK(4,
                                        1), LEFT_CLICK_OUTSIDE_OF_INVENTORY_WHILE_HOLDING_NOTHING(4,
                                                0,
                                                false), RIGHT_CLICK_OUTSIDE_OF_INVENTORY_WHILE_HOLDING_NOTHING(
                                                        4, 1, false), START_LEFT_MOUSE_DRAG(5, 0,
                                                                false), START_RIGHT_MOUSE_DRAG(5, 4,
                                                                        false), ADD_LEFT_MOUSE_DRAG_SLOT(
                                                                                5,
                                                                                1), ADD_RIGHT_MOUSE_DRAG_SLOT(
                                                                                        5,
                                                                                        5), END_LEFT_MOUSE_DRAG(
                                                                                                5,
                                                                                                2,
                                                                                                false), END_RIGHT_MOUSE_DRAG(
                                                                                                        5,
                                                                                                        6,
                                                                                                        false), DOUBLE_CLICK(
                                                                                                                6,
                                                                                                                0);

        public final byte button, mode;
        public final boolean hasRealSlot;

        private Trigger(int button, int mode)
        {
            this((byte) button, (byte) mode);
        }

        private Trigger(byte button, byte mode)
        {
            this(button, mode, true);
        }

        private Trigger(int button, int mode, boolean hasRealSlot)
        {
            this((byte) button, (byte) mode, hasRealSlot);
        }

        private Trigger(byte button, byte mode, boolean hasRealSlot)
        {
            this.button = button;
            this.mode = mode;
            this.hasRealSlot = hasRealSlot;
        }

        public static Trigger withId(byte button, byte mode, short slot)
        {
            return withId(button, mode, slot != -999);
        }

        public static Trigger withId(byte button, byte mode, boolean hasRealSlot)
        {
            for (Trigger val : values())
                if (val.button == button && val.mode == mode && val.hasRealSlot == hasRealSlot)
                    return val;

            return null;
        }

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
            byte windowId = (byte) is.read();
            short slot = PacketParser.readObject(Short.class, is);
            byte button = (byte) is.read();
            short actionId = PacketParser.readObject(Short.class, is);
            byte mode = (byte) is.read();
            Slot clickedItem = PacketParser.readObject(Slot.class, is);

            return new WindowActionPacket(windowId, slot, Trigger.withId(button, mode, slot),
                    actionId, clickedItem);
        }

        @Override
        public void write(WindowActionPacket packet, OutputStream os) throws IOException
        {
            os.write(packet.windowId);
            PacketParser.writeObject(Short.class, packet.trigger.hasRealSlot ? packet.slot : -999,
                    os);
            os.write(packet.trigger.button);
            PacketParser.writeObject(Short.class, packet.actionId, os);
            os.write(packet.trigger.mode);
            PacketParser.writeObject(Slot.class, packet.slot, os);
        }

    }

}
