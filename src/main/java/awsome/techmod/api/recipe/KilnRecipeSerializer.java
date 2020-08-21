package awsome.techmod.api.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class KilnRecipeSerializer<T extends KilnRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<KilnRecipe> {

	private final KilnRecipeSerializer.IFactory<T> factory;
	
	public KilnRecipeSerializer(KilnRecipeSerializer.IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public KilnRecipe read(ResourceLocation recipeId, JsonObject json) {
		final JsonElement inputElement = JSONUtils.isJsonArray(json, "input") ? JSONUtils.getJsonArray(json, "input") : JSONUtils.getJsonObject(json, "input");
		final Ingredient input = Ingredient.deserialize(inputElement);
		
		final ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), false);
		final int temp = JSONUtils.getInt(json, "firing_temperature");
		
		return this.factory.create(recipeId, input, output, temp);
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {
		final Ingredient input = Ingredient.read(buffer);
		final ItemStack output = buffer.readItemStack();
		final ResourceLocation id = buffer.readResourceLocation();
		final int temperature = buffer.readInt();
		
		return this.factory.create(id, input, output, temperature);
	}

	@Override
	public void write(PacketBuffer buffer, KilnRecipe recipe) {
		recipe.input.write(buffer);
		buffer.writeItemStack(recipe.output);
		buffer.writeResourceLocation(recipe.id);
		buffer.writeInt(recipe.fireTemp);
	}
	
	public interface IFactory<T extends KilnRecipe> {
		T create(ResourceLocation resourceLocation, Ingredient ingredient, ItemStack output, int temp);
	}
	
	public KilnRecipeSerializer.IFactory<T> getFactory() {
		return factory;
	}
}
