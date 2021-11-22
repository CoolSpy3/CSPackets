package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.datatypes.FriendlyFireSetting;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.datatypes.NameTagVisibility;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class TeamUpdatePacket extends Packet
{

    public final String teamName;
    public final Mode mode;
    public final String displayName, prefix, suffix;
    public final FriendlyFireSetting friendlyFire;
    public final NameTagVisibility nameTagVisibility;
    public final MCColor color;
    public final String[] players;

    public TeamUpdatePacket(String teamName, Mode mode, String displayName, String prefix,
            String suffix, FriendlyFireSetting friendlyFire, NameTagVisibility nameTagVisibility,
            MCColor color, String[] players)
    {
        this.teamName = teamName;
        this.mode = mode;
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

    public static enum Mode
    {
        CREATE(0), REMOVE(1), UPDATE(2), ADD_PLAYERS(3), REMOVE_PLAYERS(4);

        public final byte id;

        private Mode(int id)
        {
            this((byte) id);
        }

        private Mode(byte id)
        {
            this.id = id;
        }

        public Byte getId()
        {
            return id;
        }

        public static Mode withId(byte id)
        {
            for (Mode val : values())
                if (val.id == id) return val;

            return null;
        }
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
            String teamName = Utils.readString(is);
            Mode mode = Mode.withId((byte) is.read());
            String displayName = null;
            String prefix = null;
            String suffix = null;
            FriendlyFireSetting friendlyFire = null;
            NameTagVisibility nameTagVisibility = null;
            MCColor color = null;
            String[] players = null;

            if (mode == Mode.CREATE || mode == Mode.UPDATE)
            {
                displayName = Utils.readString(is);
                prefix = Utils.readString(is);
                suffix = Utils.readString(is);
                friendlyFire = FriendlyFireSetting.withId((byte) is.read());
                nameTagVisibility = NameTagVisibility.valueOf(Utils.readString(is));
                color = MCColor.withId((byte) is.read());
            }

            if (mode == Mode.CREATE || mode == Mode.ADD_PLAYERS || mode == Mode.REMOVE_PLAYERS)
            {
                int numPlayers = Utils.readVarInt(is);
                players = new String[numPlayers];

                for (int i = 0; i < numPlayers; i++)
                    players[i] = Utils.readString(is);
            }

            return new TeamUpdatePacket(teamName, mode, displayName, prefix, suffix, friendlyFire,
                    nameTagVisibility, color, players);
        }

        @Override
        public void write(TeamUpdatePacket packet, OutputStream os) throws IOException
        {
            Utils.writeString(packet.teamName, os);
            os.write(packet.mode.id);

            if (packet.mode == Mode.CREATE || packet.mode == Mode.UPDATE)
            {
                Utils.writeString(packet.displayName, os);
                Utils.writeString(packet.prefix, os);
                Utils.writeString(packet.suffix, os);
                os.write(packet.friendlyFire.id);
                Utils.writeString(packet.nameTagVisibility.id, os);
                os.write(packet.color.id);
            }

            if (packet.mode == Mode.CREATE || packet.mode == Mode.ADD_PLAYERS
                    || packet.mode == Mode.REMOVE_PLAYERS)
            {
                Utils.writeVarInt(packet.players.length, os);

                for (int i = 0; i < packet.players.length; i++)
                    Utils.writeString(packet.players[i], os);
            }
        }

    }

}
