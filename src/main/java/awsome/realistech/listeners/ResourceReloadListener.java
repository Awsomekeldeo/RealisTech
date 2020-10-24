package awsome.realistech.listeners;

import java.io.IOException;
import java.util.function.Predicate;

import awsome.realistech.Reference;
import awsome.realistech.util.GFXUtil;
import awsome.realistech.util.GeneralUtils;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

public class ResourceReloadListener implements ISelectiveResourceReloadListener {
	
	private static final ResourceLocation TEMPERATURE_COLORMAP_LOCATION = new ResourceLocation(Reference.MODID, "textures/colormap/temperature.png");
	
	int[] tempColors;
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
		if (resourcePredicate.test(VanillaResourceType.TEXTURES)) {
			try {
				tempColors = GFXUtil.loadTempMapColors(resourceManager, TEMPERATURE_COLORMAP_LOCATION);
				GeneralUtils.setTemperatureColorizer(tempColors);
			} catch (IOException ioexception) {
				throw new IllegalStateException("Failed to load temperature color texture", ioexception);
			}
		}
	}
}
