package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.cspackets.datatypes.CombatEvent;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class CombatEventPacket extends Packet
{

    public final CombatEvent event;
    public final int duration, playerId, entityId;
    public final String message;

    public CombatEventPacket(CombatEvent event, int duration, int playerId, int entityId,
            String message)
    {
        this.event = event;
        this.duration = duration;
        this.playerId = playerId;
        this.entityId = entityId;
        this.message = message;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

    public static class Serializer implements PacketSerializer<CombatEventPacket>
    {

        @Override
        public Class<CombatEventPacket> getType()
        {
            return CombatEventPacket.class;
        }

        @Override
        public CombatEventPacket read(InputStream is) throws IOException
        {
            CombatEvent event = PacketParser.readObject(CombatEvent.class, is);

            switch (event)
            {
                case ENTER_COMBAT:
                    return new CombatEventPacket(event, 0, 0, 0, null);

                case END_COMBAT:
                    return new CombatEventPacket(event,
                            PacketParser.readWrappedObject(Packet.VarInt.class, is), 0,
                            PacketParser.readWrappedObject(Packet.VarInt.class, is), null);

                case ENTITY_DEAD:
                    return new CombatEventPacket(event, 0,
                            PacketParser.readWrappedObject(Packet.VarInt.class, is),
                            PacketParser.readWrappedObject(Packet.VarInt.class, is),
                            PacketParser.readObject(String.class, is));

                default:
                    return null;
            }
        }

        @Override
        public void write(CombatEventPacket packet, OutputStream os) throws IOException
        {
            PacketParser.writeObject(CombatEvent.class, packet.event, os);

            switch (packet.event)
            {
                case ENTER_COMBAT:
                    break;

                case END_COMBAT:
                    PacketParser.writeObject(Packet.VarInt.class, packet.duration, os);
                    PacketParser.writeObject(Packet.VarInt.class, packet.entityId, os);

                    break;

                case ENTITY_DEAD:
                    PacketParser.writeObject(Packet.VarInt.class, packet.playerId, os);
                    PacketParser.writeObject(Packet.VarInt.class, packet.entityId, os);
                    PacketParser.writeObject(String.class, packet.message, os);

                    break;

                default:
                    break;
            }
        }

    }

}
