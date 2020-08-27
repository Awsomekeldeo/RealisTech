package awsome.realistech.worldgen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import awsome.realistech.worldgen.api.BlockPosDim;
import awsome.realistech.worldgen.api.OreAPI;
import awsome.realistech.worldgen.capability.IWorldgenCapability;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;

public class ClayDepositFeature extends Feature<ClayDepositConfig> {

	public ClayDepositFeature(Function<Dynamic<?>, ? extends ClayDepositConfig> configFactoryIn) {
		super(configFactoryIn);
	}
	
	/*Geolosys Worldgen Algorithm stuff
	 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/feature/PlutonOreFeature.java
	 * Original source and credit goes to ohitsjustjose
	 */
	//Start
	private boolean isInChunk(ChunkPos chunkPos, BlockPos pos)
    {
        int blockX = pos.getX();
        int blockZ = pos.getZ();
        return blockX >= chunkPos.getXStart() && blockX <= chunkPos.getXEnd() && blockZ >= chunkPos.getZStart()
                && blockZ <= chunkPos.getZEnd();
    }

    private boolean isInChunk(ChunkPos chunkPos, BlockPosDim pos)
    {
        int blockX = pos.getX();
        int blockZ = pos.getZ();
        return blockX >= chunkPos.getXStart() && blockX <= chunkPos.getXEnd() && blockZ >= chunkPos.getZStart()
                && blockZ <= chunkPos.getZEnd();
    }
    
    //Modified Version of PlutonOreFeature::place
	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
			BlockPos pos, ClayDepositConfig config) {
		IWorldgenCapability worldgenCapability = worldIn.getWorld().getCapability(OreAPI.TECHMOD_WORLDGEN_CAPABILITY)
                .orElse(null);
        // Fill in pending Blocks when possible:
        worldgenCapability.getPendingBlocks().forEach((pPos, pState) -> {
            if (isInChunk(new ChunkPos(pos), pPos))
            {
                worldIn.setBlockState(pPos.getPos(), pState, 2 | 16);
                worldgenCapability.getPendingBlocks().remove(pPos);
            }
        });
		if (rand.nextInt(100) > 35) {
			return false;
		}
		if (worldIn.getBiome(pos).getCategory() != Biome.Category.PLAINS) {
			return false;
		}
		
		boolean placed = generateDeposit(worldIn, pos, rand, config, worldgenCapability);
		return placed;
	}
	
	//Modified version of PlutonOreFeature::generateLayer
	private boolean generateDeposit(IWorld worldIn, BlockPos pos, Random rand, ClayDepositConfig config, IWorldgenCapability cap) {
		ChunkPos chunkPos = new ChunkPos(pos);
		boolean placed = false;
		
		int x = ((chunkPos.getXStart() + chunkPos.getXEnd()) / 2) - rand.nextInt(8) + rand.nextInt(16);
		int y = pos.getY();
        int z = ((chunkPos.getZStart() + chunkPos.getZEnd()) / 2) - rand.nextInt(8) + rand.nextInt(16);
        
        BlockPos blockPos = new BlockPos(x, y, z);
        
        int size = config.size;
        int depth = rand.nextInt(config.maxYSize - 2) + 2;
        
        for (int dX = -size; dX <= size; dX++)
        {
            for (int dZ = -size; dZ <= size; dZ++)
            {
                for (int dY = 0; dY < depth; dY++)
                {
                    float dist = (dX * dX) + (dZ * dZ);
                    if (dist > size)
                    {
                        continue;
                    }

                    BlockPos currentBlock = blockPos.add(dX, dY, dZ);

                    if (isInChunk(chunkPos, currentBlock) || worldIn.chunkExists(x >> 4, z >> 4))
                    {
                        float density = 1;

                        if (rand.nextFloat() > density)
                        {
                            continue;
                        }
                        BlockState state = worldIn.getBlockState(currentBlock);
                        for (BlockState matcherState : (config.replaceStates == null
                                ? Utils.getDefaultMatchers()
                                : config.replaceStates))
                        {
                            if (Utils.doStatesMatch(matcherState, state))
                            {
                                worldIn.setBlockState(currentBlock, config.state, 2 | 16);
                                placed = true;

                                break;
                            }
                        }
                    }
                    else
                    {
                        cap.putPendingBlock(
                                new BlockPosDim(pos, Utils.dimensionToString(worldIn.getDimension())), config.state);
                    }
                }
            }
        }
        
        return placed;
	}
}
