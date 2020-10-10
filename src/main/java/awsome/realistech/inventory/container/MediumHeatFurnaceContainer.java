package awsome.realistech.inventory.container;

import awsome.realistech.api.recipe.WeakSmeltingRecipe;
import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.MediumHeatFurnaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MediumHeatFurnaceContainer extends Container {
	
	public TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private IIntArray furnaceData;
	private World world;

	public MediumHeatFurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {
		super(Registration.WEAK_FURNACE_CONTAINER.get(), windowId);
		tileEntity = world.getTileEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		this.world = world;
		
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 56, 17));
				addSlot(new SlotItemHandler(h, 1, 56, 53));
				addSlot(new SlotItemHandler(h, 2, 116, 35));
			});
			this.furnaceData = ((MediumHeatFurnaceTileEntity)tileEntity).mediumHeatFurnaceData;
		}
		
		
		layoutPlayerInventorySlots(8, 84);
		trackFurnaceData();
	}
	
	private boolean hasRecipe(ItemStack stack) {
		IItemHandlerModifiable recipeItemHandler = new ItemStackHandler();
		recipeItemHandler.setStackInSlot(0, stack);
		RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
		return this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, recipeWrapper, this.world).isPresent() || this.world.getRecipeManager().getRecipe(WeakSmeltingRecipe.WEAK_SMELTING_RECIPE, recipeWrapper, this.world).isPresent();
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index == 2) {
                if (!this.mergeItemStack(stack, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (index != 1 && index != 0) {
            	if (this.hasRecipe(stack)) {
            		if (!this.mergeItemStack(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
            	} else if (ForgeHooks.getBurnTime(stack) > 0) {
                    if (!this.mergeItemStack(stack, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(stack, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(stack, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 3, 39, false)) {
            	return ItemStack.EMPTY;
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
	
	private void trackFurnaceData() {
		trackIntArray(furnaceData);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Registration.WEAK_FURNACE.get());
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
		int i = this.furnaceData.get(1);
		if (i == 0) {
			i = 200;
		}
		return this.furnaceData.get(0) * 13 / i;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.furnaceData.get(0) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getSmeltProgressScaled() {
		int i = this.furnaceData.get(2);
		int j = this.furnaceData.get(3);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}
	
}
