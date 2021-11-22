package com.coolspy3.cspackets.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSerializer;
import com.coolspy3.csmodloader.network.packet.PacketSpec;
import com.coolspy3.csmodloader.util.Utils;

@PacketSpec(types = {}, direction = PacketDirection.CLIENTBOUND)
public class CombatEventPacket extends Packet
{

    public final Event event;
    public final int duration, playerId, entityId;
    public final String message;

    public CombatEventPacket(Event event, int duration, int playerId, int entityId, String message)
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

    public static enum Event
    {
        ENTER_COMBAT(0), END_COMBAT(1), ENTITY_DEAD(2);

        public final int id;

        private Event(int id)
        {
            this.id = id;
        }

        public Integer getId()
        {
            return id;
        }

        public static Event withId(int id)
        {
            for (Event val : values())
                if (val.id == id) return val;

            return null;
        }
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
            Event event = Event.withId(Utils.readVarInt(is));

            switch (event)
            {
                case ENTER_COMBAT:
                    return new CombatEventPacket(event, 0, 0, 0, null);

                case END_COMBAT:
                    return new CombatEventPacket(event, Utils.readVarInt(is), 0,
                            Utils.readVarInt(is), null);

                case ENTITY_DEAD:
                    return new CombatEventPacket(event, 0, Utils.readVarInt(is),
                            Utils.readVarInt(is), Utils.readString(is));

                default:
                    return null;
            }
        }

        @Override
        public void write(CombatEventPacket packet, OutputStream os) throws IOException
        {
            Utils.writeVarInt(packet.event.id, os);

            switch (packet.event)
            {
                case ENTER_COMBAT:
                    break;

                case END_COMBAT:
                    Utils.writeVarInt(packet.duration, os);
                    Utils.writeVarInt(packet.entityId, os);

                    break;

                case ENTITY_DEAD:
                    Utils.writeVarInt(packet.playerId, os);
                    Utils.writeVarInt(packet.entityId, os);
                    Utils.writeString(packet.message, os);

                    break;

                default:
                    break;
            }
        }

    }

}
