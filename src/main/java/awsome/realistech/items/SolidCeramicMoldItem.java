package awsome.realistech.items;

import java.util.List;
import java.util.Map;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.api.recipe.SolidificationRecipe;
import awsome.realistech.capability.FilledMoldCapabilityWrapper;
import awsome.realistech.util.GeneralUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SolidCeramicMoldItem extends Item {
	
	private Item emptyMold;
	
	public SolidCeramicMoldItem() {
		super(new Item.Properties().maxStackSize(1));
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		Item prevItem = stack.getItem();
		if (prevItem instanceof CeramicMoldItem) {
			return new FilledMoldCapabilityWrapper(((CeramicMoldItem) prevItem).getCapacity(), stack);
		}else{
			return new FilledMoldCapabilityWrapper(144, stack);
		}
	}
	
	public void setEmptyMold(Item empty) {
		this.emptyMold = empty;
	}
	
	public ItemStack getEmptyMold() {
		if (emptyMold != null) {
			return new ItemStack(emptyMold);
		}else{
			return ItemStack.EMPTY;
		}
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if (!worldIn.isRemote) {
			ItemStack stack = playerIn.getHeldItem(handIn);
			if (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
				if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
					IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
					
					float temperature = h.getTemperature();
					
					if (temperature > 80) {
						playerIn.attackEntityFrom(DamageSource.GENERIC, 5.0f);
					}
					
				}
				IItemHandler h = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
				
				if (stack.getItem() instanceof SolidCeramicMoldItem) {
					ItemHandlerHelper.giveItemToPlayer(playerIn, h.getStackInSlot(0));
					SolidCeramicMoldItem moldItem = (SolidCeramicMoldItem) stack.getItem();
					playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, moldItem.getEmptyMold());
				}
				return ActionResult.resultSuccess(stack);
			}
		}
		return ActionResult.resultPass(playerIn.getHeldItem(handIn));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			if (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
				if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
					
					IItemHandler h = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
					IFluidHandlerItem h2 = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
					
					for (IRecipe<?> recipe : this.getRecipes(SolidificationRecipe.SOLIDIFICATION_RECIPE, worldIn.getRecipeManager()).values()) {
						if (recipe instanceof SolidificationRecipe) {
							SolidificationRecipe solidificationRecipe = (SolidificationRecipe) recipe;
							if (solidificationRecipe.isValid(h2)) {
								ItemStack recipeOutput = solidificationRecipe.getRecipeOutput().copy();
								h.insertItem(0, recipeOutput, false);
								h2.drain(solidificationRecipe.getInput(), FluidAction.EXECUTE);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TranslationTextComponent("tooltip.realistech.ceramic_molds.solidified").applyTextStyles(TextFormatting.GREEN, TextFormatting.ITALIC));
	}
	
	private Map<ResourceLocation, IRecipe<?>> getRecipes (IRecipeType<?> recipeType, RecipeManager manager) {
        final Map<IRecipeType<?>, Map<ResourceLocation, IRecipe<?>>> recipesMap = ObfuscationReflectionHelper.getPrivateValue(RecipeManager.class, manager, "field_199522_d");
        return recipesMap.get(recipeType);
    }
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}

	public double getTemperatureForDisplay(ItemStack stack) {
		if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
			IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			if (stack.getTag().contains("realistech:meltTemp")) {
				return h.getTemperature() < stack.getTag().getFloat("realistech:meltTemp") ? h.getTemperature() / stack.getTag().getFloat("realistech:meltTemp") : 1;
			}
			return 1 - h.getTemperature() / h.getMaxTemperature();
		}
		return 1.0d;
	}

	public int getRGBForTemperatureDisplay(ItemStack stack) {
		int color = 0x00FFFFFF;
		if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
			IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			color = GeneralUtils.getTempFromColorMap(h.getTemperature());
		}
		return color;
	}
	
	@Override
	public CompoundNBT getShareTag(ItemStack stack) {
		CompoundNBT nbt = stack.getOrCreateTag();
		stack.getCapability(HeatCapability.HEAT_CAPABILITY).ifPresent(h -> {
			nbt.put("realistech:heatData", HeatCapability.HEAT_CAPABILITY.writeNBT(h, null));
		});
		return nbt;
	}
	
	@Override
	public void readShareTag(ItemStack stack, CompoundNBT nbt) {
		super.readShareTag(stack, nbt);
		if (nbt != null) {
			stack.getCapability(HeatCapability.HEAT_CAPABILITY).ifPresent(h -> {
				HeatCapability.HEAT_CAPABILITY.readNBT(h, null, nbt.get("realistech:heatData"));
			});
		}
	}

	public boolean shouldShowTemperatureBar(ItemStack stack) {
		if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
			IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			
			
			if (h.getTemperature() > 80) {
				return true;
			}
			
		}
		
		return false;
	}
}
