package awsome.realistech.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import awsome.realistech.registry.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MediumHeatFurnaceTileEntity extends TileEntity implements ITickableTileEntity {
	
	private ItemStackHandler itemHandler = createHandler();
	private int burnTime;
	private int currentItemBurnTime;
	private int smeltProgress;
	private int totalSmeltTime = 200;
	protected final IRecipeType<WeakSmeltingRecipe> recipeType;
	
	public final IIntArray mediumHeatFurnaceData = new IIntArray() {
		
		@Override
		public int size() {
			return 4;
		}
		
		@Override
		public void set(int index, int value) {
			switch (index) {
				case 0:
					MediumHeatFurnaceTileEntity.this.burnTime = value;
					break;
				case 1:
					MediumHeatFurnaceTileEntity.this.currentItemBurnTime = value;
					break;
				case 2:
					MediumHeatFurnaceTileEntity.this.smeltProgress = value;
					break;
				case 3:
					MediumHeatFurnaceTileEntity.this.totalSmeltTime = value;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
				case 0:
					return MediumHeatFurnaceTileEntity.this.burnTime;
				case 1:
					return MediumHeatFurnaceTileEntity.this.currentItemBurnTime;
				case 2:
					return MediumHeatFurnaceTileEntity.this.smeltProgress;
				case 3:
					return MediumHeatFurnaceTileEntity.this.totalSmeltTime;
			}
			return index;
		}
		
	};
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	public MediumHeatFurnaceTileEntity() {
		super(Registration.WEAK_FURNACE_TILEENTITY.get());
		this.recipeType = WeakSmeltingRecipe.WEAK_SMELTING_RECIPE;
	}
	
	@Override
	public void tick() {
		boolean needsUpdating = false;
		boolean burning = this.isBurning();
		if (this.isBurning()) {
			this.burnTime--;
		}
		if (!this.world.isRemote) {
			ItemStack is = this.itemHandler.getStackInSlot(1);
			if (this.isBurning() || !is.isEmpty() && !this.itemHandler.getStackInSlot(0).isEmpty()) {
				ItemStack stack = this.itemHandler.getStackInSlot(0);
				IItemHandlerModifiable recipeItemHandler = new ItemStackHandler();
				recipeItemHandler.setStackInSlot(0, stack);
				RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
				IRecipe<?> weakSmeltingRecipe = this.world.getRecipeManager().getRecipe(this.recipeType, recipeWrapper, this.world).orElse(null);
				IRecipe<?> furnaceSmeltingRecipe = this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, recipeWrapper, this.world).orElse(null);
				if (!this.isBurning() && (this.canSmelt(weakSmeltingRecipe) || this.canSmelt(furnaceSmeltingRecipe))) {
					
					this.burnTime = ForgeHooks.getBurnTime(is);
					this.currentItemBurnTime = this.burnTime;
					
					if (this.isBurning()) {
						needsUpdating = true;
						if (is.hasContainerItem()) {
							this.itemHandler.setStackInSlot(1, is.getContainerItem());
						}else if (!is.isEmpty()){
							Item item = is.getItem();
							is.shrink(1);
							if (is.isEmpty()) {
								ItemStack stack1 = item.getContainerItem(is);
								this.itemHandler.setStackInSlot(1, stack1);
							}
						}
					}
				}
				
				if (this.isBurning() && this.canSmelt(weakSmeltingRecipe)) {
					++this.smeltProgress;
					if (this.smeltProgress == this.totalSmeltTime) {
						this.smeltProgress = 0;
						this.totalSmeltTime = 200;
						this.smelt(weakSmeltingRecipe);
						needsUpdating = true;
					}
				}else if (this.isBurning() && this.canSmelt(furnaceSmeltingRecipe)) {
					++this.smeltProgress;
					if (this.smeltProgress == this.totalSmeltTime) {
						this.smeltProgress = 0;
						this.totalSmeltTime = 200;
						this.smelt(furnaceSmeltingRecipe);
						needsUpdating = true;
					}
				}else{
					this.smeltProgress = 0;
				}
			}
			
			if (burning != this.isBurning())
            {
                needsUpdating = true;
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockStateProperties.LIT, Boolean.valueOf(this.isBurning())), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
            }
		}
		if (needsUpdating)
        {
            this.markDirty();
        }
	}
	
	private boolean canSmelt(@Nullable IRecipe<?> recipeIn) {
		if (!this.itemHandler.getStackInSlot(0).isEmpty() && recipeIn != null) {
			ItemStack output = recipeIn.getRecipeOutput();
			if (output.isEmpty()) {
				return false;
			}else{
				ItemStack stackInOutputSlot = this.itemHandler.getStackInSlot(2);
				if (stackInOutputSlot.isEmpty()) {
					return true;
				}else if(!stackInOutputSlot.isItemEqual(output)) {
					return false;
				}else if (stackInOutputSlot.getCount() + output.getCount() <= 64 && stackInOutputSlot.getCount() + output.getCount() <= stackInOutputSlot.getMaxStackSize()) {
					return true;
				}else{
					return stackInOutputSlot.getCount() + output.getCount() <= stackInOutputSlot.getMaxStackSize();
				}
			}
		}
		return false;
	}
	
	private void smelt(@Nullable IRecipe<?> recipeIn) {
		if (recipeIn != null && this.canSmelt(recipeIn)) {
			ItemStack stackInInputSlot = this.itemHandler.getStackInSlot(0);
			ItemStack recipeOutput = recipeIn.getRecipeOutput();
			ItemStack stackInOutputSlot = this.itemHandler.getStackInSlot(2);
			if (stackInOutputSlot.isEmpty()) {
				this.itemHandler.setStackInSlot(2, recipeOutput.copy());
			}else if (stackInOutputSlot.getItem() == recipeOutput.getItem()) {
				stackInOutputSlot.grow(recipeOutput.getCount());
			}
			
			stackInInputSlot.shrink(1);
		}
	}
	
	private boolean isBurning() {
		return this.burnTime > 0;
	}
	
	private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 1) {
                	return ForgeHooks.getBurnTime(stack) > 0;
                }else if (slot == 0) {
                	return true;
                }else{
                	return false;
                }
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                return super.insertItem(slot, stack, simulate);
            }
        };
    }
	
	@Override
	public void read(CompoundNBT compound) {
		itemHandler.deserializeNBT(compound);
		this.burnTime = compound.getInt("burnTime");
		this.currentItemBurnTime = compound.getInt("currentItemBurnTime");
		this.smeltProgress = compound.getInt("smeltProgress");
		super.read(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.putInt("burnTime", this.burnTime);
		compound.putInt("currentItemBurnTime", this.currentItemBurnTime);
		compound.putInt("smeltProgress", this.smeltProgress);
		return super.write(compound);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
		
		return super.getCapability(cap, side);
	}
}
