package awsome.realistech.api.capability.impl;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CrucibleTankHandler implements IFluidHandler {
	
	private final int totalCapacity;
	private NonNullList<FluidStack> fluids = NonNullList.create();
	
	public CrucibleTankHandler(int totalCapacityMb) {
		this.totalCapacity = totalCapacityMb;
		this.fluids.add(FluidStack.EMPTY);
	}
	
	public NonNullList<FluidStack> getStacks() {
		return fluids;
	}
	
	@Override
	public int getTanks() {
		return fluids.size();
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		return fluids.get(tank);
	}

	@Override
	public int getTankCapacity(int tank) {
		int capacity = totalCapacity;
		
		for (int i = 0; i < this.fluids.size(); i++) {
			capacity -= this.fluids.get(i).getAmount();
		}
		
		return capacity;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		return true;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		int capacity = totalCapacity;
		
		if (resource.isEmpty()) {
			return 0;
		}
		
		int nextAvailableSlot = 0;
		int prevSlot = 0;
		
		for (int i = 0; i < this.fluids.size(); i++) {
			
			if (this.fluids.get(i).isEmpty()) {
				nextAvailableSlot = i;
			}
			
			prevSlot = nextAvailableSlot == 0 ? 0 : nextAvailableSlot - 1;
			
			capacity -= this.fluids.get(i).getAmount();
		}
		
		if (!this.isFluidValid(nextAvailableSlot, resource)) {
			return 0;
		}
		
		if (action.simulate()) {
			
			if (this.fluids.get(nextAvailableSlot).isEmpty()) {
				return Math.min(capacity, resource.getAmount());
			}
			
			if (!this.fluids.get(nextAvailableSlot).isFluidEqual(resource)) {
				return 0;
			}
			
			return Math.min(capacity - this.fluids.get(nextAvailableSlot).getAmount(), resource.getAmount());
		}
		
		if (this.fluids.get(nextAvailableSlot).isEmpty() && !this.fluids.get(prevSlot).isFluidEqual(resource)) {
			
			this.fluids.add(nextAvailableSlot, new FluidStack(resource, Math.min(capacity, resource.getAmount())));
			return this.fluids.get(nextAvailableSlot).getAmount();
			
		}
		
		if (!this.fluids.get(prevSlot).isFluidEqual(resource)) {
			return 0;
		}
		
		int filled = capacity;
			
		if (resource.getAmount() <= filled) {
			this.fluids.get(prevSlot).grow(resource.getAmount());
			filled = resource.getAmount();
		}
		
		return filled;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		
		if (resource.isEmpty() || !resource.isFluidEqual(this.fluids.get(0))) {
			return FluidStack.EMPTY;
		}
		
		return drain(resource.getAmount(), action);
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		
		int drained = maxDrain;
		
		if (this.fluids.get(0).getAmount() < drained) {
			drained = this.fluids.get(0).getAmount();
		}
		
		FluidStack stack = new FluidStack(this.fluids.get(0), drained);
		
		if (action.execute() && drained > 0) {
			this.fluids.get(0).shrink(drained);
		}
		
		return stack;
	}
	
	public void deserializeNBT(CompoundNBT nbt) {
		ListNBT tags = nbt.getList("Fluids", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tags.size(); i++) {
			CompoundNBT fluidTags = tags.getCompound(i);
			int tank = fluidTags.getInt("Tank");
			
			if (tank >= 0 && tank < this.fluids.size()) {
				this.fluids.set(tank, FluidStack.loadFluidStackFromNBT(fluidTags));
			}
		}
	}
	
	public CompoundNBT serializeNBT() {
		ListNBT list = new ListNBT();
		for (int i = 0; i < this.fluids.size(); i++) {
			if(!this.fluids.get(i).isEmpty()) {
				CompoundNBT fluidTag = new CompoundNBT();
				fluidTag.putInt("Tank", i);
				this.fluids.get(i).writeToNBT(fluidTag);
				list.add(fluidTag);
			}
		}
		CompoundNBT compound = new CompoundNBT();
		compound.put("Tanks", list);
		return compound;
	}
	
}
