package awsome.techmod.worldgen.api.deposit;

import java.util.List;

import awsome.techmod.worldgen.api.PlutonType;
import awsome.techmod.worldgen.utils.Utils;
import net.minecraft.block.BlockState;

/*
 * Using Geolosys worldgen code
 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/api/world/DepositStone.java
 * Original source and credit goes to ohitsjustjose
 */
public class DepositStone implements IDeposit {
	
	private BlockState block;
    private int yMin;
    private int yMax;
    private int chance;
    private int size;
    private String[] dimBlacklist;

    public DepositStone(BlockState stoneBlock, int yMin, int yMax, int chance, int size, String[] dimBlacklist)
    {
        this.block = stoneBlock;
        this.yMin = yMin;
        this.yMax = yMax;
        this.chance = chance;
        this.size = size;
        this.dimBlacklist = dimBlacklist;
    }

    public String[] getDimensionBlacklist()
    {
        return this.dimBlacklist;
    }

    public BlockState getOre()
    {
        return this.block;
    }

    public BlockState getSample()
    {
        return null;
    }

    public int getYMin()
    {
        return this.yMin;
    }

    public int getYMax()
    {
        return this.yMax;
    }

    public int getChance()
    {
        return this.chance;
    }

    public int getSize()
    {
        return this.size;
    }

    public boolean canReplace(BlockState state)
    {
        for (BlockState s : Utils.getDefaultMatchers())
        {
            if (Utils.doStatesMatch(state, s))
            {
                return true;
            }
        }
        return false;
    }

    public List<BlockState> getBlockStateMatchers()
    {
        return null;
    }

    public boolean oreMatches(BlockState other)
    {
        return Utils.doStatesMatch(other, this.block);
    }

    public boolean sampleMatches(BlockState other)
    {
        return true;
    }

    public float getDensity()
    {
        return 1.0F;
    }

    public PlutonType getPlutonType()
    {
        return PlutonType.DENSE;
    }

    public String getFriendlyName()
    {
        return Utils.blockStateToName(this.getOre());
    }
}
