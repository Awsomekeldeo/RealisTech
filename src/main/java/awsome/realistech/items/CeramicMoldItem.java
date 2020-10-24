package awsome.realistech.items;

import awsome.realistech.api.capability.energy.HeatCapability;
import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.capability.MoldCapabilityWrapper;
import awsome.realistech.registry.Registration;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.util.GeneralUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

public class CeramicMoldItem extends Item {
	
	private int capacity;
	private Item solidMold;
	
	public CeramicMoldItem(int capacity) {
		super(new Item.Properties().group(ModSetup.REALISTECH_MISC).maxStackSize(1));
		this.capacity = capacity;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public Item getSolidMold() {
		return this.solidMold;
	}
	
	public void setSolidMold(Item solid) {
		this.solidMold = solid;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote) {
			if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
				if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
					IFluidHandlerItem h = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
					IHeat h2 = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);

					float temperature = h2.getTemperature();

					if (temperature > h2.getBaseTempBasedOnBiome(null)) {
						h2.cool();
					}
					
					if (temperature < h.getFluidInTank(0).getFluid().getAttributes().getTemperature()) {
						if (stack.getItem() instanceof CeramicMoldItem) {
							if (entityIn instanceof PlayerEntity) {
								PlayerEntity player =  (PlayerEntity) entityIn;
								CeramicMoldItem moldItem = (CeramicMoldItem) stack.getItem();
								ItemStack newStack = new ItemStack(moldItem.getSolidMold(), stack.getCount());
								CompoundNBT nbt2 = stack.getTag();

								if (!h.getFluidInTank(0).getFluid().equals(Registration.MOLTEN_IRON.get())) {
									nbt2.putInt("realistech:itemColor", h.getFluidInTank(0).getFluid().getAttributes().getColor());
								}else{
									nbt2.putInt("realistech:itemColor", 0xFFD8D8D8);
								}
								
								nbt2.putFloat("realistech:meltTemp", h.getFluidInTank(0).getFluid().getAttributes().getTemperature());

								newStack.setTag(nbt2);
								player.inventory.setInventorySlotContents(itemSlot, newStack);
							}
						}
					}
				}
			}
		}
	}
	
	public int getRGBForTemperatureDisplay(ItemStack stack) {
		int color = 0xFFFFFFFF;
		if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
			IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			color = GeneralUtils.getTempFromColorMap(h.getTemperature());
		}
		return color;
	}
	
	public double getTemperatureForDisplay(ItemStack stack) {
		if (stack.getCapability(HeatCapability.HEAT_CAPABILITY).isPresent()) {
			IHeat h = stack.getCapability(HeatCapability.HEAT_CAPABILITY).orElse(null);
			if (stack.getTag().contains("realistech:meltTemp")) {
				return h.getTemperature() < stack.getTag().getFloat("realistech:meltTemp") ? h.getTemperature() / stack.getTag().getFloat("realistech:meltTemp") : 1;
			}
			return h.getTemperature() / h.getMaxTemperature();
		}
		return 1.0d;
	}
	
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return false;
	}
	
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
		return new MoldCapabilityWrapper(capacity, stack);
	}
	
	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		return 0xFFFFFFFF;
	}
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		
		
		if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
			IFluidHandlerItem h = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			
			if (h.getFluidInTank(0).isEmpty()) {
				return false;
			}
			
			if ((double) h.getFluidInTank(0).getAmount() / (double) h.getTankCapacity(0) == 1.00) {
				return false;
			}
			
			return true;
		}
		
		return false;
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
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		
		if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
			IFluidHandlerItem h = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			
			return (double) 1 - (h.getFluidInTank(0).getAmount() / (double) h.getTankCapacity(0));
		}
		
		return 1.00d;
	}

	public boolean shouldShowTemperatureBar(ItemStack stack) {
		
		if (stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent()) {
			IFluidHandlerItem h = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(null);
			
			if (h.getFluidInTank(0).isEmpty()) {
				return false;
			}
			
			return true;
		}
		
		return false;
		
	}
}
