package awsome.realistech.multiblock;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import awsome.realistech.Realistech;
import awsome.realistech.blocks.BellowsBlock;
import awsome.realistech.util.MathUtil;
import awsome.realistech.util.MultiblockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.registries.ForgeRegistryEntry;

/* Modified version of Immersive Engineering's TemplateMultiblock:
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.14/src/main/java/blusunrize/immersiveengineering/common/blocks/multiblocks/IETemplateMultiblock.java
 * Original source and credit goes to Blusunrize & the IE team.
 */
public class Multiblock extends ForgeRegistryEntry<Multiblock> {
	
	private final ResourceLocation structureLocation;
	private boolean canMirror;
	private BlockPos checkOrigin;
	
	@Nullable
	private Template structureTemplate;
	
	public Multiblock(ResourceLocation path, boolean canMirror, BlockPos checkOrigin) {
		this.structureLocation = path;
		this.canMirror = canMirror;
		this.checkOrigin = checkOrigin;
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	public Template getStructureTemplate() {
		if (structureTemplate == null) {
			try {
				structureTemplate = MultiblockUtil.loadStaticStructureTemplate(structureLocation);
				List<Template.BlockInfo> structureBlocks = structureTemplate.blocks.get(0);
				for (int i = 0; i < structureBlocks.size(); i++) {
					Template.BlockInfo blockInfo = structureBlocks.get(i);
					if (blockInfo.state == Blocks.AIR.getDefaultState()) {
						structureBlocks.remove(i);
						i--;
					}else if (blockInfo.state.isAir()) {
						Realistech.LOGGER.info("Found a non-default air block in multiblock structure" + structureLocation);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return structureTemplate;
	}
	
	public boolean checkStructureValid(World world, BlockPos pos, Direction side) {
		Rotation rotation = MathUtil.getRotationBetweenFacings(Direction.NORTH, side);
		
		if (rotation == null) {
			return false;
		}
		
		Template template = getStructureTemplate();
		
		loopMirrors:
		for (Mirror mirror : canMirror ? ImmutableList.of(Mirror.NONE, Mirror.FRONT_BACK) : ImmutableList.of(Mirror.NONE)) {
			PlacementSettings settings = new PlacementSettings().setMirror(mirror).setRotation(rotation);
			BlockPos origin = pos.subtract(Template.transformedBlockPos(settings, checkOrigin));
			
			for (Template.BlockInfo blockInfo : template.blocks.get(0)) {
				BlockPos relativePos = Template.transformedBlockPos(settings, blockInfo.pos);
				BlockPos currentBlock = origin.add(relativePos);
				
				BlockState structureState = blockInfo.state.mirror(mirror).rotate(rotation);
				BlockState worldState = world.getBlockState(currentBlock);
				
				if (worldState.has(BellowsBlock.STATIC)) {
					worldState = worldState.with(BellowsBlock.STATIC, true);
				}
				
				if (worldState.has(BlockStateProperties.LIT)) {
					worldState = worldState.with(BlockStateProperties.LIT, false);
				}
				
				if (!worldState.equals(structureState)) {
					continue loopMirrors;
				}
			}
			
			return true;
		}
		return false;
	}

}
