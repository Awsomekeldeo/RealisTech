package awsome.realistech.capability;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.capability.impl.HeatHandlerItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FilledMoldCapabilityWrapper implements ICapabilitySerializable<CompoundNBT> {
	
	private ItemStackHandler itemHandler;
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	
	private FluidHandlerItemStack fluidHandler;
	private LazyOptional<IFluidHandlerItem> handler2 = LazyOptional.of(() -> fluidHandler);
	
	private HeatHandlerItemStack heatHandler;
	private LazyOptional<IHeat> handler3 = LazyOptional.of(() -> heatHandler);
	
	public FilledMoldCapabilityWrapper(int capacity, ItemStack stack, CompoundNBT nbt) {
		this.itemHandler = new ItemStackHandler();
		this.fluidHandler = new FluidHandlerItemStack(stack, capacity);
		this.heatHandler = new HeatHandlerItemStack(stack, 1773.0f, 2.0f, 2.0f);
		this.deserializeNBT(nbt);
	}
	
	public FilledMoldCapabilityWrapper(int capacity, ItemStack stack) {
		this.itemHandler = new ItemStackHandler();
		this.fluidHandler = new FluidHandlerItemStack(stack, capacity);
		this.heatHandler = new HeatHandlerItemStack(stack, 1773.0f, 2.0f, 2.0f);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			return handler.cast();
		}
		
		if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)) {
			return handler2.cast();
		}
		
		if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
			return handler3.cast();
		}
		
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		ItemStack stack = this.itemHandler.getStackInSlot(0);
		if (stack != null) {
			stack.write(compound);
		}
		
		compound.put("realistech:heatData", HeatCapability.HEAT_CAPABILITY.writeNBT(heatHandler, null));
		
		FluidStack fluidStack = this.fluidHandler.getFluidInTank(0);
		
		if (fluidStack != null) {
			fluidStack.writeToNBT(compound);
		}
		
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		ItemStack stack = ItemStack.read(nbt);
		if (stack != null) {
			this.itemHandler.setStackInSlot(0, stack);
		}
		if (nbt.contains("realistech:heatData")) {
			HeatCapability.HEAT_CAPABILITY.readNBT(heatHandler, null, nbt.getCompound("realistech:heatData"));
		}
		if (nbt.contains("Fluid")) {
			FluidStack fluidstack = FluidStack.loadFluidStackFromNBT(nbt.getCompound("Fluid"));
			if (fluidstack != null) {
				this.fluidHandler.fill(fluidstack, FluidAction.EXECUTE);
			}
		}
	}
	
}
