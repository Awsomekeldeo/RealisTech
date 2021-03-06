package awsome.realistech.inventory.container;

import awsome.realistech.api.recipe.KilnRecipe;
import awsome.realistech.registry.Registration;
import awsome.realistech.tileentity.KilnTileEntity;
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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class KilnContainer extends Container {
	
	public TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private IIntArray kilnData;
	public float temperature;
	private World world;
	
	public KilnContainer(int windowId, World world, BlockPos pos, PlayerInventory inventory, PlayerEntity player) {
		super(Registration.KILN_CONTAINER.get(), windowId);
		tileEntity = world.getTileEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inventory);
		this.world = world;
		
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 29, 19));
				addSlot(new SlotItemHandler(h, 1, 47, 19));
				addSlot(new SlotItemHandler(h, 2, 29, 37));
				addSlot(new SlotItemHandler(h, 3, 47, 37));
				addSlot(new SlotItemHandler(h, 4, 38, 73));
				addSlot(new SlotItemHandler(h, 5, 103, 42));
				addSlot(new SlotItemHandler(h, 6, 121, 42));
				addSlot(new SlotItemHandler(h, 7, 103, 60));
				addSlot(new SlotItemHandler(h, 8, 121, 60));
			});
			this.kilnData = ((KilnTileEntity)tileEntity).kilnData;
		}
		
		layoutPlayerInventorySlots(8,103);
		trackKilnData();
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Registration.KILN.get());
	}
	
	private void trackKilnData() {
		trackIntArray(kilnData);
	}
	
	public int getBurnTime() {
		return ((KilnTileEntity)tileEntity).getBurnTime();
	}
	
	private boolean hasRecipe(ItemStack stack) {
		IItemHandlerModifiable recipeItemHandler = new ItemStackHandler();
		recipeItemHandler.setStackInSlot(0, stack);
		RecipeWrapper recipeWrapper = new RecipeWrapper(recipeItemHandler);
		return this.world.getRecipeManager().getRecipe(KilnRecipe.KILN_RECIPE, recipeWrapper, this.world).isPresent();
	}
	
	@Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();
            if (index >= 5 && index <= 8) {
                if (!this.mergeItemStack(stack, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(stack, itemstack);
            } else if (index > 8) {
            	if (this.hasRecipe(stack)) {
            		if (!this.mergeItemStack(stack, 0, 4, false)) {
            			return ItemStack.EMPTY;
            		}
            	}
                if (ForgeHooks.getBurnTime(stack) > 0) {
                    if (!this.mergeItemStack(stack, 4, 5, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 9 && index < 36) {
                    if (!this.mergeItemStack(stack, 36, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 36 && index < 45 && !this.mergeItemStack(stack, 4, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.mergeItemStack(stack, 9, 45, false)) {
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
		int i = this.kilnData.get(1);
		if (i == 0) {
			i = 200;
		}
		return this.kilnData.get(0) * 13 / i;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isBurning() {
		return this.kilnData.get(0) > 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public float getTemperature() {
		return (this.kilnData.get(2) / 100.0f);
	}
	
	@OnlyIn(Dist.CLIENT) 
	public int getTemperatureScaled() {
		return (int) ((this.kilnData.get(2) / 100.0f) + 3) * 63 / 1773;
	}
	
	@OnlyIn(Dist.CLIENT)
	public int getFireProgressScaled() {
		int i = this.kilnData.get(3);
		int j = this.kilnData.get(4);
		return j != 0 && i != 0 ? i * 24 / j : 0;
	}
}
