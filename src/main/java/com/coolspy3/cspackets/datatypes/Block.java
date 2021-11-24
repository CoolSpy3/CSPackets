package com.coolspy3.cspackets.datatypes;

import com.coolspy3.csmodloader.network.packet.WrapperType;

public class Block
{

    public final int id, meta;

    public Block(int id, int meta)
    {
        this.id = id;
        this.meta = meta;
    }

    public static Block fromShort(short data)
    {
        return new Block(data >> 4, data & 15);
    }

    public Short toShort()
    {
        return (short) ((id << 4) | (meta & 15));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Block) return ((Block) obj).id == id && ((Block) obj).meta == meta;

        return false;
    }

    public static class AsInt extends WrapperType<Block>
    {}

    public static class AsVarInt extends WrapperType<Block>
    {}

    public static class AsShort extends WrapperType<Block>
    {}

}
