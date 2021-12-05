package com.coolspy3.cspackets;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import com.coolspy3.csmodloader.util.Utils;

import net.querz.io.Deserializer;
import net.querz.nbt.io.LittleEndianNBTInputStream;
import net.querz.nbt.io.NBTInput;
import net.querz.nbt.io.NBTInputStream;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.Tag;

// Custom implementation of net.querz.nbt.io.NBTDeserializer which properly handles tags which only
// exist as an END_TAG component
public class NBTDeserializer implements Deserializer<NamedTag>
{

    private boolean compressed, littleEndian;

    public NBTDeserializer()
    {
        this(true);
    }

    public NBTDeserializer(boolean compressed)
    {
        this.compressed = compressed;
    }

    public NBTDeserializer(boolean compressed, boolean littleEndian)
    {
        this.compressed = compressed;
        this.littleEndian = littleEndian;
    }

    public NamedTag fromStream(InputStream stream) throws IOException
    {
        NBTInput nbtIn;
        InputStream input;
        if (compressed)
        {
            input = new GZIPInputStream(stream);
        }
        else
        {
            input = stream;
        }

        if (littleEndian)
        {
            nbtIn = new LittleEndianNBTInputStream(input);
        }
        else
        {
            nbtIn = new NBTInputStream(input);
        }

        stream.mark(1);

        byte nextId = Utils.readByte(stream);

        stream.reset();

        if (nextId == 0) return new NamedTag(null, new CompoundTag());

        return nbtIn.readTag(Tag.DEFAULT_MAX_DEPTH);
    }
}
