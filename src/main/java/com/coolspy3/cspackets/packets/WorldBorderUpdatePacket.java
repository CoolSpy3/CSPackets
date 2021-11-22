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
public class WorldBorderUpdatePacket extends Packet
{

    public final Action action;
    public final double x, z, oldRadius, newRadius;
    public final long speed;
    public final int portalTeleportBoundary, warningTime, warningBlocks;

    public WorldBorderUpdatePacket(double radius)
    {
        this(Action.SET_SIZE, 0, 0, 0, radius, 0, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double oldRadius, double newRadius, long speed)
    {
        this(Action.LERP_SIZE, 0, 0, oldRadius, newRadius, speed, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double x, double z)
    {
        this(Action.LERP_SIZE, x, z, 0, 0, 0, 0, 0, 0);
    }

    public WorldBorderUpdatePacket(double x, double z, double oldRadius, double newRadius,
            long speed, int portalTeleportBoundary, int warningTime, int warningBlocks)
    {
        this(Action.INITIALIZE, x, z, oldRadius, newRadius, speed, portalTeleportBoundary,
                warningTime, warningBlocks);
    }

    public WorldBorderUpdatePacket(Action action, int warningTime, int warningBlocks)
    {
        this(action, 0, 0, 0, 0, 0, 0, warningTime, warningBlocks);
    }

    public WorldBorderUpdatePacket(Action action, double x, double z, double oldRadius,
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

    public static enum Action
    {

        SET_SIZE(0), LERP_SIZE(1), SET_CENTER(2), INITIALIZE(3), SET_WARNING_TIME(
                4), SET_WARNING_BLOCKS(5);

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
            Action action = Action.withId(Utils.readVarInt(is));

            switch (action)
            {
                case SET_SIZE:
                    return new WorldBorderUpdatePacket(Utils.readVarInt(is));

                case LERP_SIZE:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is), Utils.readVarLong(is));

                case SET_CENTER:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is));

                case INITIALIZE:
                    return new WorldBorderUpdatePacket(PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is),
                            PacketParser.readObject(Double.class, is), Utils.readVarLong(is),
                            Utils.readVarInt(is), Utils.readVarInt(is), Utils.readVarInt(is));

                case SET_WARNING_TIME:
                    return new WorldBorderUpdatePacket(action, Utils.readVarInt(is), 0);

                case SET_WARNING_BLOCKS:
                    return new WorldBorderUpdatePacket(action, 0, Utils.readVarInt(is));

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
                    Utils.writeVarLong(packet.speed, os);

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
                    Utils.writeVarLong(packet.speed, os);
                    Utils.writeVarInt(packet.portalTeleportBoundary, os);
                    Utils.writeVarInt(packet.warningTime, os);
                    Utils.writeVarInt(packet.warningBlocks, os);

                    break;

                case SET_WARNING_TIME:
                    Utils.writeVarInt(packet.warningTime, os);
                    break;

                case SET_WARNING_BLOCKS:
                    Utils.writeVarInt(packet.warningBlocks, os);
                    break;

                default:
                    break;
            }
        }

    }

}
