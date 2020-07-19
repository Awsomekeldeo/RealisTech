package com.awsome.techmod.api.worldgen;

import java.util.List;

import javax.annotation.Nullable;

import com.awsome.techmod.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
/*	Using Geolosys's worldgen code
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/api/world/Deposit.java
 * 	Original source & credit goes to oitsjustjose
 */	

public class Deposit implements IOre {
	private IBlockState oreBlock;
	private int yMin;
	private int yMax;
	private int size;
    private int chance;
    private int[] dimensionBlacklist;
    private List<IBlockState> blockStateMatchers;
    private float density;
    private Block sample;
    private String customName;


	public Deposit(IBlockState oreBlock, Block sampleBlock, int yMin, int yMax, int size, int chance,
            int[] dimensionBlacklist, List<IBlockState> blockStateMatchers, float density, @Nullable String name) {
		this.oreBlock = oreBlock;
		this.yMin = yMin;
		this.sample = sampleBlock;
		this.yMax = yMax;
		this.size = size;
		this.chance = chance;
		this.dimensionBlacklist = dimensionBlacklist;
		this.density = density;
		this.blockStateMatchers = blockStateMatchers;
		if (name != null) {
            this.customName = name;
        }
	}
	
	@Override
	public IBlockState getOre() {
		return this.oreBlock;
	}
	
	@Override
	public Block getSample() {
		return this.sample;
	}

	@Override
	public String getDepositName() {
		return this.customName == null
                ? new ItemStack(this.oreBlock.getBlock(), 1, this.oreBlock.getBlock().getMetaFromState(this.oreBlock))
                        .getDisplayName()
                : this.customName;
	}

	@Override
	public int getYMin() {
		return this.yMin;
	}

	@Override
	public int getYMax() {
		return this.yMax;
	}

	@Override
	public int getChance() {
		return this.chance;
	}

	@Override
	public int getSize() {
		return this.size;
	}

	@Override
	public boolean canReplace(IBlockState state) {
		if (this.blockStateMatchers == null) {
            return true;
        }
        for (IBlockState s : this.blockStateMatchers) {
            if (Utils.doStatesMatch(s, state)) {
                return true;
            }
        }
        return false;
	}

	@Override
	public int[] getDimBlacklist() {
		return this.dimensionBlacklist;
	}
	
	public List<IBlockState> getBlockStateMatchers() {
		return this.blockStateMatchers;
	}

	@Override
	public boolean oreMatches(IBlockState other) {
		return Utils.doStatesMatch(this.oreBlock, other);
	}

	@Override
	public float getDensity() {
		return this.density;
	}

}
