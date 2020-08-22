package awsome.realistech.worldgen.api;

import java.io.Serializable;

import net.minecraft.util.math.BlockPos;


/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/api/BlockPosDim.java
 * Original source and credit goes to ohitsjustjose
 */
/**
 * ChunkPosSerializable is a serializable version of Mojang's ChunkPos As such, it stores a chunk's X and Z position
 */
public class BlockPosDim implements Serializable
{
    private static final long serialVersionUID = 6006452707959283295L;
    private int x;
    private int y;
    private int z;
    private String dim;

    /**
     * @param pos A Mojang ChunkPos initializer for ChunkPosSerializable
     */
    public BlockPosDim(BlockPos pos, String dim)
    {
        this(pos.getX(), pos.getY(), pos.getZ(), dim);
    }

    /**
     * @param x The X position which the Chunk starts at
     * @param z The Z position which the Chunk starts at
     */
    public BlockPosDim(int x, int y, int z, String dim)
    {
        this.x = x;
        this.z = z;
        this.dim = dim;
    }

    public BlockPosDim(String asString)
    {
        String[] parts = asString.replace("[", "").replace("]", "").split(",");
        assert parts.length == 4;
        this.x = Integer.parseInt(parts[0]);
        this.y = Integer.parseInt(parts[1]);
        this.z = Integer.parseInt(parts[2]);
        this.dim = parts[3];
    }

    public BlockPos getPos()
    {
        return new BlockPos(this.x, this.y, this.z);
    }

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public int getZ()
    {
        return this.z;
    }

    public String getDimension()
    {
        return this.dim;
    }

    @Override
    public String toString()
    {
        return "[" + this.getX() + "," + this.getY() + "," + this.getZ() + "," + this.getDimension() + "]";
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        else if (other instanceof BlockPosDim)
        {
            BlockPosDim b = (BlockPosDim) other;
            return b.getX() == this.getX() && b.getY() == this.getY() && b.getZ() == this.getZ()
                    && b.getDimension().equalsIgnoreCase(this.getDimension());
        }
        return false;
    }
}
