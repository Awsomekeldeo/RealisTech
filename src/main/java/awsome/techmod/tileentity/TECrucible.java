package awsome.techmod.tileentity;

import javax.annotation.Nonnull;

import awsome.techmod.api.capability.energy.CapabilityHeat;
import awsome.techmod.api.capability.energy.IHeat;
import awsome.techmod.api.capability.impl.HeatHandler;
import awsome.techmod.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TECrucible extends TileEntity implements ITickableTileEntity {
	
	private ItemStackHandler itemHandler = createItemHandler();
	private HeatHandler heatHandler = new HeatHandler(this, 1773.0f, true);
	
	public TECrucible() {
		super(Registration.CRUCIBLE_TILEENTITY.get());
	}
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	
	public ItemStackHandler createItemHandler() {
		return new ItemStackHandler(1) {
			
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
		heatHandler.deserializeNBT(compound.getCompound("techmod:heatData"));
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("techmod:heatData", heatHandler.serializeNBT());
		return super.write(compound);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return handler.cast();
        }
        if (cap.equals(CapabilityHeat.HEAT_CAPABILITY)) {
        	return heatCap.cast();
        }
		return super.getCapability(cap, side);
	}

	@Override
	public void tick() {
		this.heatHandler.setTemp(this.heatHandler.drawHeatFromSide(getPos(), Direction.DOWN));
	}
}
