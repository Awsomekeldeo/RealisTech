package awsome.realistech.tileentity;

import javax.annotation.Nonnull;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.capability.impl.HeatHandler;
import awsome.realistech.data.tags.ModTags;
import awsome.realistech.registry.Registration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BloomeryTileEntity extends TileEntity implements ITickableTileEntity {
	
	private int air = 0;
	
	private ItemStackHandler itemHandler = createHandler();
	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	
	private HeatHandler heatHandler = new HeatHandler(this, 1200.0f, true, 0.25f, 0.5f);
	private LazyOptional<IHeat> heatCap = LazyOptional.of(() -> heatHandler);
	
	private int burnTime;
	private int currentItemBurnTime;
	
	private int bloomProgress;
	private int totalBloomProgress = 200;
	
	public boolean multiBlockFormed = false;
	
	private static final int AIR_AMOUNT_PER_BELLOWS_PUMP = 200;
	private static final int MAX_AIR_CAPACITY = 600;
	
	public final IIntArray bloomeryData = new IIntArray() {

		@Override
		public int size() {
			return 6;
		}

		@Override
		public void set(int index, int value) {
			switch(index) {
				case 0:
					BloomeryTileEntity.this.burnTime = value;
					break;
				case 1:
					BloomeryTileEntity.this.currentItemBurnTime = value;
					break;
				case 2:
					BloomeryTileEntity.this.heatHandler.setTemp(value / 100.0f);
					break;
				case 3:
					BloomeryTileEntity.this.air = value;
					break;
				case 4:
					BloomeryTileEntity.this.bloomProgress = value;
					break;
				case 5:
					BloomeryTileEntity.this.totalBloomProgress = value;
					break;
			}
		}

		@Override
		public int get(int index) {
			switch(index) {
				case 0:
					return BloomeryTileEntity.this.burnTime;
				case 1:
					return BloomeryTileEntity.this.currentItemBurnTime;
				case 2:
					return (int) (BloomeryTileEntity.this.heatHandler.getTemperature() * 100);
				case 3:
					return BloomeryTileEntity.this.air;
				case 4:
					return BloomeryTileEntity.this.bloomProgress;
				case 5:
					return BloomeryTileEntity.this.totalBloomProgress;
			}
			return index;
		}
	};
	
	private boolean isBurning() {
		return this.burnTime > 0;
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
	
	public BloomeryTileEntity() {
		super(Registration.BLOOMERY_TILEENTITY.get());
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(4) {

			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
			}

			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				if (slot == 3) {
					if (ForgeHooks.getBurnTime(stack) > 0) {
						return true;
					}
				} else if (slot == 1) {
					if (ModTags.Items.CHARCOAL.contains(stack.getItem())) {
						return true;
					}
				} else if (slot == 0) {
					if (Tags.Items.ORES_IRON.contains(stack.getItem())) {
						return true;
					}
				}
				return false;
			}
			
		};
	}

	@Override
	public void tick() {
		boolean needsUpdating = false;
		boolean burning = this.isBurning();
		
		if (!this.multiBlockFormed) {
			return;
		}
		
		if (this.isBurning()) {
			this.burnTime--;
		}
		
		if (this.air > 0) {
			this.air--;
		}
		
		if (!this.world.isRemote) {
			
			if (this.air >= MAX_AIR_CAPACITY) {
				this.air = MAX_AIR_CAPACITY;
				needsUpdating = true;
			}
			
			ItemStack is = this.itemHandler.getStackInSlot(3);
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
				
				if (this.isBurning() && this.canSmelt()) {
					++this.bloomProgress;
					if (this.bloomProgress == this.totalBloomProgress) {
						this.bloomProgress = 0;
						this.totalBloomProgress = 200;
						this.smelt();
						needsUpdating = true;
					}
				}else{
					this.bloomProgress = 0;
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
                this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(BlockStateProperties.LIT, Boolean.valueOf(this.isBurning())), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
            }
		}
		
		if (needsUpdating)
        {
            this.markDirty();
        }
	}
	
	public void pullAirFromBellows() {
		if (this.air < MAX_AIR_CAPACITY) {
			air += AIR_AMOUNT_PER_BELLOWS_PUMP;
		}else if (this.air >= MAX_AIR_CAPACITY){
			air = MAX_AIR_CAPACITY;
		}
	}
	
	private boolean canSmelt() {
		if (!this.itemHandler.getStackInSlot(0).isEmpty() && !this.itemHandler.getStackInSlot(1).isEmpty() && this.air > 0 && this.heatHandler.temperature >= 800) {
			ItemStack stackInInputSlot = this.itemHandler.getStackInSlot(0);
			ItemStack stackInCharcoalSlot = this.itemHandler.getStackInSlot(1);
			if (!ModTags.Items.CHARCOAL.contains(stackInCharcoalSlot.getItem())) {
				return false;
			}
			if (!Tags.Items.ORES_IRON.contains(stackInInputSlot.getItem())) {
				return false;
			}
			ItemStack stackInOutputSlot = this.itemHandler.getStackInSlot(2);
			ItemStack output = new ItemStack(Registration.IRON_BLOOM.get());
			if (stackInOutputSlot.isEmpty()) {
				return true;
			}else if(!stackInOutputSlot.isItemEqual(output)) {
				return false;
			}else if (stackInOutputSlot.getCount() + output.getCount() <= 64 && stackInOutputSlot.getCount() + output.getCount() <= stackInOutputSlot.getMaxStackSize()) {
				return true;
			}else{
				return stackInOutputSlot.getCount() + output.getCount() <= stackInOutputSlot.getMaxStackSize();
			}
		}
		return false;
	}
	
	private void smelt() {
		if (this.canSmelt()) {
			ItemStack stackInInputSlot = this.itemHandler.getStackInSlot(0);
			ItemStack stackInCharcoalSlot = this.itemHandler.getStackInSlot(1);
			ItemStack recipeOutput = new ItemStack(Registration.IRON_BLOOM.get());
			ItemStack stackInOutputSlot = this.itemHandler.getStackInSlot(2);
			if (stackInOutputSlot.isEmpty()) {
				this.itemHandler.setStackInSlot(2, recipeOutput.copy());
			}else if (stackInOutputSlot.getItem() == recipeOutput.getItem()) {
				stackInOutputSlot.grow(recipeOutput.getCount());
			}
			
			stackInInputSlot.shrink(1);
			stackInCharcoalSlot.shrink(1);
		}
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			return handler.cast();
		}
		
		if (cap.equals(HeatCapability.HEAT_CAPABILITY)) {
			return heatCap.cast();
		}
		
		return super.getCapability(cap);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		itemHandler.deserializeNBT(compound.getCompound("inventory"));
		heatHandler.deserializeNBT(compound.getCompound("realistech:heatData"));
		this.burnTime = compound.getInt("burnTime");
		this.currentItemBurnTime = compound.getInt("currentItemBurnTime");
		this.air = compound.getInt("realistech:airAmount");
		this.bloomProgress = compound.getInt("bloomProgress");
		super.read(compound);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("inventory", itemHandler.serializeNBT());
		compound.put("realistech:heatData", heatHandler.serializeNBT());
		compound.putInt("burnTime", this.burnTime);
		compound.putInt("currentItemBurnTime", this.currentItemBurnTime);
		compound.putInt("realistech:airAmount", this.air);
		compound.putInt("bloomProgress", this.bloomProgress);
		return super.write(compound);
	}
}
