package com.coolspy3.cspackets.datatypes;

public enum WindowTrigger
{
    LEFT_CLICK(0, 0), RIGHT_CLICK(0, 1), SHIFT_LEFT_CLICK(1, 0), SHIFT_RIGHT_CLICK(1, 1), NUM_0(2,
            0), NUM_1(2, 0), NUM_2(2, 1), NUM_3(2, 2), NUM_4(2, 3), NUM_5(2, 4), NUM_6(2, 5), NUM_7(
                    2, 6), NUM_8(2, 7), NUM_9(2, 8), MIDDLE_CLICK(3, 2), DROP(4, 0), DROP_STACK(4,
                            1), LEFT_CLICK_OUTSIDE_OF_INVENTORY_WHILE_HOLDING_NOTHING(4, 0,
                                    false), RIGHT_CLICK_OUTSIDE_OF_INVENTORY_WHILE_HOLDING_NOTHING(
                                            4, 1, false), START_LEFT_MOUSE_DRAG(5, 0,
                                                    false), START_RIGHT_MOUSE_DRAG(5, 4,
                                                            false), ADD_LEFT_MOUSE_DRAG_SLOT(5,
                                                                    1), ADD_RIGHT_MOUSE_DRAG_SLOT(5,
                                                                            5), END_LEFT_MOUSE_DRAG(
                                                                                    5, 2,
                                                                                    false), END_RIGHT_MOUSE_DRAG(
                                                                                            5, 6,
                                                                                            false), DOUBLE_CLICK(
                                                                                                    6,
                                                                                                    0);

    public final byte button, mode;
    public final boolean hasRealSlot;

    private WindowTrigger(int button, int mode)
    {
        this((byte) button, (byte) mode);
    }

    private WindowTrigger(byte button, byte mode)
    {
        this(button, mode, true);
    }

    private WindowTrigger(int button, int mode, boolean hasRealSlot)
    {
        this((byte) button, (byte) mode, hasRealSlot);
    }

    private WindowTrigger(byte button, byte mode, boolean hasRealSlot)
    {
        this.button = button;
        this.mode = mode;
        this.hasRealSlot = hasRealSlot;
    }

    public static WindowTrigger withId(byte button, byte mode, short slot)
    {
        return withId(button, mode, slot != -999);
    }

    public static WindowTrigger withId(byte button, byte mode, boolean hasRealSlot)
    {
        for (WindowTrigger val : values())
            if (val.button == button && val.mode == mode && val.hasRealSlot == hasRealSlot)
                return val;

        return null;
    }

}
