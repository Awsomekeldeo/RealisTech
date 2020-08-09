package awsome.techmod.api.capability.energy;

import awsome.techmod.api.capability.impl.HeatHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityHeat {
	
	@CapabilityInject(IHeat.class)
	public static Capability<IHeat> HEAT_CAPABILITY = null;
	
	public static void init() {
		CapabilityManager.INSTANCE.register(IHeat.class, new Capability.IStorage<IHeat>() {
			@Override
			public INBT writeNBT(Capability<IHeat> capability, IHeat instance, Direction side) {
				CompoundNBT tag = new CompoundNBT();
				tag.putFloat("temperature", instance.getTemperature());
				return tag;
			}

			@SuppressWarnings("deprecation")
			@Override
			public void readNBT(Capability<IHeat> capability, IHeat instance, Direction side, INBT nbt) {
				CompoundNBT tag = (CompoundNBT) nbt;
				instance.setTemp(tag.getLong("temperature"));
			}
		}, () -> new HeatHandler(null, 0, false));
	}
	
}
