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
public class TitlePacket extends Packet
{

    public final Action action;
    public final String text;
    public final int fadeIn, stay, fadeOut;


    public TitlePacket(Action action)
    {
        this(action, null, 0, 0, 0);
    }

    public TitlePacket(Action action, String text)
    {
        this(action, text, 0, 0, 0);
    }

    public TitlePacket(int fadeIn, int stay, int fadeOut)
    {
        this(Action.SET_TIMES, null, fadeIn, stay, fadeOut);
    }

    public TitlePacket(Action action, String text, int fadeIn, int stay, int fadeOut)
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

    public static enum Action
    {

        SET_TITLE(0), SET_SUBTITLE(1), SET_TIMES(2), HIDE(3), RESET(4);

        public final int id;

        private Action(int id)
        {
            this.id = id;
        }

        public Integer getId()
        {
            return id;
        }

        public static Action withId(int id)
        {
            for (Action val : values())
                if (val.id == id) return val;

            return null;
        }

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
            Action action = Action.withId(Utils.readVarInt(is));

            switch (action)
            {
                case SET_TITLE:
                case SET_SUBTITLE:
                    return new TitlePacket(action, Utils.readString(is));

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
            Utils.writeVarInt(packet.action.id, os);

            switch (packet.action)
            {
                case SET_TITLE:
                case SET_SUBTITLE:
                    Utils.writeString(packet.text, os);

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
