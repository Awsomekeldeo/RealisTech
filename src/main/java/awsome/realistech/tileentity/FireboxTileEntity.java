package awsome.realistech.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.capability.impl.HeatHandler;
import awsome.realistech.registry.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FireboxTileEntity extends TileEntity implements ITickableTileEntity {
	
	private ItemStackHandler itemHandler = createHandler();
	private HeatHandler heatHandler = new HeatHandler(this, 1773.0f, true, 0.25f, 0.5f);
	private ITextComponent customName;
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
					FireboxTileEntity.this.burnTime = value;
					break;
				case 1:
					FireboxTileEntity.this.currentItemBurnTime = value;
					break;
				case 2:
					FireboxTileEntity.this.heatHandler.setTemp(value / 100.0f);
			}
		}
		
		@Override
		public int get(int index) {
			switch(index) {
	         	case 0:
	         		return FireboxTileEntity.this.burnTime;
	         	case 1:
	         		return FireboxTileEntity.this.currentItemBurnTime;
	         	case 2:
	         		return (int) (FireboxTileEntity.this.heatHandler.getTemperature() * 100);
			}
			return index;
		}
	};
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	
	public FireboxTileEntity() {
		super(Registration.FIREBOX_TILEENTITY.get());
		this.heatHandler.setTemp(this.heatHandler.getBaseTempBasedOnBiome(this.getPos()));
	}

	@Override
	public void tick() {
		boolean needsUpdating = false;
		boolean burning = this.isBurning();
		if (this.isBurning()) {
			this.burnTime--;
		}
		if (!this.world.isRemote) {
			ItemStack is = this.itemHandler.getStackInSlot(0);
			if (this.isBurning() || !is.isEmpty()) {
				if (!this.isBurning()) {
					
					this.burnTime = ForgeHooks.getBurnTime(is);
					this.currentItemBurnTime = this.burnTime;
					
					if (this.isBurning()) {
						needsUpdating = true;
						if (is.hasContainerItem()) {
							this.itemHandler.setStackInSlot(0, is.getContainerItem());
						}else if (!is.isEmpty()){
							Item item = is.getItem();
							is.shrink(1);
							if (is.isEmpty()) {
								ItemStack stack1 = item.getContainerItem(is);
								this.itemHandler.setStackInSlot(0, stack1);
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
		if (compound.contains("CustomName", 8)) {
	         this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
	    }
		this.burnTime = compound.getInt("burnTime");
		this.currentItemBurnTime = compound.getInt("currentItemBurnTime");
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("techmod:heatData", heatHandler.serializeNBT());
		if (this.customName != null) {
	         compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
	    }
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