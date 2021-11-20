package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketSpec;

@PacketSpec(types = {Long.class, Long.class}, direction = PacketDirection.CLIENTBOUND)
public class TimeUpdatePacket extends Packet
{

    public final long worldAge;
    public final long timeOfDay;

    public TimeUpdatePacket(long worldAge, long timeOfDay)
    {
        this.worldAge = worldAge;
        this.timeOfDay = timeOfDay;
    }

    public TimeUpdatePacket(Object[] args)
    {
        this((long) args[0], (long) args[1]);
    }

    @Override
    public Object[] getValues()
    {
        return new Object[] {worldAge, timeOfDay};
    }

}
