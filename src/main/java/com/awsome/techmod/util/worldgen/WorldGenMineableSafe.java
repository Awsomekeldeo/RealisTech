package com.awsome.techmod.util.worldgen;

import java.util.Random;

import com.awsome.techmod.api.worldgen.IOre;
import com.awsome.techmod.api.worldgen.OreAPI;
import com.awsome.techmod.util.Utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/*	Using Geolosys's worldgen code:
 * 	https://github.com/oitsjustjose/Geolosys/src/main/java/com/oitsjustjose/geolosys/common/world/WorldGenMinableSafe.java
 * 	Original source & credit goes to oitsjustjose
 */	

public class WorldGenMineableSafe extends WorldGenerator {
	private IOre ore;
	private String dataName;
	
	public WorldGenMineableSafe(IOre ore, String dataName) {
        this.ore = ore;
        this.dataName = dataName;
    }
	
	private boolean isInChunk(ChunkPos chunkPos, BlockPos pos) {
        int blockX = pos.getX();
        int blockZ = pos.getZ();
        return blockX >= chunkPos.getXStart() && blockX <= chunkPos.getXEnd() && blockZ >= chunkPos.getZStart()
                && blockZ <= chunkPos.getZEnd();
    }

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		float f = rand.nextFloat() * (float) Math.PI;
        double d0 = (double) ((float) (position.getX() + 8) + MathHelper.sin(f) * (float) this.ore.getSize() / 8.0F);
        double d1 = (double) ((float) (position.getX() + 8) - MathHelper.sin(f) * (float) this.ore.getSize() / 8.0F);
        double d2 = (double) ((float) (position.getZ() + 8) + MathHelper.cos(f) * (float) this.ore.getSize() / 8.0F);
        double d3 = (double) ((float) (position.getZ() + 8) - MathHelper.cos(f) * (float) this.ore.getSize() / 8.0F);
        double d4 = (double) (position.getY() + rand.nextInt(3) - 2);
        double d5 = (double) (position.getY() + rand.nextInt(3) - 2);
        
        PendingBlocks pendingBlocks = PendingBlocks.getForWorld(worldIn, dataName);
        ChunkPos thisChunk = new ChunkPos(position);
        boolean placedOre = false;
        
        for (int i = 0; i < this.ore.getSize(); ++i) {
            float f1 = (float) i / (float) this.ore.getSize();
            double d6 = d0 + (d1 - d0) * (double) f1;
            double d7 = d4 + (d5 - d4) * (double) f1;
            double d8 = d2 + (d3 - d2) * (double) f1;
            double d9 = rand.nextDouble() * (double) this.ore.getSize() / 16.0D;
            double d10 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double) (MathHelper.sin((float) Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor(d6 - d10 / 2.0D);
            int k = MathHelper.floor(d7 - d11 / 2.0D);
            int l = MathHelper.floor(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1) {
                double d12 = ((double) l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D) {
                    for (int i2 = k; i2 <= j1; ++i2) {
                        double d13 = ((double) i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D) {
                            for (int j2 = l; j2 <= k1; ++j2) {
                                double d14 = ((double) j2 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                                    BlockPos blockpos = new BlockPos(l1, i2, j2);

                                    float density = this.ore.getDensity() > 1.0F ? 1.0F : this.ore.getDensity();

                                    if (rand.nextFloat() > density) {
                                        continue;
                                    }

                                    if (isInChunk(thisChunk, blockpos)
                                            || worldIn.isChunkGeneratedAt(l1 >> 4, j2 >> 4)) {

                                        IBlockState state = worldIn.getBlockState(blockpos);
                                        if (state != null) {
                                            // If it has custom blockstate matcher:
                                            if (this.ore.getBlockStateMatchers() != null) {
                                                for (IBlockState iBlockState : this.ore.getBlockStateMatchers()) {
                                                    if (Utils.doStatesMatch(iBlockState, state)) {
                                                        worldIn.setBlockState(blockpos, this.ore.getOre(), 2 | 16);
                                                        placedOre = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            // Otherwise just use the default
                                            else {
                                                for (IBlockState iBlockState : OreAPI.replacementMats) {
                                                    if (Utils.doStatesMatch(iBlockState, state)) {
                                                        worldIn.setBlockState(blockpos, this.ore.getOre(), 2 | 16);
                                                        placedOre = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        pendingBlocks.storePending(blockpos, this.ore.getOre());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return placedOre;
	}
	
}
