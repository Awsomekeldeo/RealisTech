package awsome.realistech.setup;

import awsome.realistech.Reference;
import awsome.realistech.client.gui.containter.screen.CrucibleScreen;
import awsome.realistech.client.gui.containter.screen.FireboxScreen;
import awsome.realistech.client.gui.containter.screen.KilnScreen;
import awsome.realistech.client.gui.containter.screen.KnappingScreen;
import awsome.realistech.client.gui.containter.screen.MoldingScreen;
import awsome.realistech.listeners.RecipeReloadListener;
import awsome.realistech.registry.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static void init(final FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new RecipeReloadListener());
		registerBlockColors();
		registerItemColors(Minecraft.getInstance().getBlockColors());
		ScreenManager.registerFactory(Registration.FIREBOX_CONTAINER.get(), FireboxScreen::new);
		ScreenManager.registerFactory(Registration.CRUCIBLE_CONTAINER.get(), CrucibleScreen::new);
		ScreenManager.registerFactory(Registration.KILN_CONTAINER.get(), KilnScreen::new);
		ScreenManager.registerFactory(Registration.KNAPPING_CRAFTING_CONTAINER.get(), KnappingScreen::new);
		ScreenManager.registerFactory(Registration.MOLDING_CRAFTING_CONTAINER.get(), MoldingScreen::new);
		RenderType cutoutMipped = RenderType.getCutoutMipped();
		RenderType cutout = RenderType.getCutout();
		
		RenderTypeLookup.setRenderLayer(Registration.CRUCIBLE.get(), cutout);
		RenderTypeLookup.setRenderLayer(Registration.VANILLA_CLAY_GRASS.get(), cutoutMipped);
		RenderTypeLookup.setRenderLayer(Registration.KAOLINITE_CLAY_GRASS.get(), cutoutMipped);
		RenderTypeLookup.setRenderLayer(Registration.GOLDENROD.get(), cutout);
		RenderTypeLookup.setRenderLayer(Registration.KAOLINITE_LILY.get(), cutout);
	}

	private static void registerBlockColors() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		blockColors.register((state, world, pos, tintIndex) -> {
			return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5d, 1.0d);
		}, Registration.VANILLA_CLAY_GRASS.get(), Registration.KAOLINITE_CLAY_GRASS.get());
	}
	
	private static void registerItemColors(BlockColors colors) {
		ItemColors itemColors = Minecraft.getInstance().getItemColors();
		itemColors.register((stack, tintIndex) -> {
			BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return colors.getColor(state, (ILightReader)null, (BlockPos)null, tintIndex);
		}, Registration.VANILLA_CLAY_GRASS_ITEM.get(), Registration.KAOLINITE_CLAY_GRASS_ITEM.get());
	}
}
