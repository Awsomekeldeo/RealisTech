package awsome.techmod.api.capability.energy;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

/**
 * Capability used for thermal energy (includes cooling)
 * 
 * @author Awsomekeldeo
 */
public interface IHeat {
	
	/**
	 * Draws heat energy from nearby Tile Entities
	 * @return Amount of heat drawn
	 */
	float drawHeatFromSide(BlockPos pos, Direction side);
	
	
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
	 * Gets wether or not the machine is in cooling mode (Cooling mode allows a machine's temperature to drop below 300K)
	 */
	boolean getInCoolingMode();
	
	/**
	 * Sets the temperature of the machine
	 */
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
	 * @param mode true for cooling mode, false for heating mode
	 */
	void setThermalMode(boolean mode);
	
}
