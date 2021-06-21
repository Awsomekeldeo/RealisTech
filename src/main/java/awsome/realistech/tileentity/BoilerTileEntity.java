package awsome.realistech.tileentity;

import javax.annotation.Nonnull;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.capability.impl.HeatHandler;
import awsome.realistech.registry.Registration;
import awsome.realistech.util.MathUtil;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BoilerTileEntity extends TileEntity implements ITickableTileEntity {
	
	public ItemStackHandler itemHandler = createItemHandler();
	private HeatHandler heatHandler = new HeatHandler(0.435f, this, 1538.0f, false, 0.5f);
	
	private FluidTank waterTank = new FluidTank(16000);
	private FluidTank steamTank = new FluidTank(16000);
	
	public IntReferenceHolder boilerData = new IntReferenceHolder() {
		
		@Override
		public void set(int value) {
			BoilerTileEntity.this.heatHandler.setTemp(value / 100.0f);
		}
		
		@Override
		public int get() {
			return (int) (BoilerTileEntity.this.heatHandler.getTemperature() * 100.0f);
		}
	};

	public BoilerTileEntity() {
		super(Registration.BOILER_TILEENTITY.get());
	}

	private ItemStackHandler createItemHandler() {
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

	@Override
	public void tick() {
		float lastKnownTemp = this.heatHandler.getTemperature();
		
		if (!this.world.isRemote) {
			
			if (this.itemHandler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
				IFluidHandlerItem h = this.itemHandler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
				if (FluidUtil.tryFluidTransfer(this.waterTank, h, Integer.MAX_VALUE, true) != null) {
					this.itemHandler.setStackInSlot(0, h.getContainer());
				}
			}
			
			if (this.itemHandler.getStackInSlot(1).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
				IFluidHandlerItem h = this.itemHandler.getStackInSlot(1).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
				if (FluidUtil.tryFluidTransfer(h, this.waterTank, Integer.MAX_VALUE, true) != null) {
					this.itemHandler.setStackInSlot(1, h.getContainer());
				}
			}
			
			if (this.heatHandler.getTemperature() < this.heatHandler.getMaxTemperature() && this.heatHandler.getTemperature() >= MathUtil.roundFloat(this.heatHandler.getBaseTempBasedOnBiome(this.getPos()), 2)) {
				if(this.heatHandler.drawHeatFromSide(getPos(), Direction.DOWN)) {
					this.heatHandler.heat();
				}else{
					this.heatHandler.cool();
				}
			}
			
			if (this.heatHandler.getTemperature() >= 100.0f) {
				if (this.waterTank.getFluidInTank(0).getAmount() <= 2000 && this.waterTank.getFluidInTank(0).getAmount() > 0) {
					this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
					this.world.createExplosion(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), 4.0f, Explosion.Mode.DESTROY);
				}else{
					if (this.steamTank.fill(new FluidStack(Registration.STEAM.get(), 5), FluidAction.EXECUTE) > 0) {
						this.waterTank.drain(2, FluidAction.EXECUTE);
					}
				}
			}
		}
		
		if (this.heatHandler.getTemperature() != lastKnownTemp) {
			markDirty();
		}
	}
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	private LazyOptional<IFluidHandler> waterHandler = LazyOptional.of(() -> waterTank);
	private LazyOptional<IFluidHandler> steamHandler = LazyOptional.of(() -> steamTank);
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
		
        if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
        	return heatCap.cast();
        }
        
        if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
        	if (side.equals(Direction.UP)) {
        		return steamHandler.cast();
        	}else if (!side.equals(Direction.DOWN)){
        		return waterHandler.cast();
        	}
        	
        	return waterHandler.cast();
        }
        
		return super.getCapability(cap, side);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		itemHandler.deserializeNBT(compound.getCompound("inventory"));
		heatHandler.deserializeNBT(compound.getCompound("realistech:heatData"));
		waterTank.readFromNBT(compound.getCompound("waterTank"));
		steamTank.readFromNBT(compound.getCompound("steamTank"));
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("realistech:heatData", heatHandler.serializeNBT());
		compound.put("waterTank", new CompoundNBT());
		compound.put("steamTank", new CompoundNBT());
		waterTank.writeToNBT(compound.getCompound("waterTank"));
		steamTank.writeToNBT(compound.getCompound("steamTank"));
		return super.write(compound);
	}

}
