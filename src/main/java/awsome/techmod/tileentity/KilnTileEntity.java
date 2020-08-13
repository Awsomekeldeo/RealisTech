package awsome.techmod.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.techmod.api.capability.energy.HeatCapability;
import awsome.techmod.api.capability.energy.IHeat;
import awsome.techmod.api.capability.impl.HeatHandler;
import awsome.techmod.registry.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class KilnTileEntity extends TileEntity {
	
	private ItemStackHandler itemHandler = createHandler();
	private HeatHandler heatHandler = new HeatHandler(this, 1773.0f, true, 0.25f, 0.5f);
	private int burnTime;
	protected int currentItemBurnTime;
	public final IIntArray fireboxData = new IIntArray() {
		
		@Override
		public int size() {
			return 3;
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
			}
			return index;
		}
	};
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	
	public KilnTileEntity() {
		super(Registration.KILN_TILEENTITY.get());
		this.heatHandler.setTemp(this.heatHandler.getBaseTempBasedOnBiome(this.getPos()));
	}
	
	private boolean isBurning() {
		return burnTime > 0;
	}

	private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return ForgeHooks.getBurnTime(stack) > 0;
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
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("techmod:heatData", heatHandler.serializeNBT());
		compound.putInt("burnTime", this.burnTime);
		compound.putInt("currentItemBurnTime", this.currentItemBurnTime);
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
		if (itemHandler.getStackInSlot(0).isEmpty()) {
			return 0;
		}else{
			return ForgeHooks.getBurnTime(itemHandler.getStackInSlot(0));
		}
	}
	
	public int getBurnTime() {
		return this.burnTime;
	}
}
