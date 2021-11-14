package com.coolspy3.cspackets;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.PacketDirection;
import com.coolspy3.csmodloader.network.packet.PacketParser;

@Mod(id = "cspackets", name = "CSPackets",
        description = "Adds implementations for default packet types", version = "1.0.0")
public class Main implements Entrypoint
{

    public Main()
    {
        PacketParser.addSpecification(new ClientChatSendPacket.Spec());
        PacketParser.registerPacketClass(PacketDirection.SERVERBOUND, ClientChatSendPacket.class,
                0x01);
    }

}
