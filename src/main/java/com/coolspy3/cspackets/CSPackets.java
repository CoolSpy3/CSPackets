package com.coolspy3.cspackets;

import java.util.Locale;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.packet.ObjectParser;
import com.coolspy3.csmodloader.network.packet.Packet;
import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.datatypes.BlockUpdateAction;
import com.coolspy3.cspackets.datatypes.ChatMode;
import com.coolspy3.cspackets.datatypes.ClientAction;
import com.coolspy3.cspackets.datatypes.CombatEvent;
import com.coolspy3.cspackets.datatypes.Difficulty;
import com.coolspy3.cspackets.datatypes.Dimension;
import com.coolspy3.cspackets.datatypes.EntityInteractionType;
import com.coolspy3.cspackets.datatypes.EntityMetadata;
import com.coolspy3.cspackets.datatypes.EntityModifier;
import com.coolspy3.cspackets.datatypes.EntityProperties;
import com.coolspy3.cspackets.datatypes.EntityStatus;
import com.coolspy3.cspackets.datatypes.Face;
import com.coolspy3.cspackets.datatypes.FriendlyFireSetting;
import com.coolspy3.cspackets.datatypes.Gamemode;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.datatypes.NameTagVisibility;
import com.coolspy3.cspackets.datatypes.PaintingDirection;
import com.coolspy3.cspackets.datatypes.PlayerAbilities;
import com.coolspy3.cspackets.datatypes.PlayerAction;
import com.coolspy3.cspackets.datatypes.PlayerAnimation;
import com.coolspy3.cspackets.datatypes.PlayerDiggingType;
import com.coolspy3.cspackets.datatypes.PlayerListAction;
import com.coolspy3.cspackets.datatypes.PlayerPositionAndLookFlags;
import com.coolspy3.cspackets.datatypes.Position;
import com.coolspy3.cspackets.datatypes.ResourcePackStatus;
import com.coolspy3.cspackets.datatypes.ScoreboardObjectiveAction;
import com.coolspy3.cspackets.datatypes.ScoreboardObjectiveType;
import com.coolspy3.cspackets.datatypes.ScoreboardPosition;
import com.coolspy3.cspackets.datatypes.SkinParts;
import com.coolspy3.cspackets.datatypes.Slot;
import com.coolspy3.cspackets.datatypes.Statistic;
import com.coolspy3.cspackets.datatypes.TeamAction;
import com.coolspy3.cspackets.datatypes.TitleAction;
import com.coolspy3.cspackets.datatypes.VehicleSteerFlags;
import com.coolspy3.cspackets.datatypes.WorldBorderAction;
import com.coolspy3.cspackets.packets.BlockChangePacket;
import com.coolspy3.cspackets.packets.CombatEventPacket;
import com.coolspy3.cspackets.packets.EntityDestroyPacket;
import com.coolspy3.cspackets.packets.EntityUsePacket;
import com.coolspy3.cspackets.packets.ExplosionPacket;
import com.coolspy3.cspackets.packets.GeneratedPackets;
import com.coolspy3.cspackets.packets.MapUpdatePacket;
import com.coolspy3.cspackets.packets.MultiblockChangePacket;
import com.coolspy3.cspackets.packets.ObjectSpawnPacket;
import com.coolspy3.cspackets.packets.ParticlePacket;
import com.coolspy3.cspackets.packets.PlayerListPacket;
import com.coolspy3.cspackets.packets.ScoreboardObjectivePacket;
import com.coolspy3.cspackets.packets.ScoreboardUpdatePacket;
import com.coolspy3.cspackets.packets.StatisticsPacket;
import com.coolspy3.cspackets.packets.TabCompletePacket;
import com.coolspy3.cspackets.packets.TabCompleteResponsePacket;
import com.coolspy3.cspackets.packets.TeamUpdatePacket;
import com.coolspy3.cspackets.packets.TitlePacket;
import com.coolspy3.cspackets.packets.WindowActionPacket;
import com.coolspy3.cspackets.packets.WindowItemsPacket;
import com.coolspy3.cspackets.packets.WindowOpenPacket;
import com.coolspy3.cspackets.packets.WorldBorderUpdatePacket;

import net.querz.nbt.io.NBTDeserializer;
import net.querz.nbt.io.NBTSerializer;
import net.querz.nbt.io.NamedTag;

