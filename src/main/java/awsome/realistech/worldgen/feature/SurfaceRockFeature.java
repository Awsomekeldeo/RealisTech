package awsome.realistech.worldgen.feature;

import java.util.Objects;
import java.util.Random;
import java.util.function.Function;

import javax.annotation.ParametersAreNonnullByDefault;

import com.mojang.datafixers.Dynamic;

import awsome.realistech.registry.Registration;
import awsome.realistech.worldgen.SampleUtils;
import awsome.realistech.worldgen.api.BlockPosDim;
import awsome.realistech.worldgen.api.ChunkPosDim;
import awsome.realistech.worldgen.api.OreAPI;
import awsome.realistech.worldgen.capability.IWorldgenCapability;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SurfaceRockFeature extends Feature<NoFeatureConfig> {

	public SurfaceRockFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}
	
	private boolean isInChunk(ChunkPos chunkPos, BlockPosDim pos)
    {
        int blockX = pos.getX();
        int blockZ = pos.getZ();
        return blockX >= chunkPos.getXStart() && blockX <= chunkPos.getXEnd() && blockZ >= chunkPos.getZStart()
                && blockZ <= chunkPos.getZEnd();
    }
    
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
        
        ChunkPosDim chunkPosDim = new ChunkPosDim(pos,
                Objects.requireNonNull(worldIn.getDimension().getType().getRegistryName()).toString());
        
        if (worldgenCapability.hasSurfaceDepositGenerated(chunkPosDim) || worldgenCapability.hasOrePlutonGenerated(chunkPosDim))
        {
            return false;
        }
        
        if (rand.nextInt(100) > 35) {
			return false;
		}
        
        if (worldIn.getDimension().getType() != DimensionType.OVERWORLD) {
        	return false;
        }
		
		boolean placed = false;
		
		if (worldIn.getWorld().getWorldType() != WorldType.FLAT)
        {
            int sampleLimit = rand.nextInt(3) + 1;
            for (int i = 0; i < sampleLimit; i++)
            {
                BlockPos samplePos = SampleUtils.getRockPosition(worldIn, new ChunkPos(pos));
                if (samplePos == null || SampleUtils.inNonWaterFluid(worldIn, samplePos))
                {
                    return false;
                }
                if (worldIn.getBlockState(samplePos) != Registration.ROCK.get().getDefaultState())
                {
                    boolean isInWater = SampleUtils.isInWater(worldIn, samplePos);
                    if (isInWater)
                    {
                        return false;
                    }
                    else
                    {
                    	worldIn.setBlockState(samplePos, Registration.ROCK.get().getDefaultState(), 2 | 16);
                    	placed = true;
                    }
                }
            }
        }
		
		return placed;
	}
	
}
