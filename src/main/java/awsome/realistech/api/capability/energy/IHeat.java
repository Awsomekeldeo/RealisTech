package awsome.realistech.api.capability.energy;

import awsome.realistech.api.capability.impl.HeatHandler;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

/**
 * Capability used for thermal energy (includes cooling)
 * <br>
 * <br>
 * <b>This is the API Interface</b>
 * <br>
 * A reference implementation can be found at {@link HeatHandler}
 * @author Awsomekeldeo
 */
public interface IHeat {
	/**
	 * Enum representing the machines thermal mode
	 * @author Awsomekeldeo
	 */
	public enum EnumThermalMode {
		COOLING((byte)0), 
		HEATING((byte)1);
		
		byte index;
		
		private EnumThermalMode(byte index) {
			this.index = index;
		}
		
		public byte getValueForNBT() {
			return this.index;
		}
		
		public static EnumThermalMode getValueFromNBT(byte value){
			switch (value) {
				case 0:
					return EnumThermalMode.COOLING;
				case 1:
					return EnumThermalMode.HEATING;
				default:
					throw new IllegalStateException("An invalid thermal mode was read from NBT");
			}
		}
		
		@Override
		public String toString() {
			switch (this) {
				case COOLING:
					return "cooling";
				case HEATING:
					return "heating";
				default:
					return "invalid";
			}
		}
	}
	
	/**
	 * Enum representing the thermal state of the machine.
	 * @author Awsomekeldeo
	 */
	public enum EnumThermalState {
		APPROACHING_EQUILIBRIUM((byte)0),
		AT_EQUILIBRIUM((byte)1),
		WORKING((byte)2);
		
		byte index;
		
		EnumThermalState(byte index) {
			this.index = index;
		}
		
		public byte getValueForNBT() {
			return this.index;
		}
		
		public static EnumThermalState getValueFromNBT(byte value){
			switch (value) {
				case 0:
					return EnumThermalState.APPROACHING_EQUILIBRIUM;
				case 1:
					return EnumThermalState.AT_EQUILIBRIUM;
				case 2:
					return EnumThermalState.WORKING;
				default:
					throw new IllegalStateException("An invalid thermal state was read from NBT");
			}
		}
		
		@Override
		public String toString() {
			switch (this) {
				case APPROACHING_EQUILIBRIUM:
					return "approaching equilibrium";
				case AT_EQUILIBRIUM:
					return "at equilibrium";
				case WORKING:
					return "working";
				default:
					return "invalid";
			}
		}
	}
	
	/**
	 * Draws heat energy from nearby Tile Entities
	 * @param pos The Tile Entity's position
	 * @param side A {@link net.minecraft.util.Direction Direction} to draw heat form
	 * @return true if the machine was succesfully able to draw heat, false otherwise
	 */
	boolean drawHeatFromSide(BlockPos pos, Direction side);
	
	/**
	 * Returns the current temperature.
	 */
	float getTemperature();
	
	/**
	 * Returns the maximum temperature a machine can have.
	 * Increasing the temperature above this level will cause
	 * the machine to melt.
	 */
	float getMaxTemperature();
	
	/**
	 * Increases or decreases the temperature of the machine
	 * @param amount The amount to decrease or increase the
	 * @return Amount the temperature changed by
	 */
	float changeTemp(float amount);
	
	/**
	 * Increases the temperature.
	 * @param increaseAmount Amount to increase the temperature by.
	 * @return The amount the temperature increased by.
	 */
	default float increaseTemp(float increaseAmount) {
		return changeTemp(increaseAmount);
	};
	
	/**
	 * Decreases the temperature.
	 * @param decreaseAmount amount to decrease the temperature by.
	 * @return The amount the temperature increased by.
	 */
	default float decreaseTemp(float decreaseAmount) {
		return changeTemp(-decreaseAmount);
	};
	
	/**
	 * Gets the machine's thermal mode
	 */
	EnumThermalMode getThermalMode();
	
	/**
	 * Sets the temperature of the machine
	 * @deprecated Use {@link #heat()} and {@link #cool()} instead of setting the temperature directly, unless synchronizing NBT data
	 */
	@Deprecated
	void setTemp(float temp);
	
	/**
	 * @return A boolean representing if the machine produces heat
	 */
	boolean canTransmitHeat();
	
	/**
	 * Gets what the base temperature should be for the biome the machine is located in
	 * @return The temperature based on the biome
	 */
	float getBaseTempBasedOnBiome(BlockPos pos);
	
	/**
	 * Sets the machine to the specified mode
	 * @param mode The machine's thermal mode
	 * @throws IllegalArgumentException when mode is not 0 or 1
	 */
	void setThermalMode(EnumThermalMode mode);
	
	/**
	 * Called when the machine should cool according to it's cooling rate
	 */
	void cool();
	
	/**
	 * Called when the machine should heat according to it's heating rate
	 */
	void heat();
	
	/**
	 * Gets the machine's cooling or heating rate
	 * @param mode Which rate to get 
	 * @throws IllegalArgumentException If heatOrCool is not equal to 0 or 1
	 */
	float getRate(EnumThermalMode mode);
	
	/**
	 * Sets the machine's cooling or heating rate
	 * @param mode Which rate to set
	 * @param rate The cooling rate
	 */
	void setRate(EnumThermalMode mode, float rate);
	
	/**
	 * Gets the thermal state of the machine
	 * <br>
	 * (E.g cooling down if the machine is hot or heating up if it's cold)
	 */
	EnumThermalState getThermalState();
}
