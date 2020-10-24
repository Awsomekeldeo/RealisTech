package awsome.realistech.util;

import java.io.IOException;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GFXUtil {
	
	public static int[] loadTempMapColors(IResourceManager manager, ResourceLocation texture) throws IOException {
		int[] intarray;
		try (
			IResource resource = manager.getResource(texture);
			NativeImage nativeImage = NativeImage.read(resource.getInputStream());
		) {
			intarray = makePixelArray(nativeImage);
		}
		
		return intarray;
	}
	
	public static int[] makePixelArray(NativeImage image) {
		if (image.getFormat() != NativeImage.PixelFormat.RGBA) {
			throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
		} else {
			
			int[] aint = new int[image.getWidth() * image.getHeight()];

			for(int i = 0; i < image.getHeight(); ++i) {
				for(int j = 0; j < image.getWidth(); ++j) {
					int k = image.getPixelRGBA(j, i);
					int l = NativeImage.getAlpha(k);
					int i1 = NativeImage.getBlue(k);
					int j1 = NativeImage.getGreen(k);
					int k1 = NativeImage.getRed(k);
					int l1 = l << 24 | k1 << 16 | j1 << 8 | i1;
					aint[j + i * image.getWidth()] = l1;
				}
			}

			return aint;
		}
	}
	
}
