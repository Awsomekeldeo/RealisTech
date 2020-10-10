package awsome.realistech.tileentity;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.realistech.api.recipe.AnvilRecipe;
import awsome.realistech.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class AnvilTileEntity extends TileEntity {
	
	private ItemStackHandler itemHandler = createHandler();
	public int hitCount = 0;
	private IRecipeType<AnvilRecipe> recipeType;
	
	public AnvilTileEntity() {
		super(Registration.ANVIL_TILEENTITY.get());
		this.recipeType = AnvilRecipe.ANVIL_RECIPE;
	}
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	private ItemStackHandler createHandler() {
		
		return new ItemStackHandler(2) {
			
			@Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            	world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.NOTIFY_NEIGHBORS);
                return super.insertItem(slot, stack, simulate);
            }
            
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
            	world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.NOTIFY_NEIGHBORS);
            	return super.extractItem(slot, amount, simulate);
            }
            
		};
	}
	
	public Optional<AnvilRecipe> findRecipe(ItemStack item1, ItemStack item2) {
		NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY);
		for (int i = 0; i < this.itemHandler.getSlots(); i++) {
			inventory.set(i, this.itemHandler.getStackInSlot(i));
		}
		ItemStack stack = this.itemHandler.getStackInSlot(0);
		ItemStack stack1 = this.itemHandler.getStackInSlot(1);
		IItemHandlerModifiable recipeItemHandler = new ItemStackHandler(2);
		recipeItemHandler.setStackInSlot(0, stack);
		recipeItemHandler.setStackInSlot(1, stack1);
		RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
		return inventory.stream().allMatch(ItemStack::isEmpty) ? Optional.empty() : this.world.getRecipeManager().getRecipe(this.recipeType, recipeWrapper, this.world);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		itemHandler.deserializeNBT(compound.getCompound("inventory"));
		this.hitCount = compound.getInt("hits");
		super.read(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.putInt("hits", this.hitCount);
		return super.write(compound);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
	}
	
	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		this.read(tag);
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT compoundNBT = new CompoundNBT();
		write(compoundNBT);
		return compoundNBT;
	}
	
	@Override
	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT compoundNBT = new CompoundNBT();
		write(compoundNBT);
		return new SUpdateTileEntityPacket(this.pos, 42, compoundNBT);
	}
}
