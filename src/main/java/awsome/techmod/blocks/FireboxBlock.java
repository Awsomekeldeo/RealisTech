package awsome.techmod.blocks;

import awsome.techmod.inventory.container.FireboxContainer;
import awsome.techmod.tileentity.FireboxTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;

public class FireboxBlock extends Block {

	public FireboxBlock() {
		super(Properties.create(Material.ROCK)
				.sound(SoundType.METAL)
				.hardnessAndResistance(1.25f)
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new FireboxTileEntity();
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
			if (te instanceof FireboxTileEntity) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {
					
					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new FireboxContainer(i, worldIn, pos, playerInv, player);
					}
					
					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.techmod.firebox");
					}
				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, te.getPos());
			}else {
				throw new IllegalStateException("The tile entity's state provider is missing.");
			}
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
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
