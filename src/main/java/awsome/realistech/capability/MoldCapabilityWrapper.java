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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class MoldCapabilityWrapper implements ICapabilitySerializable<CompoundNBT> {
	
	private FluidHandlerItemStack fluidHandler;
	private LazyOptional<IFluidHandlerItem> handler = LazyOptional.of(() -> fluidHandler);
	
	private HeatHandlerItemStack heatHandler;
	private LazyOptional<IHeat> handler2 = LazyOptional.of(() -> heatHandler);
	
	public MoldCapabilityWrapper(int capacity, ItemStack stack) {
		this.fluidHandler = new FluidHandlerItemStack(stack, capacity);
		this.heatHandler = new HeatHandlerItemStack(stack, 1773.0f, 0.5f, 0.5f);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY)) {
			return handler.cast();
		}
		
		if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
			return handler2.cast();
		}
		
		return LazyOptional.empty();
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT compound = new CompoundNBT();
		return compound;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		
	}

}
