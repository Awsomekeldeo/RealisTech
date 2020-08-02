package awsome.techmod.setup;

import awsome.techmod.Reference;
import awsome.techmod.gui.FireboxScreen;
import awsome.techmod.registry.Registration;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static void init(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(Registration.FIREBOX_CONTAINER.get(), FireboxScreen::new);
	}
}
