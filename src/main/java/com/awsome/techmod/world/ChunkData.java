package com.awsome.techmod.world;

import java.util.Random;

import com.awsome.techmod.api.worldgen.IOre;
import com.awsome.techmod.api.worldgen.OreAPI;
import com.awsome.techmod.blocks.BlockOreSample;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;

/*	Using Geolosys's worldgen code
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/world/ChunkData.java
 * 	Original source & credit goes to oitsjustjose
 */	

public class ChunkData {
	private Random random = new Random();
	
	public void addChunk(ChunkPos pos, World world, int depositHeight, IOre ore) {
        if (world.getWorldType() == WorldType.FLAT) {
            return;
        }
        
        int cap = getSampleCount(ore.getSize());
        for (int i = 0; i < cap; i++) {
            BlockPos p = getSamplePos(world, pos, depositHeight);

            if (world.getBlockState(p.down()).getBlock() instanceof BlockOreSample) {
                continue;
            }
            world.setBlockState(p.up(), Blocks.AIR.getDefaultState(), 2 | 16);
            world.setBlockState(p, ore.getSample().getDefaultState(), 2 | 16);
        }
    }
	
	private BlockPos getSamplePos(World world, ChunkPos chunkPos, int depositHeight) {
        int blockPosX = (chunkPos.x << 4) + random.nextInt(16);
        int blockPosZ = (chunkPos.z << 4) + random.nextInt(16);
        BlockPos searchPos = new BlockPos(blockPosX, 0, blockPosZ);
        while (searchPos.getY() < world.getHeight()) {
            if (world.getBlockState(searchPos.down()).isSideSolid(world, searchPos.down(), EnumFacing.UP)) {
                // If the current block is air
                if (canReplace(world, searchPos)) {
                    // If the block above this state is air,
                    if (canReplace(world, searchPos.up())) {
                        // If it's above sea level it's fine
                        if (searchPos.getY() > world.getSeaLevel()) {
                            return searchPos;
                        }
                        // If not, it's gotta be at least 12 blocks away from it (i.e. below it) but at
                        // least above the deposit
                        else if (isWithinRange(world.getSeaLevel(), searchPos.getY(), 12)
                                && searchPos.getY() < depositHeight) {
                            return searchPos;
                        }
                    }
                }
            }
            searchPos = searchPos.up();
        }
        return world.getTopSolidOrLiquidBlock(searchPos);
    }
	
	private boolean canReplace(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        Material mat = state.getMaterial();
        return mat == Material.AIR || state.getBlock().isLeaves(state, world, pos)
                || state.getBlock().isFoliage(world, pos) || mat.isReplaceable();
    }

	private boolean isWithinRange(int posA, int posB, int range) {
        return (Math.abs(posA - posB) <= range);
    }
    
    public boolean canGenerateInChunk(World world, ChunkPos pos, int dimension) {
        // Return true if the dimension is -9999; the default ExU Mining Dim
        return dimension == -9999 || !OreAPI.getCurrentWorldDeposits().keySet()
                .contains(new OreAPI.ChunkPosSerializable(pos, dimension));
    }
    
    private int getSampleCount(int depositSize) {
        int count = depositSize / 4;

        // Normalize maximum sample counts
        if (count > 4) {
            count = 4;
        }

        return count;
    }
  
}
