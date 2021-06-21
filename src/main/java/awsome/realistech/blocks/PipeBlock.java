package awsome.realistech.blocks;

import awsome.realistech.data.tags.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ToolType;

public class PipeBlock extends Block {
	
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	
	public PipeBlock() {
		super(Properties.create(Material.IRON)
				.hardnessAndResistance(1.25f, 3.0f)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(0)
				.notSolid());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(UP);
		builder.add(DOWN);
		builder.add(NORTH);
		builder.add(EAST);
		builder.add(SOUTH);
		builder.add(WEST);
	}
	
	public boolean canConnect(BlockState state, Direction direction) {
		Block block = state.getBlock();
		boolean isPipe = block.isIn(ModTags.Blocks.PIPES);
		return !cannotAttach(block) || isPipe;
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IBlockReader blockReader = context.getWorld();
		BlockPos pos = context.getPos();
		BlockPos north = pos.north();
		BlockPos east = pos.east();
		BlockPos south = pos.south();
		BlockPos west = pos.west();
		BlockPos up = pos.up();
		BlockPos down = pos.down();
		BlockState northState = blockReader.getBlockState(north);
		BlockState southState = blockReader.getBlockState(south);
		BlockState eastState = blockReader.getBlockState(east);
		BlockState westState = blockReader.getBlockState(west);
		BlockState upState = blockReader.getBlockState(up);
		BlockState downState = blockReader.getBlockState(down);
		
		return super.getStateForPlacement(context)
				.with(UP, Boolean.valueOf(this.canConnect(upState, Direction.UP)))
				.with(DOWN, Boolean.valueOf(this.canConnect(downState, Direction.DOWN)))
				.with(NORTH, Boolean.valueOf(this.canConnect(northState, Direction.NORTH)))
				.with(SOUTH, Boolean.valueOf(this.canConnect(southState, Direction.SOUTH)))
				.with(EAST, Boolean.valueOf(this.canConnect(eastState, Direction.EAST)))
				.with(WEST, Boolean.valueOf(this.canConnect(westState, Direction.WEST)));
	}
	
	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
			BlockPos currentPos, BlockPos facingPos) {
		return stateIn.with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(this.canConnect(stateIn, facing.getOpposite())));
	}

}
