package awsome.realistech.api.capability.impl;

import awsome.realistech.api.capability.energy.IHeat;
import awsome.realistech.util.MathUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class HeatHandlerItemStack implements IHeat {
	
	public ItemStack container;
	public float temperature = 0;
	public float maxTemperature;
	public float coolingRate;
	public float heatingRate;
	private EnumThermalState thermalState;
	private EnumThermalMode thermalMode;
	public float specificHeat;
	
	public HeatHandlerItemStack(ItemStack container, float maxTemp, float heatingRate, float coolingRate) {
		this.container = container;
		this.maxTemperature = maxTemp;
		this.heatingRate = heatingRate;
		this.coolingRate = coolingRate;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
	}

	@Override
	public boolean drawHeatFromSide(BlockPos pos, Direction side) {
		return false;
	}

	@Override
	public float getTemperature() {
		//this.readData();
		if (this.temperature < getBaseTempBasedOnBiome(null) && thermalMode != EnumThermalMode.COOLING) {
			return getBaseTempBasedOnBiome(null);
		}else{
			return MathUtil.roundFloat(temperature, 2);	
		}
	}

	@Override
	public float getMaxTemperature() {
		//this.readData();
		return this.maxTemperature;
	}

	@Override
	public float changeTemp(float amount) {
		//this.readData();
		float oldTemp = this.getTemperature();
		float newTemp = (maxTemperature - oldTemp < amount) ? maxTemperature : (oldTemp + amount);
		setTemp(newTemp);
		if (this.temperature <= this.getBaseTempBasedOnBiome(null) && this.thermalMode != EnumThermalMode.COOLING) {
			this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
		}
		//this.writeData();
		return newTemp - oldTemp;
	}

	@Override
	public EnumThermalMode getThermalMode() {
		//this.readData();
		return this.thermalMode;
	}

	@Override
	public void setTemp(float temp) {
		//this.readData();
		if (temp < getBaseTempBasedOnBiome(null) && thermalMode != EnumThermalMode.COOLING) {
			this.temperature = getBaseTempBasedOnBiome(null);
		}else{
			this.temperature = MathUtil.roundFloat(temp, 2);
		}
		//this.writeData();
	}

	@Override
	public boolean canTransmitHeat() {
		return false;
	}

	@Override
	public float getBaseTempBasedOnBiome(BlockPos pos) {
		return 27.0f;
	}

	@Override
	public void setThermalMode(EnumThermalMode mode) {
		//this.readData();
		this.thermalMode = mode;
		//this.writeData();
	}

	@Override
	public void cool() {
		//this.readData();
		
		this.decreaseTemp(this.coolingRate);
		if (this.canTransmitHeat()) {
			if (this.thermalMode != EnumThermalMode.COOLING && (this.temperature > this.getBaseTempBasedOnBiome(null))) {
				this.thermalState = EnumThermalState.APPROACHING_EQUILIBRIUM;
			}else if (this.temperature < this.getBaseTempBasedOnBiome(null)) {
				this.thermalState = EnumThermalState.WORKING;
			}else {
				this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
			}
		}
		
		//this.writeData();
	}

	@Override
	public void heat() {
		//this.readData();
		
		this.increaseTemp(this.heatingRate);
		if (this.canTransmitHeat()) {
			if (this.thermalMode == EnumThermalMode.COOLING && (this.temperature < this.getBaseTempBasedOnBiome(null))) {
				this.thermalState = EnumThermalState.APPROACHING_EQUILIBRIUM;
			}else if (this.temperature > this.getBaseTempBasedOnBiome(null)){
				this.thermalState = EnumThermalState.WORKING;
			}else{
				this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
			}
		}
		
		//this.writeData();
	}

	@Override
	public float getRate(EnumThermalMode mode) {
		//this.readData();
		
		switch (mode) {
			case HEATING:
				return this.heatingRate;
			case COOLING:
				return this.coolingRate;
			default:
				return 0;
		}
	}

	@Override
	public void setRate(EnumThermalMode mode, float rate) {
		//this.readData();
		
		switch (mode) {
			case HEATING:
				this.heatingRate = rate;
			case COOLING:
				this.coolingRate = rate;
		}
		
		//this.writeData();
	}

	@Override
	public EnumThermalState getThermalState() {
		//this.readData();
		return this.thermalState;
	}

	public CompoundNBT writeData() {
		
		if (!container.hasTag()) {
			container.setTag(new CompoundNBT());
		}
		
		CompoundNBT heatData = new CompoundNBT();
		heatData.putFloat("temperature", this.getTemperature());
		heatData.putFloat("max_temperature", this.getMaxTemperature());
		heatData.putByte("thermal_state", this.getThermalState().getValueForNBT());
		heatData.putByte("thermal_mode", this.getThermalMode().getValueForNBT());
		container.getTag().put("realistech:heatData", heatData);
		
		return heatData;
	}

	public void readData() {
		CompoundNBT nbtBase = container.getTag();
		if (nbtBase.contains("realistech:heatData")) {
			CompoundNBT nbt = nbtBase.getCompound("realistech:heatData");
			temperature = nbt.getFloat("temperature");
			maxTemperature = nbt.getFloat("max_temperature");
			thermalState = EnumThermalState.getValueFromNBT(nbt.getByte("thermal_state"));
			thermalMode = EnumThermalMode.getValueFromNBT(nbt.getByte("thermal_mode"));
		}
	}

	@Override
	public void setMaxTemperature(float temp) {
		this.maxTemperature = temp;
	}

	@Override
	public void setThermalState(EnumThermalState valueFromNBT) {
		this.thermalState = valueFromNBT;
	}

}
