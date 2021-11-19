package com.coolspy3.cspackets;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;
import com.coolspy3.cspackets.packets.ServerChatSendPacket;

@Mod(id = "cspackets", name = "CSPackets",
        description = "Adds implementations for default packet types", version = "1.0.0",
        dependencies = "csmodloader:[1.0.0,2)")
public class CSPackets implements Entrypoint
{

    public CSPackets()
    {
        // Clientbound Packets
        PacketParser.registerPacket(ServerChatSendPacket.class, ServerChatSendPacket::new, 0x02);

        // Serverbound Packets
        PacketParser.registerPacket(ClientChatSendPacket.class, ClientChatSendPacket::new, 0x01);
    }

}
