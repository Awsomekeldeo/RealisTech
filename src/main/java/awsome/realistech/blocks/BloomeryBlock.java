package awsome.realistech.blocks;

import awsome.realistech.inventory.container.BloomeryContainer;
import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.BloomeryTileEntity;
import awsome.realistech.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

public class BloomeryBlock extends Block {
	
	private static final VoxelShape SHAPE_NORTH = makeCuboidShape(0, 0, 0, 16, 16, 2);
	private static final VoxelShape SHAPE_SOUTH = MathUtil.rotateShape(Direction.NORTH, Direction.SOUTH, SHAPE_NORTH);
	private static final VoxelShape SHAPE_EAST = MathUtil.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_NORTH);
	private static final VoxelShape SHAPE_WEST = MathUtil.rotateShape(Direction.NORTH, Direction.WEST, SHAPE_NORTH);
	
	public BloomeryBlock() {
		super(Block.Properties.create(Material.IRON, MaterialColor.ADOBE)
				.hardnessAndResistance(1.25f, 3.5f)
				.sound(SoundType.METAL)
				.harvestLevel(0)
				.harvestTool(ToolType.PICKAXE)
				.lightValue(13)
				.notSolid()
		);
		setDefaultState(this.stateContainer.getBaseState().with(BlockStateProperties.LIT, false).with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
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
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new BloomeryTileEntity();
	}
	
	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.get(BlockStateProperties.LIT) ? state.getLightValue() : 0;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.LIT);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof BloomeryTileEntity) {
				
				if (Registration.BLOOMERY_MULTIBLOCK.get().checkStructureValid(worldIn, pos, hit.getFace())) {
					((BloomeryTileEntity) te).multiBlockFormed = true;
				}else{
					((BloomeryTileEntity) te).multiBlockFormed = false;
				}
				
				INamedContainerProvider containerProvider = new INamedContainerProvider() {
					
					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new BloomeryContainer(i, worldIn, pos, playerInv, player, ((BloomeryTileEntity) te).multiBlockFormed);
					}
					
					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.realistech.bloomery");
					}
				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, (buf) -> {
					buf.writeBlockPos(te.getPos());
					buf.writeBoolean(((BloomeryTileEntity) te).multiBlockFormed);
				});
			}else{
				throw new IllegalStateException("The tile entity's state provider is missing.");
			}
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
			worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for (int i = 0; i < h.getSlots(); i++) {
					spawnAsEntity(worldIn, pos, h.getStackInSlot(i));
				}
			});
			worldIn.removeTileEntity(pos);
		}
	}
	
	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		if (!world.isRemote()) {
			TileEntity te = world.getTileEntity(pos);
			
			if (te instanceof BloomeryTileEntity) {
				if (Registration.BLOOMERY_MULTIBLOCK.get().checkStructureValid((World)world, pos, state.get(BlockStateProperties.HORIZONTAL_FACING))) {
					((BloomeryTileEntity) te).multiBlockFormed = true;
				}else{
					((BloomeryTileEntity) te).multiBlockFormed = false;
				}
			}
		}
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
