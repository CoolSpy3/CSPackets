package com.coolspy3.cspackets.packets;

import com.coolspy3.csmodloader.network.packet.PacketParser;

public final class GeneratedPackets
{

    public static void registerPackets()
    {
        // Clientbound Packets
        {% for packet in asyncapi | clientboundPackets -%}
        PacketParser.registerPacket({{packet['name']}}.class, {{packet['name']}}::new, {{packet['id']}});
        {% endfor %}

        // Serverbound Packets
        {% for packet in asyncapi | serverboundPackets -%}
        PacketParser.registerPacket({{packet['name']}}.class, {{packet['name']}}::new, {{packet['id']}});
        {% endfor %}
    }

    private GeneratedPackets()
    {}

}
