package awsome.techmod.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class MoldCapabilityWrapper implements ICapabilitySerializable<CompoundNBT> {
	
	private FluidHandlerItemStack fluidHandler;
	private LazyOptional<IFluidHandlerItem> handler = LazyOptional.of(() -> fluidHandler);
	
	public MoldCapabilityWrapper(int capacity, ItemStack stack) {
		this.fluidHandler = new FluidHandlerItemStack(stack, capacity);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)) {
			return handler.cast();
		}
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		FluidStack stack = this.fluidHandler.getFluid();
		if (stack != null) {
			stack.writeToNBT(compound);
		}
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		FluidStack stack = FluidStack.loadFluidStackFromNBT(nbt);
		if (stack != null) {
			this.fluidHandler.fill(stack, FluidAction.EXECUTE);
		}
	}

}
