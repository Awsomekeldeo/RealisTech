package awsome.techmod.blocks;

import javax.annotation.Nullable;

import awsome.techmod.inventory.container.ContainerCrucible;
import awsome.techmod.tileentity.TECrucible;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockCrucible extends Block {
	
	private static final VoxelShape INSIDE = makeCuboidShape(4, 1, 4, 12, 12, 12);
	private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
		VoxelShapes.fullCube(),
		VoxelShapes.or(
			//Mask out front
			makeCuboidShape(0, 0, 0, 16, 16, 3),
			//Mask out back
			makeCuboidShape(0, 0, 13, 16, 16, 16),
			//Mask out right side
			makeCuboidShape(0, 0, 3, 3, 16, 13),
			//Mask out left side
			makeCuboidShape(13, 0, 3, 16, 16, 13),
			//Mask out top
			makeCuboidShape(3, 12, 3, 13, 16, 13),
			//Mask out inside
			INSIDE),
		IBooleanFunction.ONLY_FIRST);
	

	public BlockCrucible() {
		super(Properties.create(Material.ROCK, MaterialColor.ADOBE)
			.hardnessAndResistance(1.25f, 7.0f)
			.sound(SoundType.STONE)
			.notSolid()
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new TECrucible();
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if(!worldIn.isRemote) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TECrucible) {
				INamedContainerProvider containerProvider = new INamedContainerProvider() {
					
					@Override
					public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
						return new ContainerCrucible(i, worldIn, pos, playerInv, player);
					}
					
					@Override
					public ITextComponent getDisplayName() {
						return new TranslationTextComponent("container.techmod.crucible");
					}
				};
				NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, te.getPos());
			} else {
				throw new IllegalStateException("The tile entity's state provider is missing.");
			}
		}
		return ActionResultType.SUCCESS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos,
			ISelectionContext context) {
		return SHAPE;
	}
}
