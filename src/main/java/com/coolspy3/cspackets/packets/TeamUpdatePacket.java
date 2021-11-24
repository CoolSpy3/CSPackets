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
import com.coolspy3.cspackets.datatypes.FriendlyFireSetting;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.datatypes.NameTagVisibility;
import com.coolspy3.cspackets.datatypes.TeamAction;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class TeamUpdatePacket extends Packet
{

    public final String teamName;
    public final TeamAction action;
    public final String displayName, prefix, suffix;
    public final FriendlyFireSetting friendlyFire;
    public final NameTagVisibility nameTagVisibility;
    public final MCColor color;
    public final String[] players;

    public TeamUpdatePacket(String teamName, TeamAction action, String displayName, String prefix,
            String suffix, FriendlyFireSetting friendlyFire, NameTagVisibility nameTagVisibility,
            MCColor color, String[] players)
    {
        this.teamName = teamName;
        this.action = action;
        this.displayName = displayName;
        this.prefix = prefix;
        this.suffix = suffix;
        this.friendlyFire = friendlyFire;
        this.nameTagVisibility = nameTagVisibility;
        this.color = color;
        this.players = players;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<TeamUpdatePacket>
    {

        @Override
        public Class<TeamUpdatePacket> getType()
        {
            return TeamUpdatePacket.class;
        }

        @Override
        public TeamUpdatePacket read(InputStream is) throws IOException
        {
            String teamName = PacketParser.readObject(String.class, is);
            TeamAction action = PacketParser.readObject(TeamAction.class, is);
            String displayName = null;
            String prefix = null;
            String suffix = null;
            FriendlyFireSetting friendlyFire = null;
            NameTagVisibility nameTagVisibility = null;
            MCColor color = null;
            String[] players = null;

            if (action == TeamAction.CREATE || action == TeamAction.UPDATE)
            {
                displayName = PacketParser.readObject(String.class, is);
                prefix = PacketParser.readObject(String.class, is);
                suffix = PacketParser.readObject(String.class, is);
                friendlyFire = PacketParser.readObject(FriendlyFireSetting.class, is);
                nameTagVisibility = PacketParser.readObject(NameTagVisibility.class, is);
                color = PacketParser.readObject(MCColor.class, is);
            }

            if (action == TeamAction.CREATE || action == TeamAction.ADD_PLAYERS
                    || action == TeamAction.REMOVE_PLAYERS)
            {
                int numPlayers = Utils.readVarInt(is);
                players = new String[numPlayers];

                for (int i = 0; i < numPlayers; i++)
                    players[i] = PacketParser.readObject(String.class, is);
            }

            return new TeamUpdatePacket(teamName, action, displayName, prefix, suffix, friendlyFire,
                    nameTagVisibility, color, players);
        }

        @Override
        public void write(TeamUpdatePacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(String.class, packet.teamName, os);
            PacketParser.writeObject(TeamAction.class, packet.action, os);

            if (packet.action == TeamAction.CREATE || packet.action == TeamAction.UPDATE)
            {
                PacketParser.writeObject(String.class, packet.displayName, os);
                PacketParser.writeObject(String.class, packet.prefix, os);
                PacketParser.writeObject(String.class, packet.suffix, os);
                PacketParser.writeObject(FriendlyFireSetting.class, packet.friendlyFire, os);
                PacketParser.writeObject(NameTagVisibility.class, packet.nameTagVisibility, os);
                PacketParser.writeObject(MCColor.class, packet.color, os);
            }

            if (packet.action == TeamAction.CREATE || packet.action == TeamAction.ADD_PLAYERS
                    || packet.action == TeamAction.REMOVE_PLAYERS)
            {
                Utils.writeVarInt(packet.players.length, os);

                for (int i = 0; i < packet.players.length; i++)
                    PacketParser.writeObject(String.class, packet.players[i], os);
            }
        }

    }

}
