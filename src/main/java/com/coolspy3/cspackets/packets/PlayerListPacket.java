package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.datatypes.Gamemode;
import com.coolspy3.cspackets.packets.PlayerListPacket.PlayerInfo.Property;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class PlayerListPacket extends Packet
{

    public final Action action;
    public final PlayerInfo[] players;

    public PlayerListPacket(Action action, PlayerInfo[] players)
    {
        this.action = action;
        this.players = players;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static enum Action
    {

        ADD_PLAYER(0), UPDATE_GAMEMODE(1), UPDATE_LATENCY(2), UPDATE_DISPLAY_NAME(3), REMOVE_PLAYER(
                4);

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

    public static class PlayerInfo
    {

        public final UUID uuid;
        public final String name;
        public final Property[] properties;
        public final Gamemode gamemode;
        public final int ping;
        public final String displayName;

        public PlayerInfo(UUID uuid)
        {
            this(uuid, null, null, null, 0, null);
        }

        public PlayerInfo(UUID uuid, Gamemode gamemode)
        {
            this(uuid, null, null, gamemode, 0, null);
        }

        public PlayerInfo(UUID uuid, int ping)
        {
            this(uuid, null, null, null, ping, null);
        }

        public PlayerInfo(UUID uuid, String displayName)
        {
            this(uuid, null, null, null, 0, displayName);
        }

        public PlayerInfo(UUID uuid, String name, Property[] properties, Gamemode gamemode,
                int ping, String displayName)
        {
            this.uuid = uuid;
            this.name = name;
            this.properties = properties;
            this.gamemode = gamemode;
            this.ping = ping;
            this.displayName = displayName;
        }

        public static class Property
        {

            public final String name, value, signature;

            public Property(String name, String value)
            {
                this(name, value, null);
            }

            public Property(String name, String value, String signature)
            {
                this.name = name;
                this.value = value;
                this.signature = signature;
            }

            public static class Parser implements ObjectParser<Property>
            {

                @Override
                public Property decode(InputStream is) throws IOException
                {
                    String name = Utils.readString(is);
                    String value = Utils.readString(is);

                    if (is.read() != 0x01) return new Property(name, value);

                    return new Property(name, value, Utils.readString(is));
                }

                @Override
                public void encode(Property obj, OutputStream os) throws IOException
                {
                    Utils.writeString(obj.name, os);
                    Utils.writeString(obj.value, os);

                    if (obj.signature == null) return;

                    os.write(0x01);
                    Utils.writeString(obj.signature, os);
                }

                @Override
                public Class<Property> getType()
                {
                    return Property.class;
                }

            }

        }

    }

    public static class Serializer implements PacketSerializer<PlayerListPacket>
    {

        @Override
        public Class<PlayerListPacket> getType()
        {
            return PlayerListPacket.class;
        }

        @Override
        public PlayerListPacket read(InputStream is) throws IOException
        {
            Action action = Action.withId(Utils.readVarInt(is));
            int numPlayers = Utils.readVarInt(is);
            PlayerInfo[] players = new PlayerInfo[numPlayers];

            for (int i = 0; i < numPlayers; i++)
            {
                UUID uuid = PacketParser.readObject(UUID.class, is);
                switch (action)
                {
                    case ADD_PLAYER:
                        String name = Utils.readString(is);
                        int numProperties = Utils.readVarInt(is);
                        Property[] properties = new Property[numProperties];

                        for (int j = 0; j < numProperties; j++)
                        {
                            String propName = Utils.readString(is);
                            String propValue = Utils.readString(is);
                            String signature = null;

                            if (is.read() == 0x01) signature = Utils.readString(is);

                            properties[j] = new Property(propName, propValue, signature);
                        }

                        Gamemode gamemode = Gamemode.withId((byte) Utils.readVarInt(is));
                        int ping = Utils.readVarInt(is);
                        String displayName = null;

                        if (is.read() == 0x01) displayName = Utils.readString(is);

                        players[i] =
                                new PlayerInfo(uuid, name, properties, gamemode, ping, displayName);

                        break;

                    case UPDATE_GAMEMODE:
                        players[i] =
                                new PlayerInfo(uuid, Gamemode.withId((byte) Utils.readVarInt(is)));

                        break;

                    case UPDATE_LATENCY:
                        players[i] = new PlayerInfo(uuid, Utils.readVarInt(is));

                        break;

                    case UPDATE_DISPLAY_NAME:
                        players[i] = new PlayerInfo(uuid,
                                is.read() == 0x01 ? Utils.readString(is) : null);

                        break;

                    case REMOVE_PLAYER:
                        players[i] = new PlayerInfo(uuid);

                        break;

                    default:
                        break;
                }
            }

            return new PlayerListPacket(action, players);
        }

        @Override
        public void write(PlayerListPacket packet, OutputStream os) throws IOException
        {
            // TODO Auto-generated method stub

        }

    }

}
