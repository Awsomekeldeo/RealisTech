package awsome.realistech.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import awsome.realistech.api.recipe.AnvilRecipe;
import awsome.realistech.items.HammerItem;
import awsome.realistech.tileentity.AnvilTileEntity;
import awsome.realistech.util.GeneralUtils;
import awsome.realistech.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class AnvilBlock extends Block {
	
	public static final List<AnvilBlock> ANVILS = new ArrayList<>();
	
	public static final VoxelShape SHAPE_NORTH = VoxelShapes.or(
				makeCuboidShape(2, 0, 4, 14, 3, 12), 
				makeCuboidShape(4, 3, 5, 12, 6, 11),
				makeCuboidShape(5, 6, 6, 11, 8, 10),
				makeCuboidShape(0, 8, 6, 16, 14, 10),
				makeCuboidShape(1, 8, 5, 16, 14, 6),
				makeCuboidShape(1, 8, 10, 16, 14, 11),
				makeCuboidShape(3, 8, 4, 16, 14, 5),
				makeCuboidShape(3, 8, 11, 16, 14, 12)
			);
	
	public static final VoxelShape SHAPE_SOUTH = MathUtil.rotateShape(Direction.NORTH, Direction.SOUTH, SHAPE_NORTH);
	public static final VoxelShape SHAPE_EAST = MathUtil.rotateShape(Direction.NORTH, Direction.EAST, SHAPE_NORTH);
	public static final VoxelShape SHAPE_WEST = MathUtil.rotateShape(Direction.NORTH, Direction.WEST, SHAPE_NORTH);

	public AnvilBlock(int harvestLevel, float hardnessIn, float resistanceIn) {
		super(Properties.create(Material.ROCK)
				.hardnessAndResistance(hardnessIn, resistanceIn)
				.notSolid()
				.harvestLevel(harvestLevel)
				.sound(SoundType.STONE));
		ANVILS.add(this);
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new AnvilTileEntity();
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof AnvilTileEntity) {
				AnvilTileEntity anvilTe = (AnvilTileEntity)te;
				ItemStack stack = player.getHeldItem(handIn);
				te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					if (!player.isSneaking()) {
						if (!(player.getHeldItemMainhand().getItem() instanceof HammerItem)) {
							if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
								if ((hit.getHitVec().getX() - hit.getPos().getX()) > 0.5) {
									player.setHeldItem(handIn, h.insertItem(0, stack, false));
								}else{
									player.setHeldItem(handIn, h.insertItem(1, stack, false));
								}
							}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
								if ((hit.getHitVec().getX() - hit.getPos().getX()) < 0.5) {
									player.setHeldItem(handIn, h.insertItem(0, stack, false));
								}else{
									player.setHeldItem(handIn, h.insertItem(1, stack, false));
								}
							}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
								if ((hit.getHitVec().getZ() - hit.getPos().getZ()) > 0.5) {
									player.setHeldItem(handIn, h.insertItem(0, stack, false));
								}else{
									player.setHeldItem(handIn, h.insertItem(1, stack, false));
								}
							}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
								if ((hit.getHitVec().getZ() - hit.getPos().getZ()) < 0.5) {
									player.setHeldItem(handIn, h.insertItem(0, stack, false));
								}else{
									player.setHeldItem(handIn, h.insertItem(1, stack, false));
								}
							}
						}else{
							Optional<AnvilRecipe> optional = anvilTe.findRecipe(h.getStackInSlot(0), h.getStackInSlot(1));
							if (optional.isPresent()) {
								AnvilRecipe recipe = optional.get();
								anvilTe.hitCount++;
								player.getHeldItemMainhand().attemptDamageItem(3, GeneralUtils.RAND, null);
								worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);
								if (anvilTe.hitCount == 3) {
									anvilTe.hitCount = 0;
									ItemHandlerHelper.giveItemToPlayer(player, recipe.getRecipeOutput());
									h.extractItem(0, 1, false);
									h.extractItem(1, 1, false);
								}
							}
						}
					}else{
						if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
							if ((hit.getHitVec().getX() - hit.getPos().getX()) > 0.5) {
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(0, h.getStackInSlot(0).getCount(), false));
							}else{
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(1, h.getStackInSlot(1).getCount(), false));
							}
						}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
							if ((hit.getHitVec().getX() - hit.getPos().getX()) < 0.5) {
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(0, h.getStackInSlot(0).getCount(), false));
							}else{
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(1, h.getStackInSlot(1).getCount(), false));
							}
						}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
							if ((hit.getHitVec().getZ() - hit.getPos().getZ()) > 0.5) {
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(0, h.getStackInSlot(0).getCount(), false));
							}else{
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(1, h.getStackInSlot(1).getCount(), false));
							}
						}else if (state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
							if ((hit.getHitVec().getZ() - hit.getPos().getZ()) < 0.5) {
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(0, h.getStackInSlot(0).getCount(), false));
							}else{
								ItemHandlerHelper.giveItemToPlayer(player, h.extractItem(1, h.getStackInSlot(1).getCount(), false));
							}
						}
					}
				});
			}
		}
		
		return ActionResultType.SUCCESS;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
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
	
	public static List<AnvilBlock> getAnvilList() {
		return Collections.unmodifiableList(ANVILS);
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
			worldIn.getTileEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				for (int i = 0; i < h.getSlots(); i++) {
					spawnAsEntity(worldIn, pos, h.getStackInSlot(i));
				}
			});
		}
		worldIn.removeTileEntity(pos);
	}
}
