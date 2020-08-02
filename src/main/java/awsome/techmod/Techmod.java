package awsome.techmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import awsome.techmod.registry.Registration;
import awsome.techmod.setup.ClientSetup;
import awsome.techmod.setup.ModSetup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class Techmod {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public Techmod() {
		Registration.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
	}
	
}