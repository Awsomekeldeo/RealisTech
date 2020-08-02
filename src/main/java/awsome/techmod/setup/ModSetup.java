package awsome.techmod.setup;

import awsome.techmod.Reference;
import awsome.techmod.api.capability.energy.CapabilityHeat;
import awsome.techmod.registry.Registration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
	
	public static final ItemGroup TECHMOD_MACHINES = new ItemGroup("techmod.machines") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.FIREBOX.get());
		}
	};
	public static final ItemGroup TECHMOD_ORES =  new ItemGroup("techmod.ores") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.COPPER_ORE.get());
		}
		
	};
	
	public static final ItemGroup TECHMOD_MATERIALS =  new ItemGroup("techmod.materials") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.COPPER_INGOT.get());
		}
		
	};
	
	public static void init(final FMLCommonSetupEvent event) {
		CapabilityHeat.init();
	}
}
