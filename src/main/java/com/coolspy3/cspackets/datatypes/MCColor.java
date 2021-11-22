package com.coolspy3.cspackets.datatypes;

public enum MCColor
{

    BLACK('0', 0x0), DARK_BLUE('1', 0x1), DARK_GREEN('2', 0x2), DARK_AQUA('3', 0x3), DARK_RED('4',
            0x4), DARK_PURPLE('5', 0x5), GOLD('6', 0x6), GRAY('7', 0x7), DARK_GRAY('8',
                    0x8), BLUE('9', 0x9), GREEN('a', 0xA), AQUA('b', 0xB), RED('c',
                            0xC), LIGHT_PURPLE('d', 0xD), YELLOW('e', 0xE), WHITE('f',
                                    0xF), OBFUSCATED('k'), BOLD('l'), STRIKETHROUGH(
                                            'm'), UNDERLINE('n'), ITALIC('o'), RESET('r');

    private final String toString;
    public final byte id;

    private MCColor(char c)
    {
        this(c, 0);
    }

    private MCColor(char c, int id)
    {
        this(c, (byte) id);
    }

    private MCColor(char c, byte id)
    {
        this.toString = "\u00a7" + c;
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static MCColor withId(byte id)
    {
        for (MCColor val : values())
            if (val.id == id) return val;

        return null;
    }

    @Override
    public String toString()
    {
        return toString;
    }

}
