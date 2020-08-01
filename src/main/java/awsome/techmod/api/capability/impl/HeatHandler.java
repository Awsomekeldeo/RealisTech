package awsome.techmod.api.capability.impl;

import awsome.techmod.api.capability.energy.CapabilityHeat;
import awsome.techmod.api.capability.energy.IHeat;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutable;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class HeatHandler implements IHeat, INBTSerializable<CompoundNBT> {
	public TileEntity te;
	public float temperature = 0;
	public float maxTemperature;
	
	public boolean canCool;
	public boolean transmitsHeat;
	
	public HeatHandler(TileEntity te, float maxTemp, boolean canTransmitHeat) {
		this.te = te;
		this.maxTemperature = maxTemp;
		this.transmitsHeat = canTransmitHeat;
		getBaseTempBasedOnBiome(this.te.getPos());
	}
	
	public HeatHandler heatProducer(TileEntity te, float maxTemp) {
		return new HeatHandler(te, maxTemp, true);
	}
	
	public HeatHandler heatConsumer(TileEntity te, float maxTemp) {
		return new HeatHandler(te, maxTemp, false);
	}
	
	public void setTemp(float temp) {
		if (temp < getBaseTempBasedOnBiome(this.te.getPos()) && !canCool ) {
			this.temperature = getBaseTempBasedOnBiome(te.getPos());
		}else{
			this.temperature = temp;
		}
	}
	
	@Override
	public float getTemperature() {
		if (this.temperature < getBaseTempBasedOnBiome(this.te.getPos()) && !canCool) {
			return getBaseTempBasedOnBiome(te.getPos());
		}else{
			return this.temperature;	
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
		if (newTemp < 0)
			newTemp = 0;
		setTemp(newTemp);
		return newTemp - oldTemp;
	}

	@Override
	public boolean getInCoolingMode() {
		return false;
	}

	@Override
	public float drawHeatFromSide(BlockPos pos, Direction side) {
		if (!pos.equals(BlockPos.ZERO)) {
			PooledMutable blockPos = PooledMutable.retain();
			blockPos.setPos(pos).move(side);
			World world = this.te.getWorld();
			TileEntity tile = world.getTileEntity(blockPos);
			if (tile != null) {
				LazyOptional<IHeat> heatCap = tile.getCapability(CapabilityHeat.HEAT_CAPABILITY, null);
				IHeat heatHandler = heatCap.orElseThrow(() -> new IllegalArgumentException("Heat capability cannot be null"));
				if (heatHandler != null) {
					if (heatHandler.canTransmitHeat() == true) {
						float lossyTemp = (float) (heatHandler.getTemperature() * (7/8f));
						return lossyTemp;
					}else{
						return this.getTemperature();
					}
				}else{
					return this.getTemperature();
				}
			}
		}
		return 0;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putFloat("temperature", temperature);
		tag.putFloat("max_temperature", maxTemperature);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag) {
		temperature = tag.getLong("temperature");
		maxTemperature = tag.getLong("max_temperature");
	}

	@Override
	public boolean canTransmitHeat() {
		return this.transmitsHeat;
	}

	@Override
	public float getBaseTempBasedOnBiome(BlockPos pos) {
		World world = this.te.getWorld();
		if (!pos.equals(BlockPos.ZERO)) {
			PooledMutable blockPos = PooledMutable.retain();
			blockPos.setPos(pos);
			Biome biome = world.getBiome(blockPos);
			float biomeTemp = biome.getTemperature(blockPos);
			float tempF = (float) (95.9451242/(1+(3.012462778*(Math.pow(Math.E, (-3.330913488*biomeTemp))))));
			float temp = (tempF - 32) * (5.0f/9.0f);
			blockPos.close();
			return temp;
		}
		return 0;
	}

	@Override
	public void setThermalMode(boolean mode) {
		this.canCool = mode;
	}
}
