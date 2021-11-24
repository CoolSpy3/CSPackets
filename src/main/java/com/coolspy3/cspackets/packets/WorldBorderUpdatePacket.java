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
import com.coolspy3.cspackets.datatypes.WorldBorderAction;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class WorldBorderUpdatePacket extends Packet
{

    public final WorldBorderAction action;
    public final double x, z, oldRadius, newRadius;
    public final long speed;
    public final int portalTeleportBoundary, warningTime, warningBlocks;

    public WorldBorderUpdatePacket(double radius)
    {
        this(WorldBorderAction.SET_SIZE, 0, 0, 0, radius, 0, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double oldRadius, double newRadius, long speed)
    {
        this(WorldBorderAction.LERP_SIZE, 0, 0, oldRadius, newRadius, speed, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double x, double z)
    {
        this(WorldBorderAction.LERP_SIZE, x, z, 0, 0, 0, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double x, double z, double oldRadius, double newRadius,
            long speed, int portalTeleportBoundary, int warningTime, int warningBlocks)
    {
        this(WorldBorderAction.INITIALIZE, x, z, oldRadius, newRadius, speed,
                portalTeleportBoundary, warningTime, warningBlocks);
    }

    public WorldBorderUpdatePacket(WorldBorderAction action, int warningTime, int warningBlocks)
    {
        this(action, 0, 0, 0, 0, 0, 0, warningTime, warningBlocks);
    }

    public WorldBorderUpdatePacket(WorldBorderAction action, double x, double z, double oldRadius,
            double newRadius, long speed, int portalTeleportBoundary, int warningTime,
            int warningBlocks)
    {
        this.x = x;
        this.z = z;
        this.action = action;
        this.oldRadius = oldRadius;
        this.newRadius = newRadius;
        this.speed = speed;
        this.portalTeleportBoundary = portalTeleportBoundary;
        this.warningTime = warningTime;
        this.warningBlocks = warningBlocks;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<WorldBorderUpdatePacket>
    {

        @Override
        public Class<WorldBorderUpdatePacket> getType()
        {
            return WorldBorderUpdatePacket.class;
        }

        @Override
        public WorldBorderUpdatePacket read(InputStream is) throws IOException
        {
            WorldBorderAction action = PacketParser.readObject(WorldBorderAction.class, is);

            switch (action)
            {
                case SET_SIZE:
                    return new WorldBorderUpdatePacket(
                            PacketParser.readWrappedObject(VarInt.class, is));

                case LERP_SIZE:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readWrappedObject(VarLong.class, is));

                case SET_CENTER:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is));

                case INITIALIZE:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readWrappedObject(VarLong.class, is),
                            PacketParser.readWrappedObject(VarInt.class, is),
                            PacketParser.readWrappedObject(VarInt.class, is),
                            PacketParser.readWrappedObject(VarInt.class, is));

                case SET_WARNING_TIME:
                    return new WorldBorderUpdatePacket(action,
                            PacketParser.readWrappedObject(VarInt.class, is), 0);

                case SET_WARNING_BLOCKS:
                    return new WorldBorderUpdatePacket(action, 0,
                            PacketParser.readWrappedObject(VarInt.class, is));

                default:
                    return null;
            }
        }

        @Override
        public void write(WorldBorderUpdatePacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.action.id, os);

            switch (packet.action)
            {
                case SET_SIZE:
                    PacketParser.writeObject(Double.class, packet.newRadius, os);

                    break;

                case LERP_SIZE:
                    PacketParser.writeObject(Double.class, packet.oldRadius, os);
                    PacketParser.writeObject(Double.class, packet.newRadius, os);
                    PacketParser.writeObject(VarLong.class, packet.speed, os);

                    break;

                case SET_CENTER:
                    PacketParser.writeObject(Double.class, packet.x, os);
                    PacketParser.writeObject(Double.class, packet.z, os);

                    break;

                case INITIALIZE:
                    PacketParser.writeObject(Double.class, packet.x, os);
                    PacketParser.writeObject(Double.class, packet.z, os);
                    PacketParser.writeObject(Double.class, packet.oldRadius, os);
                    PacketParser.writeObject(Double.class, packet.newRadius, os);
                    PacketParser.writeObject(VarLong.class, packet.speed, os);
                    PacketParser.writeObject(VarInt.class, packet.portalTeleportBoundary, os);
                    PacketParser.writeObject(VarInt.class, packet.warningTime, os);
                    PacketParser.writeObject(VarInt.class, packet.warningBlocks, os);

                    break;

                case SET_WARNING_TIME:
                    PacketParser.writeObject(VarInt.class, packet.warningTime, os);
                    break;

                case SET_WARNING_BLOCKS:
                    PacketParser.writeObject(VarInt.class, packet.warningBlocks, os);
                    break;

                default:
                    break;
            }
        }

    }

}
