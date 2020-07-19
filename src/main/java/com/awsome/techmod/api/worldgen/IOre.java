package com.awsome.techmod.api.worldgen;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
/*	Using Geolosys's worldgen code
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/api/world/IOre.java
 * 	Original source & credit goes to oitsjustjose
 */	

public interface IOre {
	public IBlockState getOre();
	
	public String getDepositName();
	
	public int getYMin();
	
	public int getYMax();
	
	public int getChance();
	
	public int getSize();
	
	public int[] getDimBlacklist();
	
	public float getDensity();

	public boolean canReplace(IBlockState state);

	public boolean oreMatches(IBlockState state);

	public List<IBlockState> getBlockStateMatchers();

	public Block getSample();
}
