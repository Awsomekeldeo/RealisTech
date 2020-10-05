package awsome.realistech.worldgen.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.datafixers.Dynamic;

import awsome.realistech.worldgen.SampleUtils;
import awsome.realistech.worldgen.api.BlockPosDim;
import awsome.realistech.worldgen.api.ChunkPosDim;
import awsome.realistech.worldgen.api.OreAPI;
import awsome.realistech.worldgen.api.deposit.ISurfaceDeposit;
import awsome.realistech.worldgen.capability.IWorldgenCapability;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutable;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SurfaceDepositFeature extends Feature<NoFeatureConfig> {

	public SurfaceDepositFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}
	
	/*Geolosys Worldgen Algorithm stuff
	 * https://github.com/oitsjustjose/Geolosys/blob/1.15/src/main/java/com/oitsjustjose/geolosys/common/world/feature/PlutonOreFeature.java
	 * Original source and credit goes to ohitsjustjose
	 */
	//Start
	
	//Modified version of PlutonOreFeature::postPlacement
	private void postPlacement(IWorld world, BlockPos startPos, ISurfaceDeposit dep)
    {
		IWorldgenCapability plutonCapability = world.getWorld().getCapability(OreAPI.REALISTECH_WORLDGEN_CAPABILITY)
                .orElse(null);
		if (plutonCapability != null)
        {

            // Random space between plutons as well
            plutonCapability.setSurfaceDepositGenerated(new ChunkPosDim(startPos.getX(), startPos.getZ(),
                    Utils.dimensionToString(world.getDimension())));

        }
		
    }
	
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
	@ParametersAreNonnullByDefault
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand,
			BlockPos pos, NoFeatureConfig config) {
		IWorldgenCapability worldgenCapability = worldIn.getWorld().getCapability(OreAPI.REALISTECH_WORLDGEN_CAPABILITY)
                .orElse(null);
        // Fill in pending Blocks when possible:
        worldgenCapability.getPendingBlocks().forEach((pPos, pState) -> {
            if (isInChunk(new ChunkPos(pos), pPos))
            {
                worldIn.setBlockState(pPos.getPos(), pState, 2 | 16);
                worldgenCapability.getPendingBlocks().remove(pPos);
            }
        });
        
        ISurfaceDeposit dep = OreAPI.plutonRegistry.pickSurfaceDeposit();
        ChunkPosDim chunkPosDim = new ChunkPosDim(pos,
                Objects.requireNonNull(worldIn.getDimension().getType().getRegistryName()).toString());
        
        if (worldgenCapability.hasSurfaceDepositGenerated(chunkPosDim))
        {
            return false;
        }
        
        if (dep == null)
        {
            return false;
        }
        
        if (rand.nextInt(100) > dep.getChance()) {
			return false;
		}
        
        if (worldIn.getBiome(pos).getDownfall() < 0.4f) {
			return false;
		}
        
        List<DimensionType> dimTypes = Arrays.stream(dep.getDimensionBlacklist()).parallel()
                .map(x -> DimensionType.byName(new ResourceLocation(x))).collect(Collectors.toList());
        
        if (dimTypes.contains(worldIn.getDimension().getType()))
        {
            return false;
        }
		
		boolean placed = generateDeposit(worldIn, pos, rand, worldgenCapability, dep);
		
		if (placed)
        {
            this.postPlacement(worldIn, pos, dep);
        }
		
		return placed;
	}
	
	//Modified version of PlutonOreFeature::generateLayer
	private boolean generateDeposit(IWorld worldIn, BlockPos pos, Random rand, IWorldgenCapability cap, ISurfaceDeposit deposit) {
		ChunkPos chunkPos = new ChunkPos(pos);
		boolean placed = false;
		
		int depth = (rand.nextInt(deposit.getMaxYSize() - 2) + 2);
		int x = ((chunkPos.getXStart() + chunkPos.getXEnd()) / 2) - rand.nextInt(8) + rand.nextInt(16);
		int y = pos.getY();
        int z = ((chunkPos.getZStart() + chunkPos.getZEnd()) / 2) - rand.nextInt(8) + rand.nextInt(16);
        
        //Check for surface block
        PooledMutable highestNonAirBlock = PooledMutable.retain();
        highestNonAirBlock.setPos(x, y, z);
        for (int i = y; i < 255; i++) {
        	if (worldIn.getBlockState(highestNonAirBlock) != Blocks.AIR.getDefaultState()) {
        		highestNonAirBlock.setPos(x, i, z);
        	}
        }
        
        int size = deposit.getSize();
        int indicatorsPlaced = 0;
        
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

                    BlockPos currentBlock = highestNonAirBlock.add(dX, dY, dZ);

                    if (isInChunk(chunkPos, currentBlock) || worldIn.chunkExists(x >> 4, z >> 4))
                    {
                        float density = 1;

                        if (rand.nextFloat() > density)
                        {
                            continue;
                        }
                        BlockState state = worldIn.getBlockState(currentBlock);
                        for (BlockState matcherState : (deposit.getSubsoilStateMatchers() == null
                                ? Utils.getDefaultMatchers()
                                : deposit.getSubsoilStateMatchers()))
                        {
                            if (Utils.doStatesMatch(matcherState, state))
                            {
                                worldIn.setBlockState(currentBlock, deposit.getSubsoil(), 2 | 16);
                                placed = true;

                                break;
                            }
                        }
                        
                        for (BlockState matcherState : (deposit.getTopsoilStateMatchers() == null
                                ? Utils.getDefaultMatchers()
                                : deposit.getTopsoilStateMatchers()))
                        {
                            if (Utils.doStatesMatch(matcherState, state))
                            {
                                worldIn.setBlockState(currentBlock, deposit.getTopsoil(), 2 | 16);
                                //Randomly place samples above the deposit
                                if (indicatorsPlaced > 0 && indicatorsPlaced < 10) {
                                	if (rand.nextInt(100) <= 5) {
                                    	BlockPos sampleBlock = currentBlock.up();
                                    	if (SampleUtils.canPlaceOn(worldIn, sampleBlock)) {
                                    		worldIn.setBlockState(sampleBlock, deposit.getIndicator(), 2 | 16);
                                    	}
                                    	currentBlock.down();
                                    	indicatorsPlaced++;
                                    }
                                }else if (indicatorsPlaced == 0) {
                                	BlockPos sampleBlock = currentBlock.up();
                                	if (SampleUtils.canPlaceOn(worldIn, sampleBlock)) {
                                		worldIn.setBlockState(sampleBlock, deposit.getIndicator(), 2 | 16);
                                	}
                                	indicatorsPlaced++;
                                	currentBlock.down();
                                }
                                placed = true;

                                break;
                            }
                        }
                    }
                    else
                    {
                        cap.putPendingBlock(
                                new BlockPosDim(pos, Utils.dimensionToString(worldIn.getDimension())), deposit.getSubsoil());
                    }
                }
            }
        }
        
        return placed;
	}
}
