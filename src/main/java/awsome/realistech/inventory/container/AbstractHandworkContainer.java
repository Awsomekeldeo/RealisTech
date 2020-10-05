package awsome.realistech.inventory.container;

import java.util.Optional;

import awsome.realistech.api.recipe.AbstractHandworkRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractHandworkContainer extends Container {
	
	private IItemHandler playerInventory;
	private ItemStackHandler craftingInventory;
	private ItemStackHandler craftResultInventory;
	private World playerWorld;
	public int buttonTextureX;
	public int buttonTextureY;
	public int buttonXDiffTex;
	public int buttonYDiffTex;
	protected final IRecipeType<? extends AbstractHandworkRecipe> recipeType;
	private PlayerEntity player;
	private boolean hasClickedButton = false;
	private int handSlot;
	private ItemStack stackInSelectedSlot;
	private int amountConsumed;
	private boolean hasConsumedItem = false;
	private boolean shouldConsumeOnClick = false;
	
	public AbstractHandworkContainer(ContainerType<?> containerType, int windowId, PlayerInventory inventory, IRecipeType<? extends AbstractHandworkRecipe> recipeType, int buttonTextureX, int buttonTextureY,
			int buttonXDiffTex, int buttonYDiffTex, Item containerItem, int amountConsumed, boolean consumeOnClick) {
		super(containerType, windowId);
		this.playerInventory = new InvWrapper(inventory);
		this.craftingInventory = new ItemStackHandler(25) {
			
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return false;
			}
			
		};
		
		this.craftResultInventory = new ItemStackHandler(1) {
			
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return false;
			}
			
		};
		
		this.buttonTextureX = buttonTextureX;
		this.buttonTextureY = buttonTextureY;
		this.buttonXDiffTex = buttonXDiffTex;
		this.buttonYDiffTex = buttonYDiffTex;
		this.recipeType = recipeType;
		this.playerWorld = inventory.player.world;
		this.player = inventory.player;
		this.handSlot = player.inventory.currentItem;
		this.stackInSelectedSlot = player.inventory.getCurrentItem();
		this.amountConsumed = amountConsumed;
		this.shouldConsumeOnClick = consumeOnClick;
		
		layoutPlayerInventorySlots(8,112);
		addSlot(new SlotItemHandler(craftResultInventory, 0, 139, 49) {
			
			@Override
        	public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        		ForgeHooks.setCraftingPlayer(thePlayer);
        		RecipeWrapper recipeWrapper = new RecipeWrapper(craftingInventory);
        		@SuppressWarnings("unchecked")
				NonNullList<ItemStack> stacks = thePlayer.world.getRecipeManager().getRecipeNonNull((IRecipeType<AbstractHandworkRecipe>)recipeType, recipeWrapper, thePlayer.world);
        		ForgeHooks.setCraftingPlayer(null);
        		ItemStack recipeItemStack = stackInSelectedSlot;
        		for (int i = 0; i < stacks.size(); i++) {
        			ItemStack slotStack = craftingInventory.getStackInSlot(i);
        			ItemStack compareStack = stacks.get(i);
        			
        			if (!slotStack.isEmpty()) {
        				craftingInventory.extractItem(i, 1, false);
        				slotStack = craftingInventory.getStackInSlot(i);
        			}
        			
        			if (!compareStack.isEmpty()) {
        				if (slotStack.isEmpty()) {
        					craftingInventory.setStackInSlot(i, compareStack);
        				} else if (ItemStack.areItemStacksEqual(slotStack, compareStack) && ItemStack.areItemStackTagsEqual(slotStack, compareStack)) {
        					craftingInventory.setStackInSlot(i, compareStack);
        				} else if (!thePlayer.inventory.addItemStackToInventory(compareStack)) {
        					thePlayer.dropItem(compareStack, false);
        				}
        			}
        			
        		}
        		
        		if (!recipeItemStack.isEmpty() && !shouldConsumeOnClick) {
    				playerInventory.extractItem(handSlot, amountConsumed, false);
    			}
        		
        		return stack;
        	}
        	
		});
		layoutCraftingSlots(21, 17);
		fillCraftingSlots(containerItem);
	}
	
	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
		return slotId == this.handSlot ? ItemStack.EMPTY : super.slotClick(slotId, dragType, clickTypeIn, player);
	}
	
	public int getHandSlot() {
		return this.handSlot;
	}
	
	public void setButtonClicked(boolean clicked) {
		this.hasClickedButton = clicked;
	}
	
	private void fillCraftingSlots(Item item) {
		for (int i = 0; i < this.craftingInventory.getSlots(); i++) {
			this.putStackInSlot(i + 37, new ItemStack(item));
		}
	}
	
	private void updateOutput(World worldIn, PlayerEntity playerIn) {
		if (!worldIn.isRemote) {
			ItemStack itemstack = ItemStack.EMPTY;
			RecipeWrapper recipeWrapper = new RecipeWrapper(this.craftingInventory);
			@SuppressWarnings("unchecked")
			Optional<AbstractHandworkRecipe> optional = worldIn.getRecipeManager().getRecipe((IRecipeType<AbstractHandworkRecipe>)this.recipeType, recipeWrapper, worldIn);
			if (optional.isPresent()) {
				AbstractHandworkRecipe recipe = optional.get();
				itemstack = recipe.getCraftingResult(recipeWrapper);
			}
			
			this.putStackInSlot(36, itemstack);
		}
	}
	
	public void consumeRecipeItem(World worldIn) {
		if (!worldIn.isRemote) {
			if (this.hasClickedButton && !this.hasConsumedItem && this.shouldConsumeOnClick) {
				stackInSelectedSlot.shrink(this.amountConsumed);
				this.hasConsumedItem = true;
			}
		}
	}

	@Override
	public void detectAndSendChanges() {
		this.updateOutput(this.playerWorld, this.player);
		this.consumeRecipeItem(this.playerWorld);
		super.detectAndSendChanges();
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
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
                if (index < 28) {
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
	
	private int addFakeSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y) {
            	
            	@Override
            	public boolean isEnabled() {
            		return false;
            	}
            	
            });
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
	
	private void layoutPlayerInventorySlots(int leftCol, int topRow) {
		// Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
		
		// Player inventory
        topRow -= 58;
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
    }
	
	private void layoutCraftingSlots(int leftCol, int topRow) {
		addFakeSlotBox(craftingInventory, 0, leftCol, topRow, 5, 16, 5, 16);
	}
}
