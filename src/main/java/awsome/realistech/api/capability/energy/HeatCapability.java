package awsome.realistech.api.capability.energy;

import awsome.realistech.api.capability.energy.IHeat.EnumThermalMode;
import awsome.realistech.api.capability.energy.IHeat.EnumThermalState;
import awsome.realistech.api.capability.impl.HeatHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class HeatCapability {
	
	@CapabilityInject(IHeat.class)
	public static Capability<IHeat> HEAT_CAPABILITY = null;
	
	public static void init() {
		CapabilityManager.INSTANCE.register(IHeat.class, new Capability.IStorage<IHeat>() {
			@Override
			public INBT writeNBT(Capability<IHeat> capability, IHeat instance, Direction side) {
				CompoundNBT tag = new CompoundNBT();
				tag.putFloat("temperature", instance.getTemperature());
				tag.putFloat("max_temperature", instance.getMaxTemperature());
				tag.putByte("thermal_state", instance.getThermalState().getValueForNBT());
				tag.putByte("thermal_mode", instance.getThermalMode().getValueForNBT());
				return tag;
			}

			@Override
			public void readNBT(Capability<IHeat> capability, IHeat instance, Direction side, INBT nbt) {
				CompoundNBT tag = (CompoundNBT) nbt;
				instance.setTemp(tag.getFloat("temperature"));
				instance.setMaxTemperature(tag.getFloat("max_temperature"));
				instance.setThermalMode(EnumThermalMode.getValueFromNBT(tag.getByte("thermal_mode")));
				instance.setThermalState(EnumThermalState.getValueFromNBT(tag.getByte("thermal_state")));
			}
		}, () -> new HeatHandler(null, 0, false));
	}
	
}
