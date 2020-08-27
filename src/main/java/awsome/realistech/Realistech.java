package awsome.realistech;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import awsome.realistech.registry.Registration;
import awsome.realistech.setup.ClientSetup;
import awsome.realistech.setup.ModSetup;
import awsome.realistech.worldgen.capability.WorldgenCapProvider;
import awsome.realistech.worldgen.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MODID)
public class Realistech {
	
	public static final Logger LOGGER = LogManager.getLogger(Reference.MODID);
	
	public Realistech() {
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
	
	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void addItemTooltips(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		if (stack.getItem() == Items.BRICK) {
			event.getToolTip().add(1, new TranslationTextComponent("tooltip.realistech.brick").applyTextStyle(TextFormatting.GRAY).applyTextStyle(TextFormatting.ITALIC));
		}
	}
}