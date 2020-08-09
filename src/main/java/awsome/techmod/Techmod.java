package awsome.techmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import awsome.techmod.registry.Registration;
import awsome.techmod.setup.ClientSetup;
import awsome.techmod.setup.ModSetup;
import awsome.techmod.worldgen.capability.WorldgenCapProvider;
import awsome.techmod.worldgen.utils.Utils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class Techmod {
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);
	
	public Techmod() {
		Registration.init();
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<World> event) {
		event.addCapability(new ResourceLocation(Reference.MODID, "pluton"), new WorldgenCapProvider());
		LOGGER.info("Worldgen capability attached for " + Utils.dimensionToString(event.getObject().getDimension()));
	}
}