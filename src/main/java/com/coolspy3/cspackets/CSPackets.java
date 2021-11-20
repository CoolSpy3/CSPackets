package com.coolspy3.cspackets;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.cspackets.datatypes.Difficulty;
import com.coolspy3.cspackets.datatypes.Dimension;
import com.coolspy3.cspackets.datatypes.Gamemode;
import com.coolspy3.cspackets.packets.AllPackets;

@Mod(id = "cspackets", name = "CSPackets",
        description = "Adds implementations for default packet types", version = "1.0.0",
        dependencies = "csmodloader:[1.0.0,2)")
public class CSPackets implements Entrypoint
{

    public CSPackets()
    {
        // Parsers
        PacketParser.addParser(PacketParser.mappingParser(Byte.class, Difficulty::getId,
                Difficulty::withId, Difficulty.class));
        PacketParser.addParser(PacketParser.mappingParser(Byte.class, Dimension::getId,
                Dimension::withId, Dimension.class));
        PacketParser.addParser(PacketParser.mappingParser(Byte.class, Gamemode::getId,
                Gamemode::withId, Gamemode.class));

        AllPackets.registerPackets();
    }

}