@Mod(id = "cspackets", name = "CSPackets",
		description = "Adds implementations for default packet types", version = "1.0.0",
		dependencies = "csmodloader:[1.0.0,2)")
public class CSPackets implements Entrypoint
{

	private static final NBTDeserializer nbtDeserializer = new NBTDeserializer(false, false);
	private static final NBTSerializer nbtSerializer = new NBTSerializer(false, false);

	public CSPackets()
	{
		// Parsers
		PacketParser.addParser(PacketParser.mappingParser(Byte.class, BlockUpdateAction::getId,
				BlockUpdateAction::withId, BlockUpdateAction.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, ChatMode::getId,
				ChatMode::withId, ChatMode.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				ClientAction::getId, ClientAction::withId, ClientAction.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				CombatEvent::getId, CombatEvent::withId, CombatEvent.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, Difficulty::getId,
				Difficulty::withId, Difficulty.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, Dimension::getId,
				Dimension::withId, Dimension.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				EntityInteractionType::getId, EntityInteractionType::withId,
				EntityInteractionType.class));

		PacketParser.addParser(new EntityMetadata.Parser());

		PacketParser
				.addParser(PacketParser.mappingParser(Byte.class, EntityModifier.Operation::getId,
						EntityModifier.Operation::withId, EntityModifier.Operation.class));
		PacketParser.addParser(new EntityModifier.Parser());

		PacketParser.addParser(new EntityProperties.Parser());

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, EntityStatus::getId,
				EntityStatus::withId, EntityStatus.class));

		PacketParser.addParser(
				PacketParser.mappingParser(Byte.class, Face::getId, Face::withId, Face.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, FriendlyFireSetting::getId,
				FriendlyFireSetting::withId, FriendlyFireSetting.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, Gamemode::getId,
				Gamemode::withId, Gamemode.class));

		PacketParser.addParser(PacketParser.mappingParser(String.class, Locale::toString,
				locale -> new Locale(locale.split("_")[0], locale.split("_")[1]), Locale.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, MCColor::getId,
				MCColor::withId, MCColor.class));

		PacketParser.addParser(
				ObjectParser.of(nbt -> Utils.wrap(() -> Utils.box(nbtSerializer.toBytes(nbt))),
						nbtDeserializer::fromStream, NamedTag.class));

		PacketParser.addParser(PacketParser.mappingParser(String.class, NameTagVisibility::getId,
				NameTagVisibility::withId, NameTagVisibility.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, PaintingDirection::getId,
				PaintingDirection::withId, PaintingDirection.class));

		PacketParser.addParser(PacketParser.wrappingMappingParser(Byte.class,
				PlayerAbilities.CLIENTBOUND_WRITER, PlayerAbilities.CLIENTBOUND_READER,
				PlayerAbilities.class, PlayerAbilities.Clientbound.class));

		PacketParser.addParser(PacketParser.wrappingMappingParser(Byte.class,
				PlayerAbilities.SERVERBOUND_WRITER, PlayerAbilities.SERVERBOUND_READER,
				PlayerAbilities.class, PlayerAbilities.Serverbound.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				PlayerAction::getId, PlayerAction::withId, PlayerAction.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, PlayerAnimation::getId,
				PlayerAnimation::withId, PlayerAnimation.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, PlayerDiggingType::getId,
				PlayerDiggingType::withId, PlayerDiggingType.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				PlayerListAction::getId, PlayerListAction::withId, PlayerListAction.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class,
				flags -> (byte) ((flags.xRelative ? 0x01 : 0x00) | (flags.yRelative ? 0x02 : 0x00)
						| (flags.zRelative ? 0x04 : 0x00) | (flags.yawRelative ? 0x08 : 0x00)
						| (flags.pitchRelative ? 0x10 : 0x00)),
				flags -> new PlayerPositionAndLookFlags((flags & 0x01) == 0x01,
						(flags & 0x02) == 0x02, (flags & 0x04) == 0x04, (flags & 0x08) == 0x08,
						(flags & 0x10) == 0x10),
				PlayerPositionAndLookFlags.class));

		// Credit: https://wiki.vg/index.php?title=Protocol&oldid=7368#Position
		PacketParser.addParser(PacketParser.mappingParser(Long.class,
				pos -> (((long) pos.x & 0x3FFFFFF) << 38) | (((long) pos.y & 0xFFF) << 26)
						| ((long) pos.z & 0x3FFFFFF),
				val -> new Position((int) (val >> 38), (int) ((val >> 26) & 0xFFF),
						(int) (val << 38 >> 38)),
				Position.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				ResourcePackStatus::getId, ResourcePackStatus::withId, ResourcePackStatus.class));

		PacketParser
				.addParser(PacketParser.mappingParser(Byte.class, ScoreboardObjectiveAction::getId,
						ScoreboardObjectiveAction::withId, ScoreboardObjectiveAction.class));

		PacketParser
				.addParser(PacketParser.mappingParser(String.class, ScoreboardObjectiveType::getId,
						ScoreboardObjectiveType::withId, ScoreboardObjectiveType.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, ScoreboardPosition::getId,
				ScoreboardPosition::withId, ScoreboardPosition.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, SkinParts.WRITER,
				SkinParts.READER, SkinParts.class));

		PacketParser.addParser(new Slot.Parser());

		PacketParser.addParser(new Statistic.Parser());

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, TeamAction::getId,
				TeamAction::withId, TeamAction.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				TitleAction::getId, TitleAction::withId, TitleAction.class));

		PacketParser.addParser(PacketParser.mappingParser(Byte.class, VehicleSteerFlags.WRITER,
				VehicleSteerFlags.READER, VehicleSteerFlags.class));

		PacketParser.addParser(PacketParser.mappingWrappingParser(Packet.VarInt.class,
				WorldBorderAction::getId, WorldBorderAction::withId, WorldBorderAction.class));


		// Packet Specific Parsers
		PacketParser.addParser(new MultiblockChangePacket.Record.Parser());

		PacketParser.addParser(new PlayerListPacket.PlayerInfo.Property.Parser());


		GeneratedPackets.registerPackets();


		// Clientbound Packets
		PacketParser.registerPacket(ObjectSpawnPacket.class, new ObjectSpawnPacket.Serializer(),
				0x0E);

		PacketParser.registerPacket(EntityDestroyPacket.class, new EntityDestroyPacket.Serializer(),
				0x13);

		PacketParser.registerPacket(MultiblockChangePacket.class,
				new MultiblockChangePacket.Serializer(), 0x22);

		PacketParser.registerPacket(BlockChangePacket.class, new BlockChangePacket.Serializer(),
				0x23);

		PacketParser.registerPacket(ExplosionPacket.class, new ExplosionPacket.Serializer(), 0x27);

		PacketParser.registerPacket(ParticlePacket.class, new ParticlePacket.Serializer(), 0x2A);

		PacketParser.registerPacket(WindowOpenPacket.class, new WindowOpenPacket.Serializer(),
				0x2D);

		PacketParser.registerPacket(WindowItemsPacket.class, new WindowItemsPacket.Serializer(),
				0x30);

		PacketParser.registerPacket(MapUpdatePacket.class, new MapUpdatePacket.Serializer(), 0x34);

		PacketParser.registerPacket(StatisticsPacket.class, new StatisticsPacket.Serializer(),
				0x37);

		PacketParser.registerPacket(PlayerListPacket.class, new PlayerListPacket.Serializer(),
				0x38);

		PacketParser.registerPacket(TabCompleteResponsePacket.class,
				new TabCompleteResponsePacket.Serializer(), 0x3A);

		PacketParser.registerPacket(ScoreboardObjectivePacket.class,
				new ScoreboardObjectivePacket.Serializer(), 0x3B);

		PacketParser.registerPacket(ScoreboardUpdatePacket.class,
				new ScoreboardUpdatePacket.Serializer(), 0x3C);

		PacketParser.registerPacket(TeamUpdatePacket.class, new TeamUpdatePacket.Serializer(),
				0x3E);

		PacketParser.registerPacket(CombatEventPacket.class, new CombatEventPacket.Serializer(),
				0x42);

		PacketParser.registerPacket(WorldBorderUpdatePacket.class,
				new WorldBorderUpdatePacket.Serializer(), 0x44);

		PacketParser.registerPacket(TitlePacket.class, new TitlePacket.Serializer(), 0x45);


		// Serverbound Packets
		PacketParser.registerPacket(EntityUsePacket.class, new EntityUsePacket.Serializer(), 0x02);

		PacketParser.registerPacket(WindowActionPacket.class, new WindowActionPacket.Serializer(),
				0x0E);

		PacketParser.registerPacket(TabCompletePacket.class, new TabCompletePacket.Serializer(),
				0x14);
	}

}
