package awsome.realistech.registry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;

public class LazyRegistry<V extends ForgeRegistryEntry<V>> implements IForgeRegistry<V> {
	
	private final LazyValue<IForgeRegistry<V>> registry;
	
	protected LazyRegistry(final Class<V> registryType) {
		registry = new LazyValue<IForgeRegistry<V>>(
				() -> Objects.requireNonNull(GameRegistry.findRegistry(registryType),
					() -> String.format("Registry of type %s not present", registryType.getName())
				)
		);
	}

	public static <V extends ForgeRegistryEntry<V>> LazyRegistry<V> of (final Class<V> registryType) {
		return new LazyRegistry<>(registryType);
	}
	
	private IForgeRegistry<V> getRegistry() {
		return registry.getValue();
	}
	
	@Override
	public Iterator<V> iterator() {
		return getRegistry().iterator();
	}

	@Override
	public ResourceLocation getRegistryName() {
		return getRegistry().getRegistryName();
	}

	@Override
	public Class<V> getRegistrySuperType() {
		return getRegistry().getRegistrySuperType();
	}

	@Override
	public void register(V value) {
		getRegistry().register(value);
	}
	
	@SafeVarargs
	@Override
	public final void registerAll(V... values) {
		getRegistry().registerAll(values);
	}

	@Override
	public boolean containsKey(ResourceLocation key) {
		return getRegistry().containsKey(key);
	}

	@Override
	public boolean containsValue(V value) {
		return getRegistry().containsValue(value);
	}

	@Override
	public boolean isEmpty() {
		return getRegistry().isEmpty();
	}

	@Override
	public V getValue(ResourceLocation key) {
		return getRegistry().getValue(key);
	}

	@Override
	public ResourceLocation getKey(V value) {
		return getRegistry().getKey(value);
	}

	@Override
	public ResourceLocation getDefaultKey() {
		return getRegistry().getDefaultKey();
	}

	@Override
	public Set<ResourceLocation> getKeys() {
		return getRegistry().getKeys();
	}

	@Override
	public Collection<V> getValues() {
		return getRegistry().getValues();
	}

	@Override
	public Set<Entry<ResourceLocation, V>> getEntries() {
		return getRegistry().getEntries();
	}

	@Override
	public <T> T getSlaveMap(ResourceLocation slaveMapName, Class<T> type) {
		return getRegistry().getSlaveMap(slaveMapName, type);
	}
}
