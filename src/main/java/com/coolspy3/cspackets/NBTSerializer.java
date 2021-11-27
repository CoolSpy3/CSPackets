package com.coolspy3.cspackets;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import net.querz.io.Serializer;
import net.querz.nbt.io.LittleEndianNBTOutputStream;
import net.querz.nbt.io.NBTOutput;
import net.querz.nbt.io.NBTOutputStream;
import net.querz.nbt.io.NamedTag;
import net.querz.nbt.tag.CompoundTag;
import net.querz.nbt.tag.EndTag;
import net.querz.nbt.tag.Tag;

// Custom implementation of net.querz.nbt.io.NBTSerializer which properly handles tags which only
// exist as an END_TAG component
public class NBTSerializer implements Serializer<NamedTag>
{

    private boolean compressed, littleEndian;

    public NBTSerializer()
    {
        this(true);
    }

    public NBTSerializer(boolean compressed)
    {
        this.compressed = compressed;
    }

    public NBTSerializer(boolean compressed, boolean littleEndian)
    {
        this.compressed = compressed;
        this.littleEndian = littleEndian;
    }

    @Override
    public void toStream(NamedTag object, OutputStream out) throws IOException
    {
        NBTOutput nbtOut;
        OutputStream output;
        if (compressed)
        {
            output = new GZIPOutputStream(out, true);
        }
        else
        {
            output = out;
        }

        if (littleEndian)
        {
            nbtOut = new LittleEndianNBTOutputStream(output);
        }
        else
        {
            nbtOut = new NBTOutputStream(output);
        }

        if (object.getName() == null && object.getTag() instanceof CompoundTag
                && ((CompoundTag) object.getTag()).size() == 0)
            nbtOut.writeTag(EndTag.INSTANCE, Tag.DEFAULT_MAX_DEPTH);
        else
            nbtOut.writeTag(object, Tag.DEFAULT_MAX_DEPTH);
        nbtOut.flush();
    }
}
