package com.coolspy3.cspackets.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.coolspy3.csmodloader.network.packet.PacketParser;
import com.coolspy3.csmodloader.util.Utils;
import com.coolspy3.cspackets.ChunkUtils;

public class Chunk
{

    public static final int BLOCKS_PER_SUBCHUNK = 16 * 16 * 16;
    public static final short MAX_MASK = (short) 0xFFFF;

    // 2 bytes per block + .5 bytes per light level
    public static final int MIN_BYTES_IN_SUBCHUNK = (int) (2.5 * BLOCKS_PER_SUBCHUNK);

    public final int chunkX, chunkZ;

    // [x][y][z]
    public final Block[][][] blocks;
    public final byte[][][] lightLevels, skylightLevels;

    // [Subchunk][z][x]
    public final byte[][] biomeIds;

    public Chunk(Chunk chunk)
    {
        this(chunk.chunkX, chunk.chunkZ, ChunkUtils.copy3dBlocks(chunk.blocks),
                ChunkUtils.copy3dBytes(chunk.lightLevels),
                ChunkUtils.copy3dBytes(chunk.skylightLevels),
                ChunkUtils.copy2dBytes(chunk.biomeIds));
    }

    public Chunk(int chunkX, int chunkZ, Block[][][] blocks, byte[][][] lightLevels,
            byte[][][] skylightLevels, byte[][] biomeIds)
    {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.blocks = ChunkUtils.nullBlocksToAir(blocks);
        this.lightLevels = lightLevels == null ? new byte[16][256][16] : lightLevels;
        this.skylightLevels = skylightLevels;
        this.biomeIds = biomeIds == null ? new byte[16][16] : biomeIds;
    }

    public static Chunk read(int chunkX, int chunkZ, short mask, boolean hasSkylightData,
            boolean hasBiomeData, InputStream is) throws IOException
    {
        int numSubchunks = ChunkUtils.numSetBits(mask);

        Block[][][] blocks = new Block[16][256][16];

        ChunkUtils.loopBlocksInValidSubchunks(mask, (x, y, z) -> blocks[x][y][z] =
                PacketParser.readWrappedObject(Block.AsShort.class, is));

        byte[][][] lightLevels = new byte[16][256][16];

        byte[] rawLightLevels = ChunkUtils
                .fromHalfByteArray(Utils.readNBytes(is, numSubchunks * BLOCKS_PER_SUBCHUNK / 2));

        ChunkUtils.loopBlocksInValidSubchunksWithCount(mask,
                (x, y, z, pos) -> lightLevels[x][y][z] = rawLightLevels[pos]);

        byte[][][] skylightLevels = hasSkylightData ? new byte[16][256][16] : null;

        if (hasSkylightData)
        {
            byte[] rawSkylightLevels = ChunkUtils.fromHalfByteArray(
                    Utils.readNBytes(is, numSubchunks * BLOCKS_PER_SUBCHUNK / 2));

            ChunkUtils.loopBlocksInValidSubchunksWithCount(mask,
                    (x, y, z, pos) -> skylightLevels[x][y][z] = rawSkylightLevels[pos]);
        }

        byte[][] biomeIds = new byte[16][16];

        if (hasBiomeData) for (int z = 0; z < 16; z++)
            for (int x = 0; x < 16; x++)
                biomeIds[x][z] = (byte) Utils.readByte(is);

        return new Chunk(chunkX, chunkZ, blocks, lightLevels, skylightLevels, biomeIds);
    }

    public short write(boolean sendSkylightData, boolean sendBiomeData, OutputStream os)
            throws IOException
    {
        short mask = ChunkUtils.calculateMask(blocks);
        int numSubchunks = ChunkUtils.numSetBits(mask);

        ChunkUtils.loopBlocksInValidSubchunks(mask,
                (x, y, z) -> PacketParser.writeObject(Block.AsShort.class, blocks[x][y][z], os));

        byte[] rawLightLevels = new byte[numSubchunks * BLOCKS_PER_SUBCHUNK];

        ChunkUtils.loopBlocksInValidSubchunksWithCount(mask,
                (x, y, z, pos) -> rawLightLevels[pos] = lightLevels[x][y][z]);

        os.write(ChunkUtils.toHalfByteArray(rawLightLevels));

        if (sendSkylightData)
        {
            byte[] rawSkylightLevels = new byte[numSubchunks * BLOCKS_PER_SUBCHUNK];

            ChunkUtils.loopBlocksInValidSubchunksWithCount(mask,
                    (x, y, z, pos) -> rawSkylightLevels[pos] = skylightLevels[x][y][z]);

            os.write(ChunkUtils.toHalfByteArray(rawSkylightLevels));
        }

        if (sendBiomeData) for (int z = 0; z < 16; z++)
            for (int x = 0; x < 16; x++)
                os.write((byte) biomeIds[x][z]);

        return mask;
    }

}
