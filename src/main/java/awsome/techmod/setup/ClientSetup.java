package awsome.techmod.setup;

import awsome.techmod.Reference;
import awsome.techmod.gui.CrucibleScreen;
import awsome.techmod.gui.FireboxScreen;
import awsome.techmod.gui.KilnScreen;
import awsome.techmod.registry.Registration;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	@SuppressWarnings("unused")
	public static void init(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(Registration.FIREBOX_CONTAINER.get(), FireboxScreen::new);
		ScreenManager.registerFactory(Registration.CRUCIBLE_CONTAINER.get(), CrucibleScreen::new);
		ScreenManager.registerFactory(Registration.KILN_CONTAINER.get(), KilnScreen::new);
		RenderType cutoutMipped = RenderType.getCutoutMipped();
		RenderType cutout = RenderType.getCutout();
		
		RenderTypeLookup.setRenderLayer(Registration.CRUCIBLE.get(), cutout);
	}
}
