package awsome.realistech.tileentity;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.capability.impl.CrucibleTankHandler;
import awsome.realistech.api.capability.impl.HeatHandler;
import awsome.realistech.api.recipe.AlloyRecipe;
import awsome.realistech.api.recipe.MeltingRecipe;
import awsome.realistech.items.SolidCeramicMoldItem;
import awsome.realistech.registry.Registration;
import awsome.realistech.util.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CrucibleTileEntity extends TileEntity implements ITickableTileEntity {
	
	private ItemStackHandler itemHandler = createItemHandler();
	private HeatHandler heatHandler = new HeatHandler(1.05f, this, 1773.0f, false, 0.5f);
	private CrucibleTankHandler crucibleTank = new CrucibleTankHandler(4608);
	private int tick = 0;
	
	public IntReferenceHolder crucibleData = new IntReferenceHolder() {
		
		@Override
		public void set(int value) {
			CrucibleTileEntity.this.heatHandler.setTemp(value / 100.0f);
		}
		
		@Override
		public int get() {
			return (int) (CrucibleTileEntity.this.heatHandler.getTemperature() * 100.0f);
		}
	};
	
	public CrucibleTileEntity() {
		super(Registration.CRUCIBLE_TILEENTITY.get());
		this.recipeType = MeltingRecipe.MELTING_RECIPE;
	}
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	private LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> crucibleTank);
	protected final IRecipeType<MeltingRecipe> recipeType;
	
	public ItemStackHandler createItemHandler() {
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
                return super.insertItem(slot, stack, simulate);
            }
        };
	}
	
	public int getTemperature() {
		return (int) (this.heatHandler.getTemperature() * 100);
	}

	@Override
	public void read(CompoundNBT compound) {
		itemHandler.deserializeNBT(compound.getCompound("inventory"));
		heatHandler.deserializeNBT(compound.getCompound("realistech:heatData"));
		crucibleTank.deserializeNBT(compound.getCompound("boilerTanks"));
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("realistech:heatData", heatHandler.serializeNBT());
		compound.put("boilerTanks", crucibleTank.serializeNBT());
		return super.write(compound);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
		
        if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
        	return heatCap.cast();
        }
        
        if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
        	return fluidHandler.cast();
        }
        
		return super.getCapability(cap, side);
	}
	
	private boolean canMelt(@Nullable IRecipe<?> recipe) {
		if (!this.itemHandler.getStackInSlot(0).isEmpty() && recipe != null) {
			MeltingRecipe recipe1 = (MeltingRecipe)recipe;
			FluidStack output = recipe1.getResult();
			float temperature = recipe1.getMeltTemp();
			
			if (this.heatHandler.getTemperature() < temperature) {
				return false;
			}
			
			if (output.isEmpty()) {
				return false;
			}else{
				if (this.crucibleTank.fill(output, FluidAction.SIMULATE) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void meltRecipe(@Nullable IRecipe<?> recipe) {
		if (recipe != null && this.canMelt(recipe)) {
			MeltingRecipe recipe1 = (MeltingRecipe)recipe;
			ItemStack stackInInputSlot = this.itemHandler.getStackInSlot(0);
			FluidStack recipeOutput = recipe1.getResult();
			this.crucibleTank.fill(recipeOutput, FluidAction.EXECUTE);
			stackInInputSlot.shrink(1);
		}
	}
	
	private void attemptAlloyRecipe() {
		for (IRecipe<?> recipe : this.getRecipes(AlloyRecipe.ALLOY_RECIPE, this.world.getRecipeManager()).values()) {
			if (recipe instanceof AlloyRecipe) {
				AlloyRecipe alloyRecipe = (AlloyRecipe)recipe;
				if (alloyRecipe.isValid(this.crucibleTank)) {
					for (int i = 0; i < alloyRecipe.getInputs().size(); i++) {
						this.crucibleTank.drain(alloyRecipe.getInputs().get(i), FluidAction.EXECUTE);
					}
					this.crucibleTank.fill(alloyRecipe.getResult(), FluidAction.EXECUTE);
					markDirty();
				}
			}
		}
	}

	@Override
	public void tick() {
		float lastKnownTemp = this.heatHandler.getTemperature();
		if (!this.world.isRemote) {
			this.tick++;
			
			if (this.tick == 20) {
				this.tick = 0;
			}
			
			if (this.tick == 10) {
				ItemStack stack = this.itemHandler.getStackInSlot(0);
				IItemHandlerModifiable recipeItemHandler = new ItemStackHandler();
				recipeItemHandler.setStackInSlot(0, stack);
				RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
				IRecipe<?> meltingRecipe = this.world.getRecipeManager().getRecipe(this.recipeType, recipeWrapper, this.world).orElse(null);
				if (this.canMelt(meltingRecipe)) {
					this.meltRecipe(meltingRecipe);
					markDirty();
				}
				
			}
			
			attemptAlloyRecipe();
			drainMolds();
			this.crucibleTank.balanceTanks();
			fillMolds();
			
			if (this.heatHandler.getTemperature() < this.heatHandler.getMaxTemperature() && this.heatHandler.getTemperature() >= MathUtil.roundFloat(this.heatHandler.getBaseTempBasedOnBiome(this.getPos()), 2)) {
				if(this.heatHandler.drawHeatFromSide(getPos(), Direction.DOWN)) {
					this.heatHandler.heat();
				}else{
					this.heatHandler.cool();
				}
			}
		}
		
		if (this.heatHandler.getTemperature() != lastKnownTemp) {
			markDirty();
		}
	}
	
	private void drainMolds() {
		ItemStack stackInInputSlot = this.itemHandler.getStackInSlot(0);
		if(stackInInputSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent() && !(stackInInputSlot.getItem() instanceof SolidCeramicMoldItem)) {
			IFluidHandlerItem h = stackInInputSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			if (this.crucibleTank.fill(h.drain(1, FluidAction.SIMULATE), FluidAction.SIMULATE) > 0) {
				stackInInputSlot.getOrCreateTag().putInt("realistech:meltTemp", this.crucibleTank.drain(1, FluidAction.SIMULATE).getFluid().getAttributes().getTemperature());
				this.crucibleTank.fill(h.drain(1, FluidAction.EXECUTE), FluidAction.EXECUTE);
			}
		}
		
		if (stackInInputSlot.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent() && (stackInInputSlot.getItem() instanceof SolidCeramicMoldItem)) {
			if (stackInInputSlot.getOrCreateTag().contains("realistech:meltTemp")) {
				float meltTemp = stackInInputSlot.getTag().getFloat("realistech:meltTemp");
				IHeat h = stackInInputSlot.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
				
				if (h.getTemperature() >= meltTemp) {
					if(stackInInputSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
						SolidCeramicMoldItem moldItem = (SolidCeramicMoldItem) stackInInputSlot.getItem();
						ItemStack newStack = new ItemStack(moldItem.getEmptyMold().getItem(), stackInInputSlot.getCount(), stackInInputSlot.serializeNBT().getCompound("ForgeCaps"));
						CompoundNBT nbt2 = stackInInputSlot.getTag();

						newStack.setTag(nbt2);
						if (newStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
							if (stackInInputSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
								IItemHandler h4 = stackInInputSlot.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
								IFluidHandlerItem h3 = newStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
								
								ItemStack stack = h4.getStackInSlot(0);
								IItemHandlerModifiable recipeItemHandler = new ItemStackHandler();
								recipeItemHandler.setStackInSlot(0, stack);
								RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
								IRecipe<?> meltingRecipe2 = this.world.getRecipeManager().getRecipe(this.recipeType, recipeWrapper, this.world).orElse(null);
								if (meltingRecipe2 != null) {
									MeltingRecipe recipe = (MeltingRecipe)meltingRecipe2;
									FluidStack output = recipe.getResult();
									if (h.getTemperature() >= recipe.getMeltTemp() && !output.isEmpty()) {
										h3.fill(output, FluidAction.EXECUTE);
									}
								}
							}
						}
						this.itemHandler.setStackInSlot(0, newStack);
					}
				}else{
					h.heat();
				}
			}
		}
	}

	private void fillMolds() {
		ItemStack stackInMoldSlot = this.itemHandler.getStackInSlot(1);
		if(stackInMoldSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
			IFluidHandlerItem h = stackInMoldSlot.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			if (h.fill(this.crucibleTank.drain(1, FluidAction.SIMULATE), FluidAction.SIMULATE) > 0) {
				stackInMoldSlot.getOrCreateTag().putInt("realistech:meltTemp", this.crucibleTank.drain(1, FluidAction.SIMULATE).getFluid().getAttributes().getTemperature());
				h.fill(this.crucibleTank.drain(1, FluidAction.EXECUTE), FluidAction.EXECUTE);
			}
		}
		
		if (stackInMoldSlot.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent() && !(stackInMoldSlot.getItem() instanceof SolidCeramicMoldItem)) {
			IHeat h = stackInMoldSlot.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			h.setTemp(this.heatHandler.getTemperature());
		}
	}

	private Map<ResourceLocation, IRecipe<?>> getRecipes (IRecipeType<?> recipeType, RecipeManager manager) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return recipesMap.get(recipeType);
    }
}
