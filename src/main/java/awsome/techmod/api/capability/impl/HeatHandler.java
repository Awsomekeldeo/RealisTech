package awsome.techmod.api.capability.impl;

import awsome.techmod.api.capability.energy.CapabilityHeat;
import awsome.techmod.api.capability.energy.IHeat;
import awsome.techmod.util.MathUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutable;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * A default implementation of {@link IHeat}
 * @author Awsomekeldeo
 */
public class HeatHandler implements IHeat, INBTSerializable<CompoundNBT> {
	public TileEntity te;
	public float temperature = 0;
	public float maxTemperature;
	public float coolingRate;
	public float heatingRate;
	public boolean transmitsHeat;
	private EnumThermalState thermalState;
	private EnumThermalMode thermalMode;
	public float specificHeat;
	
	public HeatHandler(TileEntity te, float maxTemp, boolean canTransmitHeat, float coolingRate) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		this.coolingRate = coolingRate;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
	}
	
	public HeatHandler(TileEntity te, float maxTemp, boolean canTransmitHeat) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
	}
	
	public HeatHandler(TileEntity te, float maxTemp, boolean canTransmitHeat, float heatingRate, float coolingRate) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		this.coolingRate = coolingRate;
		this.heatingRate = heatingRate;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
	}
	
	public HeatHandler(float specificHeat, TileEntity te, float maxTemp, boolean canTransmitHeat, float heatingRate, float coolingRate) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		this.coolingRate = coolingRate;
		this.heatingRate = heatingRate;
		this.specificHeat = specificHeat;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
	}
	
	public HeatHandler(float specificHeat, TileEntity te, float maxTemp, boolean canTransmitHeat, float coolingRate) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		this.coolingRate = coolingRate;
		this.thermalMode = EnumThermalMode.HEATING;
		this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
		this.specificHeat = specificHeat;
	}
	
	@Override
	public void setTemp(float temp) {
		if (temp < getBaseTempBasedOnBiome(this.te.getPos()) && thermalMode != EnumThermalMode.COOLING) {
			this.temperature = getBaseTempBasedOnBiome(te.getPos());
		}else{
			this.temperature = MathUtil.roundFloat(temp, 2);
		}
	}
	
	@Override
	public float getTemperature() {
		if (this.temperature < getBaseTempBasedOnBiome(this.te.getPos()) && thermalMode != EnumThermalMode.COOLING) {
			return getBaseTempBasedOnBiome(te.getPos());
		}else{
			return MathUtil.roundFloat(temperature, 2);	
		}
	}

	@Override
	public float getMaxTemperature() {
		return this.maxTemperature;
	}

	@Override
	public float changeTemp(float amount) {
		float oldTemp = this.getTemperature();
		float newTemp = (maxTemperature - oldTemp < amount) ? maxTemperature : (oldTemp + amount);
		setTemp(newTemp);
		if (this.temperature <= this.getBaseTempBasedOnBiome(te.getPos()) && this.thermalMode != EnumThermalMode.COOLING) {
			this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
		}
		return newTemp - oldTemp;
	}

	@Override
	public EnumThermalMode getThermalMode() {
		return this.thermalMode;
	}

	@Override
	public boolean drawHeatFromSide(BlockPos pos, Direction side) {
		PooledMutable blockPos = PooledMutable.retain();
		blockPos.setPos(pos).move(side);
		World world = this.te.getWorld();
		if (world != null) {
			TileEntity tile = world.getTileEntity(blockPos);
			if (tile != null) {
				LazyOptional<IHeat> heatCap = tile.getCapability(CapabilityHeat.HEAT_CAPABILITY, null);
				IHeat heatHandler = heatCap.orElse(null);
				if (heatHandler != null) {
					if (heatHandler.canTransmitHeat() == true) {
						this.thermalState = heatHandler.getThermalState();
						if (this.getThermalState() == EnumThermalState.WORKING) {
							float temp1 = heatHandler.getTemperature();
							float temp2 = this.getTemperature();
							float tempDif = (temp1 + 273f) - (temp2 + 273f);
							tempDif /= 300.0f;
							float temp = tempDif*(0.2f / this.specificHeat);
							this.heatingRate = MathUtil.roundFloat(temp, 2);
							return true;
						}
					}else{
						return false;
					}
				}
			}
		}
		blockPos.close();
		return false;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putFloat("temperature", this.getTemperature());
		tag.putFloat("max_temperature", maxTemperature);
		tag.putByte("thermal_state", thermalState.getValueForNBT());
		tag.putByte("thermal_mode", thermalMode.getValueForNBT());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag) {
		temperature = tag.getFloat("temperature");
		maxTemperature = tag.getFloat("max_temperature");
		thermalState = EnumThermalState.getValueFromNBT(tag.getByte("thermal_state"));
		thermalMode = EnumThermalMode.getValueFromNBT(tag.getByte("thermal_mode"));
	}

	@Override
	public boolean canTransmitHeat() {
		return this.transmitsHeat;
	}

	@Override
	public float getBaseTempBasedOnBiome(BlockPos pos) {
		World world = this.te.getWorld();
		if (world != null) {
			PooledMutable blockPos = PooledMutable.retain();
			blockPos.setPos(pos);
			Biome biome = world.getBiome(blockPos);
			float biomeTemp = biome.getTemperature(blockPos);
			float tempF = (float) (95.9451242/(1+(3.012462778*(Math.pow(Math.E, (-3.330913488*biomeTemp))))));
			float temp = (tempF - 32.0f) * (5.0f/9.0f);
			blockPos.close();
			return temp;
		}
		return 0;
	}
	
	@Override
	public float getRate(EnumThermalMode mode) {
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
		switch (mode) {
			case HEATING:
				this.heatingRate = rate;
			case COOLING:
				this.coolingRate = rate;
		}
		
	}
	
	@Override
	public void setThermalMode(EnumThermalMode mode) {
		this.thermalMode = mode;
	}

	@Override
	public void cool() {
		this.decreaseTemp(this.coolingRate);
		if (this.canTransmitHeat()) {
			if (this.thermalMode != EnumThermalMode.COOLING && (this.temperature > this.getBaseTempBasedOnBiome(te.getPos()))) {
				this.thermalState = EnumThermalState.APPROACHING_EQUILIBRIUM;
			}else if (this.temperature < this.getBaseTempBasedOnBiome(te.getPos())) {
				this.thermalState = EnumThermalState.WORKING;
			}else {
				this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
			}
		}
	}

	@Override
	public void heat() {
		this.increaseTemp(this.heatingRate);
		if (this.canTransmitHeat()) {
			if (this.thermalMode == EnumThermalMode.COOLING && (this.temperature < this.getBaseTempBasedOnBiome(te.getPos()))) {
				this.thermalState = EnumThermalState.APPROACHING_EQUILIBRIUM;
			}else if (this.temperature > this.getBaseTempBasedOnBiome(te.getPos())){
				this.thermalState = EnumThermalState.WORKING;
			}else{
				this.thermalState = EnumThermalState.AT_EQUILIBRIUM;
			}
		}
	}

	@Override
	public EnumThermalState getThermalState() {
		return this.thermalState;
	}
}
