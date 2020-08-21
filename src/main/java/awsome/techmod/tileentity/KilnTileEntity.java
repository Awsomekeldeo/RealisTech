package awsome.techmod.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.techmod.Techmod;
import awsome.techmod.api.capability.energy.HeatCapability;
import awsome.techmod.api.capability.energy.IHeat;
import awsome.techmod.api.capability.impl.HeatHandler;
import awsome.techmod.api.recipe.KilnRecipe;
import awsome.techmod.registry.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class KilnTileEntity extends TileEntity implements ITickableTileEntity {
	
	private ItemStackHandler itemHandler = createHandler();
	private HeatHandler heatHandler = new HeatHandler(this, 1773.0f, false, 0.25f, 0.5f);
	private int burnTime;
	private int fireProgress;
	private int totalFireProgress = 200;
	protected int currentItemBurnTime;
	protected final IRecipeType<KilnRecipe> recipeType;
	
	public final IIntArray kilnData = new IIntArray() {
		
		@Override
		public int size() {
			return 5;
		}
		
		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0:
					KilnTileEntity.this.burnTime = value;
					break;
				case 1:
					KilnTileEntity.this.currentItemBurnTime = value;
					break;
				case 2:
					KilnTileEntity.this.heatHandler.setTemp(value / 100.0f);
					break;
				case 3:
					KilnTileEntity.this.fireProgress = value;
					break;
				case 4:
					KilnTileEntity.this.totalFireProgress = value;
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
	         	case 0:
	         		return KilnTileEntity.this.burnTime;
	         	case 1:
	         		return KilnTileEntity.this.currentItemBurnTime;
	         	case 2:
	         		return (int) (KilnTileEntity.this.heatHandler.getTemperature() * 100);
	         	case 3:
	         		return KilnTileEntity.this.fireProgress;
	         	case 4:
	         		return KilnTileEntity.this.totalFireProgress;
			}
			return index;
		}
	};
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	
	public KilnTileEntity() {
		super(Registration.KILN_TILEENTITY.get());
		this.heatHandler.setTemp(this.heatHandler.getBaseTempBasedOnBiome(this.getPos()));
		this.recipeType = KilnRecipe.KILN_RECIPE;
	}
	
	@Override
	public void tick() {
		boolean needsUpdating = false;
		boolean burning = this.isBurning();
		if (this.isBurning()) {
			this.burnTime--;
		}
		if (!this.world.isRemote) {
			ItemStack is = this.itemHandler.getStackInSlot(4);
			if (this.isBurning() || !is.isEmpty()) {
				if (!this.isBurning()) {
					
					this.burnTime = ForgeHooks.getBurnTime(is);
					this.currentItemBurnTime = this.burnTime;
					
					if (this.isBurning()) {
						needsUpdating = true;
						if (is.hasContainerItem()) {
							this.itemHandler.setStackInSlot(4, is.getContainerItem());
						}else if (!is.isEmpty()){
							Item item = is.getItem();
							is.shrink(1);
							if (is.isEmpty()) {
								ItemStack stack1 = item.getContainerItem(is);
								this.itemHandler.setStackInSlot(4, stack1);
							}
						}
					}
				}
				if(this.heatHandler.getTemperature() < this.heatHandler.getMaxTemperature()) {
					this.heatHandler.heat();
				}
			}else{
				if(this.heatHandler.getTemperature() > this.heatHandler.getBaseTempBasedOnBiome(getPos())) {
					this.heatHandler.cool();
				}
			}
			if (burning != this.isBurning())
            {
                needsUpdating = true;
            }
			for (int i = 0; i < 4; i++) {
				NonNullList<ItemStack> stacks = NonNullList.from(this.itemHandler.getStackInSlot(i), this.itemHandler.getStackInSlot(i));
				RecipeWrapper wrapper = new RecipeWrapper(new ItemStackHandler(stacks));
				IRecipe<?> recipe = this.world.getRecipeManager().getRecipe(this.recipeType, wrapper, this.world).orElse(null);
				if (this.canFire(recipe, i)) {
					if (i == 0) {
						this.fireProgress++;
					}
					if (this.fireProgress == totalFireProgress) {
						this.fireProgress = 0;
						this.totalFireProgress = 200;
						if (this.canFire(recipe, i)) {
							this.fire(recipe, i);
						}
						needsUpdating = true;
					}
				}else{
					this.fireProgress = 0;
					needsUpdating = true;
				}
			}
		}
		if (needsUpdating)
        {
            this.markDirty();
        }
	}
	
	private boolean isBurning() {
		return this.burnTime > 0;
	}

	private ItemStackHandler createHandler() {
        return new ItemStackHandler(9) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 4) {
                	return ForgeHooks.getBurnTime(stack) > 0;
                }else if (slot < 4) {
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
		itemHandler.deserializeNBT(compound.getCompound("inventory"));
		heatHandler.deserializeNBT(compound.getCompound("techmod:heatData"));
		this.burnTime = compound.getInt("burnTime");
		this.currentItemBurnTime = compound.getInt("currentItemBurnTime");
		this.fireProgress = compound.getInt("fireProgress");
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("techmod:heatData", heatHandler.serializeNBT());
		compound.putInt("burnTime", this.burnTime);
		compound.putInt("currentItemBurnTime", this.currentItemBurnTime);
		compound.putInt("fireProgress", this.fireProgress);
		return super.write(compound);
	}
	
	@Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
        if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
        	return heatCap.cast();
        }
        return super.getCapability(cap, side);
	}
	
	public int getItemBurnTime() {
		if (itemHandler.getStackInSlot(4).isEmpty()) {
			return 0;
		}else{
			return ForgeHooks.getBurnTime(itemHandler.getStackInSlot(4));
		}
	}
	
	public int getBurnTime() {
		return this.burnTime;
	}
	
	protected boolean canFire(@Nullable IRecipe<?> recipe, int slot) {
		if (!this.itemHandler.getStackInSlot(slot).isEmpty() && recipe != null) {
			if (this.heatHandler.getTemperature() >= 1000.0f) {
				ItemStack output = recipe.getRecipeOutput().copy();
				if (output.isEmpty()) {
					return false;
				}else{
					ItemStack stackInOutputSlot = ItemStack.EMPTY;
					for (int i = 5; i < this.itemHandler.getSlots(); i++) {
						if (this.itemHandler.getStackInSlot(i).isEmpty()) {
							break;
						}
						if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
							stackInOutputSlot = this.itemHandler.getStackInSlot(i);
						}
					}
					if (stackInOutputSlot.isEmpty()) {
						return true;
					}else if(!stackInOutputSlot.isItemEqual(output)) {
						return false;
					}else{
						return stackInOutputSlot.getCount() + output.getCount() <= stackInOutputSlot.getMaxStackSize();
					}
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	private void fire(@Nullable IRecipe<?> recipe, int slot) {
		if (recipe != null && canFire(recipe, slot)) {
			ItemStack inputStack = this.itemHandler.getStackInSlot(slot);
			ItemStack outputStack = recipe.getRecipeOutput().copy();
			Techmod.LOGGER.info("Recipe output for kiln is:" + outputStack.toString());
			ItemStack itemInOutputSlot = ItemStack.EMPTY;
			int outputSlotIndex = 5;
			for (int i = 5; i < this.itemHandler.getSlots(); i++) {
				itemInOutputSlot = this.itemHandler.getStackInSlot(i);
				if (inputStack.getItem() == itemInOutputSlot.getItem()) {
					outputSlotIndex = i;
					break;
				}
				if (itemInOutputSlot.isEmpty()) {
					outputSlotIndex = i;
					break;
				}
			}
			Techmod.LOGGER.info("Item in kiln's output slot is:" + itemInOutputSlot.toString());
			if (itemInOutputSlot.isEmpty()) {
				this.itemHandler.setStackInSlot(outputSlotIndex, outputStack.copy());
			}else if (itemInOutputSlot.getItem() == inputStack.getItem()) {
				itemInOutputSlot.grow(outputStack.getCount());
			}
			inputStack.shrink(1);
		}
	}
}
