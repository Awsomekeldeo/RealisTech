package awsome.realistech.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class Generators {
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class GatherDataSubscriber {
		@SubscribeEvent
		public static void gatherData(GatherDataEvent event) {
			DataGenerator generator = event.getGenerator();
	        if (event.includeClient()) {
	            generator.addProvider(new BlockStates(generator, event.getExistingFileHelper()));
	            generator.addProvider(new Items(generator, event.getExistingFileHelper()));
	        }
	        if (event.includeServer()) {
	        	generator.addProvider(new LootTables(generator));
	        	generator.addProvider(new Recipes(generator));
	        	generator.addProvider(new ModBlockTags(generator));
	        }
		}
	}
}
