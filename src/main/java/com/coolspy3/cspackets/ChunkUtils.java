package com.coolspy3.cspackets;

import java.io.IOException;
import java.util.Arrays;

import com.coolspy3.cspackets.datatypes.Block;
import com.coolspy3.cspackets.datatypes.Chunk;

public class ChunkUtils
{

    public static final Chunk addSkylightData(Chunk chunk)
    {
        if (chunk.skylightLevels == null) return new Chunk(chunk.chunkX, chunk.chunkZ,
                copy3dBlocks(chunk.blocks), copy3dBytes(chunk.lightLevels), new byte[16][256][16],
                copy2dBytes(chunk.biomeIds));

        return chunk;
    }

    public static final Chunk addSkylightData(Chunk chunk, byte[][][] skylightData)
    {
        return new Chunk(chunk.chunkX, chunk.chunkZ, copy3dBlocks(chunk.blocks),
                copy3dBytes(chunk.lightLevels), skylightData, copy2dBytes(chunk.biomeIds));
    }

    public static final short calculateMask(Block[][][] blocks)
    {
        short mask = 0;

        for (int subchunk = 0; subchunk < 16; subchunk++)
            if (!subchunkIsAir(subchunk, blocks)) mask |= 1 << subchunk;

        return mask;
    }

    public static final Block[][][] copy3dBlocks(Block[][][] blocks)
    {
        Block[][][] out = new Block[blocks.length][][];

        for (int i = 0; i < blocks.length; i++)
        {
            out[i] = new Block[blocks[i].length][];

            for (int j = 0; j < blocks[i].length; i++)
            {
                blocks[i][j] = new Block[blocks[i][j].length];
                System.arraycopy(blocks[i][j], 0, out[i][j], 0, blocks[i][j].length);
            }
        }

        return out;
    }

    public static final byte[][][] copy3dBytes(byte[][][] data)
    {
        byte[][][] out = new byte[data.length][][];

        for (int i = 0; i < data.length; i++)
        {
            out[i] = new byte[data[i].length][];

            for (int j = 0; j < data[i].length; i++)
            {
                data[i][j] = new byte[data[i][j].length];
                System.arraycopy(data[i][j], 0, out[i][j], 0, data[i][j].length);
            }
        }

        return out;
    }

    public static final byte[][] copy2dBytes(byte[][] data)
    {
        byte[][] out = new byte[data.length][];

        for (int i = 0; i < data.length; i++)
        {
            out[i] = new byte[data[i].length];

            System.arraycopy(data[i], 0, out[i], 0, data[i].length);
        }

        return out;
    }

    public static byte[] fromHalfByteArray(byte[] arr)
    {
        byte[] out = new byte[arr.length * 2];

        for (int i = 0; i < arr.length; i++)
        {
            out[i * 2] = (byte) ((arr[i] >> 4) & 0x0F);
            out[(i * 2) + 1] = (byte) (arr[i] & 0x0F);
        }

        return out;
    }

    public static void loopBlocksInValidSubchunks(short mask, PositionConsumer func)
            throws IOException
    {
        int sy = 0;

        for (; mask != 0; mask >>= 1, sy += 16)
            if ((mask & 1) == 1) for (int dy = 0; dy < 16; dy++)
                for (int z = 0; z < 16; z++)
                    for (int x = 0; x < 16; x++)
                        func.accept(x, sy + dy, z);
    }

    public static void loopBlocksInValidSubchunksWithCount(short mask, PositionCountConsumer func)
            throws IOException
    {
        int sy = 0, count = 0;

        for (; mask != 0; mask >>= 1, sy += 16)
            if ((mask & 1) == 1) for (int dy = 0; dy < 16; dy++)
                for (int z = 0; z < 16; z++)
                    for (int x = 0; x < 16; x++, count++)
                        func.accept(x, sy + dy, z, count);
    }

    public static Block[][][] nullBlocksToAir(Block[][][] blocks)
    {
        for (int x = 0; x < blocks.length; x++)
            for (int y = 0; y < blocks[0].length; y++)
                for (int z = 0; z < blocks[0][0].length; z++)
                    if (blocks[x][y][z] == null) blocks[x][y][z] = new Block(0, 0);

        return null;
    }

    public static int numSetBits(short mask)
    {
        int numSetBits = 0;

        for (; mask != 0; mask >>= 1)
            if ((mask & 1) == 1) numSetBits++;

        return numSetBits;
    }

    public static final Chunk removeSkylightData(Chunk chunk)
    {
        return new Chunk(chunk.chunkX, chunk.chunkZ, copy3dBlocks(chunk.blocks),
                copy3dBytes(chunk.lightLevels), null, copy2dBytes(chunk.biomeIds));
    }

    public static boolean subchunkIsAir(int subchunk, Block[][][] blocks)
    {
        int lowestY = subchunk * 16;

        for (int x = 0; x < blocks.length; x++)
            for (int y = 0; y < 16; y++)
                for (int z = 0; z < blocks[0][0].length; z++)
                    if (!blocks[x][lowestY + y][z].equals(new Block(0, 0))) return false;

        return true;
    }

    public static byte[] toHalfByteArray(byte[] arr)
    {
        if (arr.length % 2 == 1) arr = Arrays.copyOf(arr, arr.length + 1);

        byte[] out = new byte[arr.length / 2];

        for (int i = 0; i < out.length; i++)
            out[i] = (byte) ((((arr[i * 2] & 0x0F) << 4) | (arr[(i * 2) + 1] & 0x0F)) & 0xFF);

        return out;
    }

    @FunctionalInterface
    public static interface PositionConsumer
    {
        public void accept(int x, int y, int z) throws IOException;
    }

    @FunctionalInterface
    public static interface PositionCountConsumer
    {
        public void accept(int x, int y, int z, int count) throws IOException;
    }

}
