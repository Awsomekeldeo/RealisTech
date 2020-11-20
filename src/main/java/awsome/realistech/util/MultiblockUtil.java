package awsome.realistech.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;

/* A modified version Immersive Engineering's StaticTemplateManager:
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.14/src/main/java/blusunrize/immersiveengineering/common/blocks/multiblocks/StaticTemplateManager.java
 * Original source and credit goes to Blusunrize & the IE team.
 */
public class MultiblockUtil {
	
	public static Optional<InputStream> getResource(ResourcePackType type, ResourceLocation name) {
		return ModList.get().getMods().stream()
				.map(ModInfo::getModId)
				.map(ResourcePackLoader::getResourcePackFor)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.filter(filter -> filter.resourceExists(type, name))
				.map(filter -> getInputStreamOrThrow(type, name, filter))
				.findAny();
	}
	
	private static InputStream getInputStreamOrThrow(ResourcePackType type, ResourceLocation name, ModFileResourcePack source) {
		try {
			return source.getResourceStream(type, name);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Template loadStaticStructureTemplate(ResourceLocation location) throws IOException {
		String[] paths = {
			"structures/multiblock/" + location.getPath() + ".nbt",
			location.getPath() + ".nbt"
		};
		
		for (String path : paths) {
			
			Optional<InputStream> stream = getResource(ResourcePackType.SERVER_DATA,
					new ResourceLocation(location.getNamespace(), path));
			if(stream.isPresent())
				return loadTemplateFromDisk(stream.get());
		}
		
		throw new RuntimeException("Could not find resouce: " + location + ".nbt");
	}
	
	public static Template loadTemplateFromDisk(InputStream stream) throws IOException {
		CompoundNBT nbt = CompressedStreamTools.readCompressed(stream);
		Template template = new Template();
		template.read(nbt);
		return template;
	}
	
}
