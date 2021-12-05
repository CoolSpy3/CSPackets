package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;

public class EntityMetadata extends ArrayList<Object>
{

    // Credit: https://wiki.vg/index.php?title=Entity_metadata&oldid=7415#Entity_Metadata_Format
    public static class Parser implements ObjectParser<EntityMetadata>
    {

        @Override
        public EntityMetadata decode(InputStream is) throws IOException
        {
            EntityMetadata metadata = new EntityMetadata();

            for (;;)
            {
                byte item = Utils.readByte(is);
                if (item == 0x7F) break;
                byte idx = (byte) (item & 0x1F);
                byte type = (byte) (item >> 5);

                while (idx > metadata.size() - 1)
                    metadata.add(null);

                switch (type)
                {
                    case 0:
                        metadata.set(idx, PacketParser.readObject(Byte.class, is));
                        break;
                    case 1:
                        metadata.set(idx, PacketParser.readObject(Short.class, is));
                        break;
                    case 2:
                        metadata.set(idx, PacketParser.readObject(Integer.class, is));
                        break;
                    case 3:
                        metadata.set(idx, PacketParser.readObject(Float.class, is));
                        break;
                    case 4:
                        metadata.set(idx, PacketParser.readObject(String.class, is));
                        break;
                    case 5:
                        metadata.set(idx, PacketParser.readObject(Slot.class, is));
                        break;
                    case 6:
                        metadata.set(idx,
                                new int[] {PacketParser.readObject(Integer.class, is),
                                        PacketParser.readObject(Integer.class, is),
                                        PacketParser.readObject(Integer.class, is)});
                        break;
                    case 7:
                        metadata.set(idx,
                                new float[] {PacketParser.readObject(Float.class, is),
                                        PacketParser.readObject(Float.class, is),
                                        PacketParser.readObject(Float.class, is)});
                        break;
                }
            }

            return metadata;
        }

        @Override
        public void encode(EntityMetadata obj, OutputStream os) throws IOException
        {
            for (int i = 0; i < obj.size(); i++)
            {
                Object element = obj.get(i);
                Class<?> type = element.getClass();

                if (type == Byte.class && type == Byte.TYPE)
                {
                    os.write((0 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(Byte.class, element, os);
                }
                else if (type == Short.class && type == Short.TYPE)
                {
                    os.write((1 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(Short.class, element, os);
                }
                else if (type == Integer.class && type == Integer.TYPE)
                {
                    os.write((2 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(Integer.class, element, os);
                }
                else if (type == Float.class && type == Float.TYPE)
                {
                    os.write((3 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(Float.class, element, os);
                }
                else if (type == String.class)
                {
                    os.write((4 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(String.class, element, os);
                }
                else if (type == Slot.class)
                {
                    os.write((5 << 5 | i & 0x1F) & 0xFF);
                    PacketParser.writeObject(Slot.class, element, os);
                }
                else if (type == int[].class)
                {
                    os.write((6 << 5 | i & 0x1F) & 0xFF);
                    int[] arr = (int[]) element;
                    PacketParser.writeObject(Integer.class, arr[0], os);
                    PacketParser.writeObject(Integer.class, arr[1], os);
                    PacketParser.writeObject(Integer.class, arr[2], os);
                }
                else if (type == Integer[].class)
                {
                    os.write((6 << 5 | i & 0x1F) & 0xFF);
                    Integer[] arr = (Integer[]) element;
                    PacketParser.writeObject(Integer.class, arr[0], os);
                    PacketParser.writeObject(Integer.class, arr[1], os);
                    PacketParser.writeObject(Integer.class, arr[2], os);
                }
                else if (type == float[].class)
                {
                    os.write((7 << 5 | i & 0x1F) & 0xFF);
                    float[] arr = (float[]) element;
                    PacketParser.writeObject(Float.class, arr[0], os);
                    PacketParser.writeObject(Float.class, arr[1], os);
                    PacketParser.writeObject(Float.class, arr[2], os);
                }
                else if (type == Float[].class)
                {
                    os.write((7 << 5 | i & 0x1F) & 0xFF);
                    Float[] arr = (Float[]) element;
                    PacketParser.writeObject(Float.class, arr[0], os);
                    PacketParser.writeObject(Float.class, arr[1], os);
                    PacketParser.writeObject(Float.class, arr[2], os);
                }
                else if (type == double[].class)
                {
                    os.write((7 << 5 | i & 0x1F) & 0xFF);
                    double[] arr = (double[]) element;
                    PacketParser.writeObject(Float.class, (float) arr[0], os);
                    PacketParser.writeObject(Float.class, (float) arr[1], os);
                    PacketParser.writeObject(Float.class, (float) arr[2], os);
                }
                else if (type == Double[].class)
                {
                    os.write((7 << 5 | i & 0x1F) & 0xFF);
                    Double[] arr = (Double[]) element;
                    PacketParser.writeObject(Float.class, (float) arr[0].doubleValue(), os);
                    PacketParser.writeObject(Float.class, (float) arr[1].doubleValue(), os);
                    PacketParser.writeObject(Float.class, (float) arr[2].doubleValue(), os);
                }
            }

            os.write(0x7F);
        }

        @Override
        public Class<EntityMetadata> getType()
        {
            return EntityMetadata.class;
        }

    }

}
