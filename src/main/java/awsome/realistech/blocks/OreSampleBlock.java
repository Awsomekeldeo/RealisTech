package awsome.realistech.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class OreSampleBlock extends Block {
	
	public static final List<OreSampleBlock> SAMPLE_LIST = new ArrayList<>();
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final VoxelShape SHAPE = 
			VoxelShapes.or(
				makeCuboidShape(4, 0, 7, 12, 1, 11),
				makeCuboidShape(5, 0, 6, 11, 1, 7),
				makeCuboidShape(7, 0, 5, 10, 1, 6),
				makeCuboidShape(5, 0, 11, 11, 1, 12),
				makeCuboidShape(6, 0, 12, 9, 1, 13),
				makeCuboidShape(5, 1, 7, 11, 2, 11),
				makeCuboidShape(6, 1, 6, 10, 2, 7),
				makeCuboidShape(6, 1, 10, 10, 2, 12)
			);
	
	public OreSampleBlock(int harvestLevel) {
		super(Properties.create(Material.ROCK)
				.sound(SoundType.GROUND)
				.doesNotBlockMovement()
				.notSolid()
				.hardnessAndResistance(1.0f)
				.harvestLevel(harvestLevel)
				.harvestTool(ToolType.PICKAXE)
			);
		this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
		SAMPLE_LIST.add(this);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        return Block.hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
    }
    
    public static List<OreSampleBlock> getSampleList() {
		return Collections.unmodifiableList(SAMPLE_LIST);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		if (context.getWorld().getBlockState(context.getPos()).getBlock() == Blocks.WATER)
        {
            return this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
        }
        return this.getDefaultState();
	}
	
	@Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public IFluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
	
    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
            boolean isMoving)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (!this.isValidPosition(state, worldIn, pos))
        {
            worldIn.destroyBlock(pos, true);
        }
        // Update the water from flowing to still or vice-versa
        else if (state.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
    }

    public BlockState asWaterlogged()
    {
        return this.getDefaultState().with(WATERLOGGED, Boolean.TRUE);
    }
}
