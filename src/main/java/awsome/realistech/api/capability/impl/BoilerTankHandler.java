package awsome.realistech.api.capability.impl;

import awsome.realistech.registry.Registration;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BoilerTankHandler implements IFluidHandler {
	
	private FluidStack waterTank = FluidStack.EMPTY;
	private FluidStack steamTank = FluidStack.EMPTY;

	@Override
	public int getTanks() {
		return 2;
	}

	@Override
	public FluidStack getFluidInTank(int tank) {
		if (tank == 0) {
			return waterTank;
		}else if (tank == 1) {
			return steamTank;
		}else{
			throw new IndexOutOfBoundsException("Tank " + tank + " not in range [0,1]");
		}
	}

	@Override
	public int getTankCapacity(int tank) {
		return 16000;
	}

	@Override
	public boolean isFluidValid(int tank, FluidStack stack) {
		if (tank == 0) {
			if (!(stack.getFluid() == Fluids.WATER)) {
				return false;
			}
			return true;
		}else if (tank == 1) {
			if (!(stack.getFluid() == Registration.STEAM.get())) {
				return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		
		for (int i = 0; i < getTanks(); i ++) {
			if (i == 0) {
				if (resource.isEmpty() || !isFluidValid(i, resource))
		        {
		            return 0;
		        }
		        if (action.simulate())
		        {
		            if (waterTank.isEmpty())
		            {
		                return Math.min(getTankCapacity(i), resource.getAmount());
		            }
		            if (!waterTank.isFluidEqual(resource))
		            {
		                return 0;
		            }
		            return Math.min(getTankCapacity(i) - waterTank.getAmount(), resource.getAmount());
		        }
		        if (waterTank.isEmpty())
		        {
		            waterTank = new FluidStack(resource, Math.min(getTankCapacity(i), resource.getAmount()));
		            return waterTank.getAmount();
		        }
		        if (!waterTank.isFluidEqual(resource))
		        {
		            return 0;
		        }
		        int filled = getTankCapacity(i) - waterTank.getAmount();
		
		        if (resource.getAmount() < filled)
		        {
		            waterTank.grow(resource.getAmount());
		            filled = resource.getAmount();
		        }
		        else
		        {
		            waterTank.setAmount(getTankCapacity(i));
		        }
		        
		        return filled;
			}else if (i == 1) {
				
				if (resource.isEmpty() || !isFluidValid(i, resource))
		        {
		            return 0;
		        }
		        if (action.simulate())
		        {
		            if (steamTank.isEmpty())
		            {
		                return Math.min(getTankCapacity(i), resource.getAmount());
		            }
		            if (!steamTank.isFluidEqual(resource))
		            {
		                return 0;
		            }
		            return Math.min(getTankCapacity(i) - steamTank.getAmount(), resource.getAmount());
		        }
		        if (steamTank.isEmpty())
		        {
		            steamTank = new FluidStack(resource, Math.min(getTankCapacity(i), resource.getAmount()));
		            return steamTank.getAmount();
		        }
		        if (!steamTank.isFluidEqual(resource))
		        {
		            return 0;
		        }
		        int filled = getTankCapacity(i) - steamTank.getAmount();
		
		        if (resource.getAmount() < filled)
		        {
		            steamTank.grow(resource.getAmount());
		            filled = resource.getAmount();
		        }
		        else
		        {
		            steamTank.setAmount(getTankCapacity(i));
		        }
		        
		        return filled;
			}
		}
		
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		if (resource.isEmpty())
        {
            return FluidStack.EMPTY;
        }
        
		if (resource.getFluid() == Fluids.WATER) {
			
			int drained = resource.getAmount();
			if (waterTank.getAmount() < drained)
	        {
	            drained = waterTank.getAmount();
	        }
	        FluidStack stack = new FluidStack(waterTank, drained);
	        if (action.execute() && drained > 0)
	        {
	            waterTank.shrink(drained);
	        }
	        return stack;
		}
		
		if (resource.getFluid() == Registration.STEAM.get()) {
			
			int drained = resource.getAmount();
			if (steamTank.getAmount() < drained)
	        {
	            drained = steamTank.getAmount();
	        }
	        FluidStack stack = new FluidStack(steamTank, drained);
	        if (action.execute() && drained > 0)
	        {
	            steamTank.shrink(drained);
	        }
	        return stack;
		}
		
		return FluidStack.EMPTY;
	}

	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		int drained = maxDrain;
        if (waterTank.getAmount() < drained)
        {
            drained = waterTank.getAmount();
        }
        FluidStack stack = new FluidStack(waterTank, drained);
        if (action.execute() && drained > 0)
        {
            waterTank.shrink(drained);
        }
        return stack;
	}
	
	public void deserializeNBT(CompoundNBT nbt) {
		ListNBT tags = nbt.getList("Tanks", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < tags.size(); i++) {
			CompoundNBT fluidTags = tags.getCompound(i);
			int tank = fluidTags.getInt("Tank");
			
			if (tank >= 0 && tank < this.getTanks()) {
				if (tank == 0) {
					waterTank = FluidStack.loadFluidStackFromNBT(fluidTags);
				}else if (tank == 1) {
					steamTank = FluidStack.loadFluidStackFromNBT(fluidTags);
				}
			}
		}
	}
	
	public CompoundNBT serializeNBT() {
		ListNBT list = new ListNBT();
		for (int i = 0; i < this.getTanks(); i++) {
			if(!this.getFluidInTank(i).isEmpty()) {
				CompoundNBT fluidTag = new CompoundNBT();
				fluidTag.putInt("Tank", i);
				this.getFluidInTank(i).writeToNBT(fluidTag);
				list.add(fluidTag);
			}
		}
		CompoundNBT compound = new CompoundNBT();
		compound.put("Tanks", list);
		return compound;
	}

}
