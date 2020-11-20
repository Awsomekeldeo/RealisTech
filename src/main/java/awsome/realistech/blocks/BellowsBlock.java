package awsome.realistech.blocks;

import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.BellowsTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.util.Constants;

public class BellowsBlock extends Block {
	
	public static final BooleanProperty STATIC = BooleanProperty.create("static");

	public BellowsBlock() {
		super(Properties.create(Material.WOOD, MaterialColor.WOOD)
				.hardnessAndResistance(2.0f, 3.0f)
				.harvestTool(ToolType.AXE)
				.harvestLevel(0)
				.sound(SoundType.WOOD)
				.notSolid()
			);
		setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
	}
	
	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1.0f;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BellowsTileEntity();
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, STATIC);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing());
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		if (state.get(STATIC)) {
			return BlockRenderType.MODEL;
		}
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (state.get(STATIC)) {
			worldIn.setBlockState(pos, state.with(STATIC, false), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof BellowsTileEntity) {
				((BellowsTileEntity) te).setShouldAnimate(true);
				worldIn.playSound(player, pos, Registration.BLOCK_BELLOWS_CYCLE_SOUND.get(), SoundCategory.BLOCKS, 1.0f, 1.0f);
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(BlockStateProperties.HORIZONTAL_FACING, rot.rotate(state.get(BlockStateProperties.HORIZONTAL_FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.toRotation(state.get(BlockStateProperties.HORIZONTAL_FACING)));
	}
}
