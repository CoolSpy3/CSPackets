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
import com.coolspy3.cspackets.datatypes.PlayerListAction;
import com.coolspy3.cspackets.packets.PlayerListPacket.PlayerInfo.Property;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class PlayerListPacket extends Packet
{

    public final PlayerListAction action;
    public final PlayerInfo[] players;

    public PlayerListPacket(PlayerListAction action, PlayerInfo[] players)
    {
        this.action = action;
        this.players = players;
    }

    @Override
    public Object[] getValues()
    {
        return null;
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
                    String name = PacketParser.readObject(String.class, is);
                    String value = PacketParser.readObject(String.class, is);

                    return new Property(name, value,
                            PacketParser.readObject(Boolean.class, is) ? Utils.readString(is)
                                    : null);
                }

                @Override
                public void encode(Property obj, OutputStream os) throws IOException
                {
                    PacketParser.writeObject(String.class, obj.name, os);
                    PacketParser.writeObject(String.class, obj.value, os);
                    PacketParser.writeObject(Boolean.class, obj.signature != null, os);

                    if (obj.signature != null)
                        PacketParser.writeObject(String.class, obj.signature, os);
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
            PlayerListAction action = PacketParser.readObject(PlayerListAction.class, is);

            int numPlayers = Utils.readVarInt(is);
            PlayerInfo[] players = new PlayerInfo[numPlayers];

            for (int i = 0; i < numPlayers; i++)
            {
                UUID uuid = PacketParser.readObject(UUID.class, is);

                switch (action)
                {
                    case ADD_PLAYER:
                        String name = PacketParser.readObject(String.class, is);

                        int numProperties = Utils.readVarInt(is);
                        Property[] properties = new Property[numProperties];

                        for (int j = 0; j < numProperties; j++)
                            properties[i] = PacketParser.readObject(Property.class, is);

                        Gamemode gamemode = PacketParser.readObject(Gamemode.class, is);
                        int ping = PacketParser.readWrappedObject(VarInt.class, is);

                        players[i] = new PlayerInfo(uuid, name, properties, gamemode, ping,
                                PacketParser.readObject(Boolean.class, is)
                                        ? PacketParser.readObject(String.class, is)
                                        : null);

                        break;

                    case UPDATE_GAMEMODE:
                        players[i] =
                                new PlayerInfo(uuid, PacketParser.readObject(Gamemode.class, is));

                        break;

                    case UPDATE_LATENCY:
                        players[i] = new PlayerInfo(uuid,
                                PacketParser.readWrappedObject(VarInt.class, is));

                        break;

                    case UPDATE_DISPLAY_NAME:
                        players[i] = new PlayerInfo(uuid,
                                PacketParser.readObject(Boolean.class, is)
                                        ? PacketParser.readObject(String.class, is)
                                        : null);

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
            PacketParser.writeObject(PlayerListAction.class, packet.action, os);
            Utils.writeVarInt(packet.players.length, os);

            for (int i = 0; i < packet.players.length; i++)
            {
                PlayerInfo player = packet.players[i];

                PacketParser.writeObject(UUID.class, player.uuid, os);

                switch (packet.action)
                {
                    case ADD_PLAYER:
                        PacketParser.writeObject(String.class, player.name, os);

                        Utils.writeVarInt(player.properties.length, os);

                        for (int j = 0; j < player.properties.length; j++)
                            PacketParser.writeObject(PlayerInfo.Property.class,
                                    player.properties[j], os);

                        PacketParser.writeObject(Gamemode.class, player.gamemode, os);
                        PacketParser.writeObject(VarInt.class, player.ping, os);

                        break;

                    case UPDATE_GAMEMODE:
                        PacketParser.writeObject(Gamemode.class, player.gamemode, os);

                        break;

                    case UPDATE_LATENCY:
                        PacketParser.writeObject(VarInt.class, player.ping, os);

                        break;

                    case UPDATE_DISPLAY_NAME:
                        PacketParser.writeObject(Boolean.class, player.displayName == null, os);

                        if (player.displayName != null)
                            PacketParser.writeObject(String.class, player.displayName, os);

                        break;

                    case REMOVE_PLAYER:
                    default:
                        break;
                }
            }
        }

    }

}
