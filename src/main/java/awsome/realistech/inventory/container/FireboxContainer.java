package awsome.realistech.inventory.container;

import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.FireboxTileEntity;
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

public class FireboxContainer extends Container {
	
	public TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private IIntArray fireboxData;
	public float temperature;

	public FireboxContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {
		super(Registration.FIREBOX_CONTAINER.get(), windowId);
		tileEntity = world.getTileEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 80, 49));
			});
			this.fireboxData = ((FireboxTileEntity)tileEntity).fireboxData;
		}
		
		layoutPlayerInventorySlots(8,84);
		trackBurnTime();
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Registration.FIREBOX.get());
	}
	
	private void trackBurnTime() {
		trackIntArray(fireboxData);
	}
	
	public int getBurnTime() {
		return ((FireboxTileEntity)tileEntity).getBurnTime();
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
	
	@OnlyIn(Dist.CLIENT)
	public int getBurnLeftScaled() {
		int i = this.fireboxData.get(1);
		if (i == 0) {
			i = 200;
		}
		return this.fireboxData.get(0) * 13 / i;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.fireboxData.get(0) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getTemperature() {
		return (this.fireboxData.get(2) / 100.0f);
	}
	
	@OnlyIn(Dist.CLIENT) 
	public int getTemperatureScaled() {
		return (int) ((this.fireboxData.get(2) / 100.0f) + 3) * 63 / 1773;
	}
}
