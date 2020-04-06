package com.awsome.testmod.util.worldgen;

import java.util.Random;

import com.awsome.techmod.api.ModBlocks;
import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class OreGen implements IWorldGenerator{
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {	
		switch(world.provider.getDimension()) {
		case 0:
			this.runGenerator(ModBlocks.copperOre.getDefaultState(), 8, 8, 40, 75, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.tinOre.getDefaultState(), 8, 7, 20, 55, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.silverOre.getDefaultState(), 8, 3, 10, 35, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.leadOre.getDefaultState(), 8, 4, 10, 35, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.zincOre.getDefaultState(), 8, 8, 40, 75, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.nickelOre.getDefaultState(), 8, 4, 0, 29, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
			this.runGenerator(ModBlocks.cobaltOre.getDefaultState(), 8, 3, 10, 35, BlockMatcher.forBlock(Blocks.STONE), world, random, chunkX, chunkZ);
		}
	}
	
	private void runGenerator(IBlockState blockToGen, int blockAmount,  int chancesToSpawn, int minHeight, int maxHeight, Predicate<IBlockState> blockToReplace, World world, Random rand, int chunk_X, int chunk_Z){
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");

		WorldGenMinable generator = new WorldGenMinable(blockToGen, blockAmount, blockToReplace);
		int heightdiff = maxHeight - minHeight +1;
		for (int i=0; i<chancesToSpawn; i++){
			int x = chunk_X * 16 +rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightdiff);
			int z = chunk_Z * 16 + rand.nextInt(16);

			generator.generate(world, rand, new BlockPos(x, y, z));
		}
	}
	

}
