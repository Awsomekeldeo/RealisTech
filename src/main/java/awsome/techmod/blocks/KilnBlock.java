package awsome.techmod.blocks;

import awsome.techmod.tileentity.KilnTileEntity;
import awsome.techmod.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class KilnBlock extends Block {
	
	private static final VoxelShape SHAPE_NORTH = VoxelShapes.combineAndSimplify(
			VoxelShapes.or(VoxelShapes.fullCube(), 
					makeCuboidShape(0, 16, 0, 16, 17, 16)
				), 
			VoxelShapes.or(
				makeCuboidShape(0, 0, 0, 5, 16, 1),
				makeCuboidShape(11, 0, 0, 16, 16, 1),
				makeCuboidShape(10, 5, 0, 11, 6, 1),
				makeCuboidShape(5, 5, 0, 6, 6, 1),
				makeCuboidShape(5, 6, 0, 11, 7, 1),
				makeCuboidShape(0, 12, 1, 1, 16, 16),
				makeCuboidShape(15, 12, 1, 16, 16, 16),
				makeCuboidShape(1, 12, 15, 15, 16, 16),
				makeCuboidShape(5, 12, 0, 6, 13, 1),
				makeCuboidShape(10, 12, 0, 11, 13, 1),
				makeCuboidShape(5, 13, 0, 11, 16, 1),
				makeCuboidShape(1, 14, 1, 2, 16, 15),
				makeCuboidShape(2, 15, 1, 4, 16, 14),
				makeCuboidShape(2, 14, 14, 15, 16, 15),
				makeCuboidShape(14, 14, 1, 15, 16, 14),
				makeCuboidShape(12, 15, 1, 14, 16, 14),
				makeCuboidShape(4, 15, 13, 12, 16, 14),
				makeCuboidShape(1, 1, 2, 15, 7, 15),
				makeCuboidShape(6, 1, 0, 10, 5, 2),
				makeCuboidShape(1, 8, 2, 15, 12, 15),
				makeCuboidShape(2, 12, 2, 14, 14, 14),
				makeCuboidShape(4, 14, 2, 12, 15, 13),
				makeCuboidShape(6, 15, 5, 10, 17, 9),
				makeCuboidShape(6, 8, 0, 10, 12, 2),
				makeCuboidShape(5, 16, 0, 16, 17, 4),
				makeCuboidShape(0, 16, 0, 5, 17, 16),
				makeCuboidShape(11, 16, 4, 16, 17, 16),
				makeCuboidShape(5, 16, 10, 11, 17, 16)
			), IBooleanFunction.ONLY_FIRST);
	
	private static final VoxelShape SHAPE_SOUTH = MathUtil.rotateShape(Direction.NORTH, Direction.SOUTH, SHAPE_NORTH);
	private static final VoxelShape SHAPE_EAST = MathUtil.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_NORTH);
	private static final VoxelShape SHAPE_WEST = MathUtil.rotateShape(Direction.NORTH, Direction.WEST, SHAPE_NORTH);
	
	public KilnBlock() {
		super(Properties.create(Material.ROCK, MaterialColor.ADOBE)
				.hardnessAndResistance(1.25f, 7.0f)
				.sound(SoundType.STONE)
				.notSolid()
			);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new KilnTileEntity();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.NORTH) {
			return SHAPE_NORTH;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST) {
			return SHAPE_EAST;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH) {
			return SHAPE_SOUTH;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST) {
			return SHAPE_WEST;
		}
		return SHAPE_NORTH;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.NORTH) {
			return SHAPE_NORTH;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.EAST) {
			return SHAPE_EAST;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.SOUTH) {
			return SHAPE_SOUTH;
		}
		
		if (state.get(BlockStateProperties.HORIZONTAL_FACING) == Direction.WEST) {
			return SHAPE_WEST;
		}
		return SHAPE_NORTH;
	}
}
