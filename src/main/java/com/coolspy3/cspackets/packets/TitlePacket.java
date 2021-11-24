package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.TitleAction;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class TitlePacket extends Packet
{

    public final TitleAction action;
    public final String text;
    public final int fadeIn, stay, fadeOut;


    public TitlePacket(TitleAction action)
    {
        this(action, null, 0, 0, 0);
    }

    public TitlePacket(TitleAction action, String text)
    {
        this(action, text, 0, 0, 0);
    }

    public TitlePacket(int fadeIn, int stay, int fadeOut)
    {
        this(TitleAction.SET_TIMES, null, fadeIn, stay, fadeOut);
    }

    public TitlePacket(TitleAction action, String text, int fadeIn, int stay, int fadeOut)
    {
        this.action = action;
        this.text = text;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<TitlePacket>
    {

        @Override
        public Class<TitlePacket> getType()
        {
            return TitlePacket.class;
        }

        @Override
        public TitlePacket read(InputStream is) throws IOException
        {
            TitleAction action = PacketParser.readObject(TitleAction.class, is);

            switch (action)
            {
                case SET_TITLE:
                case SET_SUBTITLE:
                    return new TitlePacket(action, PacketParser.readObject(String.class, is));

                case SET_TIMES:
                    return new TitlePacket(PacketParser.readObject(Integer.class, is),
                            PacketParser.readObject(Integer.class, is),
                            PacketParser.readObject(Integer.class, is));

                case HIDE:
                case RESET:
                    return new TitlePacket(action);

                default:
                    return null;
            }
        }

        @Override
        public void write(TitlePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(TitleAction.class, packet.action, os);

            switch (packet.action)
            {
                case SET_TITLE:
                case SET_SUBTITLE:
                    PacketParser.writeObject(String.class, packet.text, os);

                    break;

                case SET_TIMES:
                    PacketParser.writeObject(Integer.class, packet.fadeIn, os);
                    PacketParser.writeObject(Integer.class, packet.stay, os);
                    PacketParser.writeObject(Integer.class, packet.fadeOut, os);

                    break;

                case HIDE:
                case RESET:
                default:
                    break;
            }
        }

    }

}
