package awsome.realistech.setup;

import awsome.realistech.Reference;
import awsome.realistech.client.gui.containter.screen.CrucibleScreen;
import awsome.realistech.client.gui.containter.screen.FireboxScreen;
import awsome.realistech.client.gui.containter.screen.KilnScreen;
import awsome.realistech.client.gui.containter.screen.KnappingScreen;
import awsome.realistech.client.gui.containter.screen.MediumHeatFurnaceScreen;
import awsome.realistech.client.gui.containter.screen.MoldingScreen;
import awsome.realistech.client.model.CeramicMoldModel;
import awsome.realistech.client.renderer.AnvilRenderer;
import awsome.realistech.listeners.RecipeReloadListener;
import awsome.realistech.listeners.ResourceReloadListener;
import awsome.realistech.registry.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GrassColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static void init(final FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new RecipeReloadListener());
		MinecraftForge.EVENT_BUS.register(new ResourceReloadListener());
		registerBlockColors();
		registerItemColors(Minecraft.getInstance().getBlockColors());
		ScreenManager.registerFactory(Registration.FIREBOX_CONTAINER.get(), FireboxScreen::new);
		ScreenManager.registerFactory(Registration.CRUCIBLE_CONTAINER.get(), CrucibleScreen::new);
		ScreenManager.registerFactory(Registration.KILN_CONTAINER.get(), KilnScreen::new);
		ScreenManager.registerFactory(Registration.KNAPPING_CRAFTING_CONTAINER.get(), KnappingScreen::new);
		ScreenManager.registerFactory(Registration.MOLDING_CRAFTING_CONTAINER.get(), MoldingScreen::new);
		ScreenManager.registerFactory(Registration.WEAK_FURNACE_CONTAINER.get(), MediumHeatFurnaceScreen::new);
		RenderType cutoutMipped = RenderType.getCutoutMipped();
		RenderType cutout = RenderType.getCutout();
		
		ModelLoaderRegistry.registerLoader(new ResourceLocation(Reference.MODID, "ceramic_mold"), CeramicMoldModel.Loader.INSTANCE);
		
		ClientRegistry.bindTileEntityRenderer(Registration.ANVIL_TILEENTITY.get(), AnvilRenderer::new);
		
		RenderTypeLookup.setRenderLayer(Registration.CRUCIBLE.get(), cutout);
		RenderTypeLookup.setRenderLayer(Registration.VANILLA_CLAY_GRASS.get(), cutoutMipped);
		RenderTypeLookup.setRenderLayer(Registration.KAOLINITE_CLAY_GRASS.get(), cutoutMipped);
		RenderTypeLookup.setRenderLayer(Registration.GOLDENROD.get(), cutout);
		RenderTypeLookup.setRenderLayer(Registration.KAOLINITE_LILY.get(), cutout);
	}
	
	/* Have to register the resource reload listener here becuause the one in FMLServerAboutToStartEvent is server-side only
	 * and doesn't have resources in the assets/ folder.
	 */
	public static void registerResourceReloadListeners(ParticleFactoryRegisterEvent event) {
		IReloadableResourceManager resourceManager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();
		resourceManager.addReloadListener(new ResourceReloadListener());
	}
	
	private static void registerBlockColors() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		blockColors.register((state, world, pos, tintIndex) -> {
			return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5d, 1.0d);
		}, Registration.VANILLA_CLAY_GRASS.get(), Registration.KAOLINITE_CLAY_GRASS.get());
	}
	
	private static void registerItemColors(BlockColors colors) {
		ItemColors itemColors = Minecraft.getInstance().getItemColors();
		
		//Clay Grasses
		itemColors.register((stack, tintIndex) -> {
			BlockState state = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			return colors.getColor(state, (ILightReader)null, (BlockPos)null, tintIndex);
		}, Registration.VANILLA_CLAY_GRASS_ITEM.get(), Registration.KAOLINITE_CLAY_GRASS_ITEM.get());
		
		//Solidified Molds
		itemColors.register((stack, tintIndex) -> {
			int color = 0x00FFFFFF;
			if (tintIndex == 1) {
				if (stack.getTag().contains("realistech:itemColor")) {
					CompoundNBT nbt = stack.getTag();
					color = nbt.getInt("realistech:itemColor");
				}
			}
			return color;
		},  Registration.FIRED_FILLED_CERAMIC_AXE_MOLD.get(),
			Registration.FIRED_FILLED_CERAMIC_PICKAXE_MOLD.get(),
			Registration.FIRED_FILLED_CERAMIC_SWORD_MOLD.get(),
			Registration.FIRED_FILLED_CERAMIC_SHOVEL_MOLD.get(),
			Registration.FIRED_FILLED_CERAMIC_INGOT_MOLD.get(),
			Registration.FIRED_FILLED_CERAMIC_PROPICK_MOLD.get());
	}
}
