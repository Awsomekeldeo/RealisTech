package awsome.realistech.inventory.container;

import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.BloomeryTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BloomeryContainer extends Container {
	
	public TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private IIntArray bloomeryData;
	public float temperature;
	public boolean multiblockFormed;

	public BloomeryContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player, boolean multiblockFormed) {
		super(Registration.BLOOMERY_CONTAINER.get(), windowId);
		tileEntity = world.getTileEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		this.multiblockFormed = multiblockFormed;
		
		if (tileEntity != null) {
			if (!this.multiblockFormed) {
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					addFakeSlot(h, 0, 35, 34);
					addFakeSlot(h, 1, 79, 34);
					addFakeSlot(h, 2, 57, 67);
					addFakeSlot(h, 3, 118, 73);
				});
				
				layoutFakePlayerInventorySlots(8,103);
				
			}else{
				tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					//Ore Input
					addSlot(new SlotItemHandler(h, 0, 35, 34));
					
					//Charcoal Input
					addSlot(new SlotItemHandler(h, 1, 79, 34));
					
					//Output
					addSlot(new SlotItemHandler(h, 2, 57, 67));
					
					//Fuel Input
					addSlot(new SlotItemHandler(h, 3, 118, 73));
				});
				
				layoutPlayerInventorySlots(8,103);
			}
			
			this.bloomeryData = ((BloomeryTileEntity)tileEntity).bloomeryData;
		}
		
		trackBurnTime();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Registration.BLOOMERY_CONTROLLER.get());
	}
	
	private void trackBurnTime() {
		trackIntArray(bloomeryData);
	}
	
	public int getBurnTime() {
		return ((BloomeryTileEntity)tileEntity).getBurnTime();
	}
	
	public void addFakeSlot(IItemHandler handler, int index, int x, int y) {
		addSlot(new SlotItemHandler(handler, index, x, y) {
			
			@Override
			public boolean canTakeStack(PlayerEntity playerIn) {
				return false;
			}
			
			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}
			
		});
	}
	
	private int addFakeSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addFakeSlot(handler, index, x, y);
            x += dx;
            index++;
        }
        return index;
    }
	
	private int addFakeSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addFakeSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
	
	private void layoutFakePlayerInventorySlots(int leftCol, int topRow) {
		// Hotbar
        topRow += 58;
        addFakeSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
		
		// Player inventory
        topRow -= 58;
        addFakeSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
    }
	
	
	@Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 0) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else {
                if (ForgeHooks.getBurnTime(stack) > 0) {
                    if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.mergeItemStack(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.mergeItemStack(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (stack.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, stack);
        }

        return itemstack;
    }
	
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }
	
	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
	
	private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
	
	public TileEntity getTileEntity() {
		return this.tileEntity;
	}
	
	public int getAir() {
		return this.bloomeryData.get(3);
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getAirScaled() {
		if (this.bloomeryData.get(3) > 600) {
			return 76;
		}
		return (this.bloomeryData.get(3) * 76) / 600;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getBloomProgress() {
		return this.bloomeryData.get(4);
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getBloomProgressScaled() {
		int i = this.bloomeryData.get(4);
		int j = this.bloomeryData.get(5);
		
		return (int) (24 * (i / (float)j));
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		int i = this.bloomeryData.get(1);
		if (i == 0) {
			i = 200;
		}
		return this.bloomeryData.get(0) * 13 / i;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.bloomeryData.get(0) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getTemperature() {
		return (this.bloomeryData.get(2) / 100.0f);
	}
	
	@OnlyIn(Dist.CLIENT) 
	public int getTemperatureScaled() {
		return (int) ((this.bloomeryData.get(2) / 100.0f) + 3) * 63 / 1200;
	}
	
}
